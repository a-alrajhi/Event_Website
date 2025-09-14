<script setup>
import { FileUpload, FloatLabel, Textarea, InputText, Select } from "primevue";
import { useCreateEventStore } from "../../stores/createEventStore";
import { watch } from "vue";

const createEventStore = useCreateEventStore();

const onAdvancedUpload = ({ files }) => {
  if (files && files.length > 0) {
    createEventStore.setEventImage(files[0]);
  }
};

watch(
  () => [createEventStore.name, createEventStore.desc],
  ([name, desc]) => {
    createEventStore.isAllowedNext = name.trim() !== "" && desc.trim() !== "";
  },
  { immediate: true }
);
</script>

<template>
  <div class="flex flex-col gap-3">
    <span class="text-primary/90 block">Basic Info</span>
    <div class="flex flex-col gap-3 w-full">
      <div class="flex w-full gap-3">
        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="name"
            v-model="createEventStore.name"
            autocomplete="off"
            class="w-full"
          />
          <label for="name">Event Name</label>
        </FloatLabel>

        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="arName"
            v-model="createEventStore.arName"
            autocomplete="off"
            class="w-full"
          />
          <label for="arName">Arabic Name</label>
        </FloatLabel>
      </div>

      <div class="flex w-full gap-3">
        <FloatLabel variant="on" class="flex-1">
          <Textarea
            id="description"
            v-model="createEventStore.desc"
            rows="4"
            class="w-full resize-none"
          />
          <label for="description">Description</label>
        </FloatLabel>

        <FloatLabel variant="on" class="flex-1">
          <Textarea
            id="arDescription"
            v-model="createEventStore.arDesc"
            rows="4"
            class="w-full resize-none"
          />
          <label for="arDescription">Arabic Description</label>
        </FloatLabel>
      </div>
    </div>
    <Select
      v-model="createEventStore.category"
      editable
      show-clear="true"
      :options="createEventStore.categories"
      optionLabel="name"
      placeholder="Select/Write a Category"
      class="w-full"
    />
    <FileUpload
      name="eventImage"
      :customUpload="true"
      @uploader="onAdvancedUpload"
      :multiple="false"
      accept="image/*"
      :maxFileSize="1000000"
    >
      <template #empty>
        <span>Drag and drop an image for the event.</span>
      </template>
    </FileUpload>
  </div>
</template>
