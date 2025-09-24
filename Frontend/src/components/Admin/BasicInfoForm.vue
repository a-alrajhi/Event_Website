<script setup>
import { useCreateEventStore } from "../../stores/createEventStore";
import { watch, onMounted, ref } from "vue";

const createEventStore = useCreateEventStore();

const name = ref("");
const arName = ref("");
const desc = ref("");
const arDesc = ref("");
const category = ref(null);
const eventImage = ref(null);
const dragOver = ref(false);

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

const handleFileSelect = (event) => {
  const file = event.target.files[0];
  if (file) {
    eventImage.value = file;
  }
};

const handleDrop = (event) => {
  event.preventDefault();
  dragOver.value = false;
  const file = event.dataTransfer.files[0];
  if (file && file.type.startsWith('image/')) {
    eventImage.value = file;
  }
};

const handleDragOver = (event) => {
  event.preventDefault();
  dragOver.value = true;
};

const handleDragLeave = () => {
  dragOver.value = false;
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
  <div class="flex flex-col gap-6">
    <!-- Section Title -->
    <div class="flex items-center gap-3">
      <i class="pi pi-info-circle text-xl" style="color: var(--color-primary);"></i>
      <h3 class="text-xl font-semibold" style="color: var(--color-text);">
        Basic Information
      </h3>
    </div>

    <!-- Form Fields -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <!-- Event Names -->
      <div class="admin-form-group">
        <label class="admin-form-label">Event Name (English)</label>
        <input
          id="name"
          v-model="name"
          type="text"
          class="admin-input"
          placeholder="Enter event name in English"
          autocomplete="off"
        />
      </div>

      <div class="admin-form-group">
        <label class="admin-form-label">Event Name (Arabic)</label>
        <input
          id="arName"
          v-model="arName"
          type="text"
          class="admin-input"
          placeholder="أدخل اسم الفعالية بالعربية"
          autocomplete="off"
        />
      </div>

      <!-- Descriptions -->
      <div class="admin-form-group">
        <label class="admin-form-label">Description (English)</label>
        <textarea
          id="description"
          v-model="desc"
          class="admin-textarea"
          placeholder="Enter detailed description in English"
        ></textarea>
      </div>

      <div class="admin-form-group">
        <label class="admin-form-label">Description (Arabic)</label>
        <textarea
          id="arDescription"
          v-model="arDesc"
          class="admin-textarea"
          placeholder="أدخل وصف مفصل بالعربية"
        ></textarea>
      </div>
    </div>

    <!-- Category Selection -->
    <div class="admin-form-group">
      <label class="admin-form-label">Category</label>
      <select
        v-model="category"
        class="admin-select"
      >
        <option value="" disabled>Select or enter a category</option>
        <option
          v-for="cat in createEventStore.categories"
          :key="cat.id"
          :value="cat"
        >
          {{ cat.name }}
        </option>
      </select>
    </div>

    <!-- File Upload -->
    <div class="admin-form-group">
      <label class="admin-form-label">Event Image</label>
      <div
        class="admin-file-upload"
        :class="{ 'dragover': dragOver }"
        @drop="handleDrop"
        @dragover="handleDragOver"
        @dragleave="handleDragLeave"
        @click="$refs.fileInput.click()"
      >
        <div class="flex flex-col items-center gap-4">
          <i class="pi pi-cloud-upload text-4xl" style="color: var(--color-primary);"></i>
          <div class="text-center">
            <p class="text-lg font-medium" style="color: var(--color-text);">
              <span v-if="eventImage">{{ eventImage.name || 'Image selected' }}</span>
              <span v-else-if="createEventStore.isEditMode">
                Click to change current event image
              </span>
              <span v-else>Click to upload or drag and drop</span>
            </p>
            <p class="text-sm mt-1" style="color: var(--color-gray);">
              PNG, JPG, GIF up to 1MB
            </p>
          </div>
        </div>
        <input
          ref="fileInput"
          type="file"
          accept="image/*"
          class="hidden"
          @change="handleFileSelect"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";

.hidden {
  display: none;
}
</style>
