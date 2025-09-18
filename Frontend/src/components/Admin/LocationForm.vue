<script setup>
import { useCreateEventStore } from "../../stores/createEventStore";
import EventLocationMap from "../Map/EventLocationMap.vue";
import { ref, computed, watch, watchEffect, onMounted } from "vue";
import { Select, Button, InputText, FloatLabel } from "primevue";

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
  <div class="flex flex-col gap-3">
    <span class="text-primary/90 block">Location Info</span>

    <div class="flex gap-3 w-full items-center">
      <!-- Selector Mode -->
      <Select
        v-if="!mapVisible"
        v-model="selectedLocation"
        :options="createEventStore.locations"
        optionLabel="name"
        placeholder="Select Location"
        class="w-full flex-9"
      />

      <!-- Map Input Mode -->
      <div class="flex gap-3 flex-9" v-else>
        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="location-name"
            v-model="tempLocation.name"
            autocomplete="off"
            class="w-full"
          />
          <label for="location-name">Location Name</label>
        </FloatLabel>
        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="location-info"
            v-model="tempLocation.additionalInfo"
            autocomplete="off"
            class="w-full"
          />
          <label for="location-info">Additional Info (Optional)</label>
        </FloatLabel>
      </div>

      <!-- Toggle Buttons -->
      <Button
        icon="pi pi-plus"
        class="w-full flex-1 h-[38px]"
        aria-label="Add"
        @click="openMap"
        v-if="!mapVisible"
      />
      <Button
        icon="pi pi-arrow-left"
        class="w-full flex-1 h-[38px]"
        aria-label="Back"
        @click="closeMap"
        v-else
      />
    </div>

    <!-- Map View -->
    <div class="flex flex-col gap-3" v-if="mapVisible">
      <EventLocationMap v-model="coords" />
      <p>
        Latitude: {{ tempLocation.latitude.toFixed(5) }}, Longitude:
        {{ tempLocation.longitude.toFixed(5) }}
      </p>
    </div>
  </div>
</template>
