<script setup>
import { Dialog, Button } from "primevue";
import { ref, toRefs, watch } from "vue";

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
});

const emit = defineEmits(["update:visible", "update:isAllowedNext"]);

const { header, pages } = toRefs(props);

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

const next = () => {
  if (currentIndex.value < pages.value.length - 1) {
    currentIndex.value++;
  } else {
    dialogVisible.value = false;
    currentIndex.value = 0;
  }
  emit("update:isAllowedNext", false);
};

const cancel = () => {
  dialogVisible.value = false;
  currentIndex.value = 0;
};
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
        :label="currentIndex === pages.length - 1 ? 'Save' : 'Next'"
        @click="next"
        :disabled="!isAllowedNext"
      />
    </div>
  </Dialog>
</template>
