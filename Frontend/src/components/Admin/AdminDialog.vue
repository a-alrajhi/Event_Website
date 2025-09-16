<script setup>
import { Dialog, Button } from "primevue";
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
      dialogVisible.value = false;
      currentIndex.value = 0;
    } catch (error) {
      console.error("Save failed:", error);
    }
  }
};

const cancel = () => {
  dialogVisible.value = false;
  currentIndex.value = 0;
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
  <Dialog
    v-model:visible="dialogVisible"
    modal
    :header="header"
    :style="{ width: '32rem' }"
    draggable="false"
  >
    <component :is="pages[currentIndex]" />

    <div class="flex justify-end items-baseline gap-2 mt-4">
      <Button
        type="button"
        label="Cancel"
        severity="secondary"
        @click="cancel"
      />
      <Button
        type="button"
        label="Back"
        v-if="currentIndex != 0"
        severity="primary"
        @click="back"
      />
      <Button
        type="button"
        :label="primaryButtonLabel"
        @click="next"
        :disabled="!isAllowedNext || isLoading"
      />
    </div>
  </Dialog>
</template>
