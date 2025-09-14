<script setup>
import { Button, Dialog, InputText } from "primevue";
import { ref } from "vue";
import BasicInfoForm from "../components/Admin/BasicInfoForm.vue";

const createRef = ref(false);

const show = () => {
  createRef.value = true;
};
</script>

<template>
  <Button :onclick="show">Create Event</Button>
  <Dialog
    v-model:visible="createRef"
    modal
    header="Create Event"
    :style="{ width: '32rem' }"
  >
    <BasicInfoForm />
    <div class="flex justify-end gap-2">
      <Button
        type="button"
        label="Cancel"
        severity="secondary"
        @click="createRef = false"
      ></Button>
      <Button type="button" label="Save" @click="createRef = false"></Button>
    </div>
  </Dialog>
</template>
