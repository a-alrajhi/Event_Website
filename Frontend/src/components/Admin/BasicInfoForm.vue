<script setup>
import { FileUpload, FloatLabel, Textarea, InputText, Select } from "primevue";
import { useCreateEventStore } from "../../stores/createEventStore";
import { watch, onMounted } from "vue";

const createEventStore = useCreateEventStore();

onMounted(async () => {
  await createEventStore.loadCategories();
});

const onAdvancedUpload = (event) => {
  const file = event?.files?.[0];
  console.log("file: " + file);
  if (file) {
    createEventStore.setEventImage(file);
  }
};

watch(
  () => [
    createEventStore.name,
    createEventStore.desc,
    createEventStore.arName,
    createEventStore.arDesc,
    createEventStore.category,
    createEventStore.eventImage,
  ],
  ([name, desc, arName, arDesc, category, eventImage]) => {
    createEventStore.isAllowedNext =
      name.trim() !== "" &&
      desc.trim() !== "" &&
      arName.trim() !== "" &&
      arDesc.trim() !== "" &&
      eventImage &&
      category;
    console.log(eventImage);
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
      :show-cancel-button="false"
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

<style scoped>
.p-fileupload-content {
  display: none !important;
}
</style>
