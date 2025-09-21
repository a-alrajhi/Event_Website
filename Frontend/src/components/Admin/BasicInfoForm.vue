<script setup>
import { FileUpload, FloatLabel, Textarea, InputText, Select } from "primevue";
import { useCreateEventStore } from "../../stores/createEventStore";
import { watch, onMounted, ref } from "vue";

const createEventStore = useCreateEventStore();

const name = ref("");
const arName = ref("");
const desc = ref("");
const arDesc = ref("");
const category = ref(null);
const eventImage = ref(null);

onMounted(async () => {
  await createEventStore.loadCategories();
  if (createEventStore.isEditMode) {
    name.value = createEventStore.name || "";
    arName.value = createEventStore.arName || "";
    desc.value = createEventStore.desc || "";
    arDesc.value = createEventStore.arDesc || "";
    category.value = createEventStore.category || null;
    eventImage.value = createEventStore.eventImage || null;
  }
});

const onAdvancedUpload = (event) => {
  const file = event?.files?.[0];
  console.log("file: " + file);
  if (file) {
    eventImage.value = file;
  }
};

watch(
  [name, arName, desc, arDesc, category, eventImage],
  ([n, arN, d, arD, cat, img]) => {
    const valid =
      n.trim() !== "" &&
      arN.trim() !== "" &&
      d.trim() !== "" &&
      arD.trim() !== "" &&
      cat &&
      img;

    createEventStore.isAllowedNext = valid;

    if (valid) {
      createEventStore.name = name.value;
      createEventStore.arName = arName.value;
      createEventStore.desc = desc.value;
      createEventStore.arDesc = arDesc.value;
      createEventStore.category = category.value;
      createEventStore.eventImage = eventImage.value;
    }
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
            v-model="name"
            autocomplete="off"
            class="w-full"
          />
          <label for="name">Event Name</label>
        </FloatLabel>

        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="arName"
            v-model="arName"
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
            v-model="desc"
            rows="4"
            class="w-full resize-none"
          />
          <label for="description">Description</label>
        </FloatLabel>

        <FloatLabel variant="on" class="flex-1">
          <Textarea
            id="arDescription"
            v-model="arDesc"
            rows="4"
            class="w-full resize-none"
          />
          <label for="arDescription">Arabic Description</label>
        </FloatLabel>
      </div>
    </div>
    <Select
      v-model="category"
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
        <span v-if="createEventStore.isEditMode"
          >Drag and drop an image to change current event image</span
        >
        <span v-else>Drag and drop an image for the event.</span>
      </template>
    </FileUpload>
  </div>
</template>

<style scoped>
.p-fileupload-content {
  display: none !important;
}
</style>
