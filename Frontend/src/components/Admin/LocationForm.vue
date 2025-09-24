<script setup>
import { useCreateEventStore } from "../../stores/createEventStore";
import EventLocationMap from "../Map/EventLocationMap.vue";
import { ref, computed, watch, watchEffect, onMounted } from "vue";

const createEventStore = useCreateEventStore();

const mapVisible = ref(false);
const coords = ref({ latitude: 24.7136, longitude: 46.6753 });
const selectedLocation = ref(null);
const tempLocation = ref(null); // used during map editing
let originalLocationBeforeMap = null; // backup before entering map

onMounted(async () => {
  await createEventStore.loadLocations();

  if (createEventStore.isEditMode) {
    const matched = createEventStore.locations.find(
      (l) => l.id === createEventStore.location?.id
    );
    if (matched) {
      selectedLocation.value = matched;
    } else if (createEventStore.location) {
      // fallback to custom location from store (not in locations list)
      selectedLocation.value = {
        name: createEventStore.location.name || "",
        additionalInfo: createEventStore.location.additionalInfo || "",
        latitude: createEventStore.location.latitude || 24.7136,
        longitude: createEventStore.location.longitude || 46.6753,
      };
      mapVisible.value = true;
      coords.value = {
        latitude: selectedLocation.value.latitude,
        longitude: selectedLocation.value.longitude,
      };
    }
  }
});

const openMap = () => {
  // Save current selection for rollback
  originalLocationBeforeMap = selectedLocation.value
    ? { ...selectedLocation.value }
    : null;

  // Work on a copy while editing on map
  tempLocation.value = {
    name: "",
    additionalInfo: "",
    latitude: coords.value.latitude,
    longitude: coords.value.longitude,
  };

  coords.value = {
    latitude: tempLocation.value.latitude,
    longitude: tempLocation.value.longitude,
  };

  mapVisible.value = true;
};

const closeMap = () => {
  // Discard map changes and restore original selection
  selectedLocation.value = originalLocationBeforeMap
    ? { ...originalLocationBeforeMap }
    : null;

  tempLocation.value = null;
  mapVisible.value = false;
};

watch(coords, () => {
  if (mapVisible.value && tempLocation.value) {
    tempLocation.value.latitude = coords.value.latitude;
    tempLocation.value.longitude = coords.value.longitude;
  }
});

const locationValid = computed(() => {
  const current = mapVisible.value
    ? tempLocation.value
    : selectedLocation.value;
  return (
    current && typeof current.name === "string" && current.name.trim() !== ""
  );
});

watchEffect(() => {
  createEventStore.isAllowedNext = locationValid.value;

  const current = mapVisible.value
    ? tempLocation.value
    : selectedLocation.value;
  if (locationValid.value && current) {
    createEventStore.location = { ...current };
  }
});
</script>

<template>
  <div class="flex flex-col gap-6">
    <!-- Section Title -->
    <div class="flex items-center gap-3">
      <i class="pi pi-map-marker text-xl" style="color: var(--color-primary);"></i>
      <h3 class="text-xl font-semibold" style="color: var(--color-text);">
        Location Information
      </h3>
    </div>

    <div class="flex gap-3 w-full items-center">
      <!-- Selector Mode -->
      <div v-if="!mapVisible" class="flex-1">
        <label class="admin-form-label">Choose Location</label>
        <select
          v-model="selectedLocation"
          class="admin-select"
        >
          <option :value="null" disabled>Select a location</option>
          <option
            v-for="location in createEventStore.locations"
            :key="location.id"
            :value="location"
          >
            {{ location.name }}
          </option>
        </select>
      </div>

      <!-- Map Input Mode -->
      <div v-else class="flex-1 grid grid-cols-1 md:grid-cols-2 gap-4">
        <div class="admin-form-group">
          <label class="admin-form-label">Location Name</label>
          <input
            v-model="tempLocation.name"
            type="text"
            class="admin-input"
            placeholder="Enter location name"
            autocomplete="off"
          />
        </div>
        <div class="admin-form-group">
          <label class="admin-form-label">Additional Info (Optional)</label>
          <input
            v-model="tempLocation.additionalInfo"
            type="text"
            class="admin-input"
            placeholder="Extra details about location"
            autocomplete="off"
          />
        </div>
      </div>

      <!-- Toggle Buttons -->
      <div class="flex flex-col mt-6 gap-2">
        <button
          v-if="!mapVisible"
          @click="openMap"
          class="admin-btn-primary"
          style="height: 40px; min-width: 120px;"
        >
          <i class="pi pi-plus"></i>
          Add Custom
        </button>
        <button
          v-else
          @click="closeMap"
          class="admin-btn-secondary mb-5"
          style="height: 40px; min-width: 120px;"
        >
          <i class="pi pi-arrow-left"></i>
          Back to List
        </button>
      </div>
    </div>

    <!-- Map View -->
    <div v-if="mapVisible" class="admin-card">
      <div class="flex items-center gap-3 mb-4">
        <i class="pi pi-globe text-lg" style="color: var(--color-primary);"></i>
        <h4 class="text-lg font-medium" style="color: var(--color-text);">
          Select Location on Map
        </h4>
      </div>

      <div class="mb-4">
        <EventLocationMap v-model="coords" />
      </div>

      <div class="text-sm p-3 rounded-lg" style="background-color: var(--color-bg); color: var(--color-gray);">
        <div class="flex items-center gap-2">
          <i class="pi pi-info-circle"></i>
          <span>
            Coordinates: {{ tempLocation.latitude.toFixed(5) }}, {{ tempLocation.longitude.toFixed(5) }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";
</style>
