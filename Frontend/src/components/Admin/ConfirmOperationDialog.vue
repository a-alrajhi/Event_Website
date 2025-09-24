<script setup>
import { ref, toRefs, watch } from "vue";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  header: {
    type: String,
    default: "Confirm Action",
  },
  message: {
    type: String,
    default: "Are you sure you want to proceed?",
  },
  saveFunction: {
    type: Function,
    required: true,
  },
});

const emit = defineEmits(["update:visible"]);

const { header, saveFunction, message } = toRefs(props);

const dialogVisible = ref(props.visible);
const isLoading = ref(false);

watch(
  () => props.visible,
  (newVal) => {
    dialogVisible.value = newVal;
  }
);

watch(dialogVisible, (newVal) => {
  if (newVal !== props.visible) {
    emit("update:visible", newVal);
  }
});

const confirm = async () => {
  isLoading.value = true;
  try {
    await saveFunction.value();
    dialogVisible.value = false;
  } catch (error) {
    console.error("Operation failed:", error);
  } finally {
    isLoading.value = false;
  }
};

const cancel = () => {
  dialogVisible.value = false;
};
</script>

<template>
  <!-- Modal Overlay -->
  <div
    v-if="dialogVisible"
    class="admin-modal-overlay"
    @click.self="cancel"
  >
    <!-- Modal Container -->
    <div class="admin-modal max-w-md">
      <!-- Header -->
      <div class="admin-modal-header">
        <h2 class="admin-modal-title flex items-center gap-3">
          <i class="pi pi-exclamation-triangle text-xl" style="color: var(--color-error);"></i>
          {{ header }}
        </h2>
        <button
          @click="cancel"
          class="w-8 h-8 rounded-lg flex items-center justify-center transition-colors"
          style="background-color: var(--color-bg); color: var(--color-gray);"
        >
          <i class="pi pi-times"></i>
        </button>
      </div>

      <!-- Content -->
      <div class="admin-modal-body">
        <div class="flex items-start gap-4">
          <div
            class="w-12 h-12 rounded-full flex items-center justify-center flex-shrink-0"
            style="background-color: rgba(var(--color-error), 0.1);"
          >
            <i class="pi pi-exclamation-triangle text-xl" style="color: var(--color-error);"></i>
          </div>
          <div class="flex-1">
            <p class="text-lg font-medium mb-2" style="color: var(--color-text);">
              Confirm Action
            </p>
            <p class="text-sm" style="color: var(--color-gray);">
              {{ message }}
            </p>
            <p class="text-sm mt-2" style="color: var(--color-gray);">
              This action cannot be undone.
            </p>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="admin-modal-footer">
        <button
          @click="cancel"
          class="admin-btn-secondary"
          :disabled="isLoading"
        >
          Cancel
        </button>

        <button
          @click="confirm"
          class="admin-btn-danger"
          :disabled="isLoading"
          :class="{ 'opacity-50 cursor-not-allowed': isLoading }"
        >
          <i v-if="isLoading" class="pi pi-spin pi-spinner"></i>
          <i v-else class="pi pi-trash"></i>
          {{ isLoading ? 'Processing...' : 'Confirm' }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";
</style>
