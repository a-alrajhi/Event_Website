<script setup>
import { computed, ref, toRefs, watch } from "vue";

const props = defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
  header: {
    type: String,
    default: "Create Event",
  },
  pages: {
    type: Array,
    required: true,
  },
  isAllowedNext: {
    type: Boolean,
    required: true,
  },
  isLoading: {
    type: Boolean,
    default: false,
  },
  saveFunction: {
    type: Function,
    required: true,
  },
});

const emit = defineEmits(["update:visible", "update:isAllowedNext"]);

const { header, pages, saveFunction, isLoading, isAllowedNext } = toRefs(props);

const dialogVisible = ref(props.visible);

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

const currentIndex = ref(0);

const next = async () => {
  if (currentIndex.value < pages.value.length - 1) {
    currentIndex.value++;
  } else {
    try {
      await saveFunction.value();
      currentIndex.value = 0;
      dialogVisible.value = false;
    } catch (error) {
      console.error("Save failed:", error);
    }
  }
};

const cancel = () => {
  currentIndex.value = 0;
  dialogVisible.value = false;
};

const back = () => {
  currentIndex.value--;
};

const primaryButtonLabel = computed(() => {
  if (currentIndex.value === pages.value.length - 1) {
    if (isLoading.value) {
      return "Saving...";
    }
    return "Save";
  }
  return "Next";
});
</script>

<template>
  <!-- Modal Overlay -->
  <div
    v-if="dialogVisible"
    class="admin-modal-overlay"
    @click.self="cancel"
  >
    <!-- Modal Container -->
    <div class="admin-modal admin-modal-compact">
      <!-- Header -->
      <div class="admin-modal-header">
        <h2 class="admin-modal-title">{{ header }}</h2>
        <button
          @click="cancel"
          class="w-8 h-8 rounded-lg flex items-center justify-center transition-colors"
          style="background-color: var(--color-bg); color: var(--color-gray);"
        >
          <i class="pi pi-times"></i>
        </button>
      </div>

      <!-- Progress Indicator -->
      <div class="px-4 py-2" style="border-bottom: 1px solid var(--color-gray);">
        <div class="flex items-center justify-center gap-2">
          <div
            v-for="(page, index) in pages"
            :key="index"
            class="flex items-center gap-1"
          >
            <div
              class="w-6 h-6 rounded-full flex items-center justify-center text-xs font-medium transition-all duration-200"
              :class="index <= currentIndex
                ? 'admin-step-active'
                : 'admin-step-inactive'"
            >
              {{ index + 1 }}
            </div>
            <div
              v-if="index < pages.length - 1"
              class="w-6 h-0.5 transition-colors duration-200"
              :style="index < currentIndex
                ? 'background-color: var(--color-primary);'
                : 'background-color: var(--color-gray);'"
            ></div>
          </div>
        </div>
      </div>

      <!-- Content -->
      <div class="admin-modal-body-compact admin-scrollbar">
        <component :is="pages[currentIndex]" />
      </div>

      <!-- Footer -->
      <div class="admin-modal-footer-compact">
        <button
          @click="cancel"
          class="admin-btn-secondary-small"
        >
          Cancel
        </button>

        <button
          v-if="currentIndex !== 0"
          @click="back"
          class="admin-btn-secondary-small"
        >
          <i class="pi pi-chevron-left"></i>
          Back
        </button>

        <button
          @click="next"
          :disabled="!isAllowedNext || isLoading"
          class="admin-btn-primary-small"
          :class="{ 'opacity-50 cursor-not-allowed': !isAllowedNext || isLoading }"
        >
          <i v-if="isLoading" class="pi pi-spin pi-spinner"></i>
          <i v-else-if="currentIndex < pages.length - 1" class="pi pi-chevron-right"></i>
          <i v-else class="pi pi-check"></i>
          {{ primaryButtonLabel }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";

.admin-step-active {
  background-color: var(--color-primary);
  color: var(--color-text);
}

.admin-step-inactive {
  background-color: var(--color-bg);
  color: var(--color-gray);
  border: 1px solid var(--color-gray);
}
</style>
