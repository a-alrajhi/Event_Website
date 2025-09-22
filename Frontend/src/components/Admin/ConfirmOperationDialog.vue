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
  message: {
    type: String,
    default: "To proceed click confirm",
  },
  saveFunction: {
    type: Function,
    required: true,
  },
});

const emit = defineEmits(["update:visible"]);

const { header, saveFunction, message } = toRefs(props);

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

const confirm = async () => {
  await saveFunction.value();
  dialogVisible.value = false;
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
    <div class="flex flex-col gap-3">
      <div>
        <p class="text-lg block">{{ message }}</p>
      </div>
      <div class="flex justify-end items-baseline gap-2 mt-4">
        <Button
          type="button"
          label="Cancel"
          severity="secondary"
          @click="cancel"
        />
        <Button
          type="button"
          label="Confirm"
          severity="danger"
          @click="confirm"
        />
      </div>
    </div>
  </Dialog>
</template>
