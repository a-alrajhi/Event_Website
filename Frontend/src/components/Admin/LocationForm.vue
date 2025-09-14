<script setup>
import { useCreateEventStore } from "../../stores/createEventStore";
import EventLocationMap from "../Map/EventLocationMap.vue";
import { ref, computed, watch } from "vue";
import { Select, Button, InputText, FloatLabel } from "primevue";

const createEventStore = useCreateEventStore();

const mapVisible = ref(false);
const error = ref(false);

const openMap = () => {
  createEventStore.location = {
    name: "",
    latitude: 24.7136,
    longitude: 46.6753,
  };
  mapVisible.value = true;
};

const closeMap = () => {
  createEventStore.location = null;
  mapVisible.value = false;
};

const locationValid = computed(() => {
  return (
    createEventStore.location &&
    createEventStore.location.name &&
    createEventStore.location.name.trim() !== ""
  );
});

watch(
  () => createEventStore.location,
  () => {
    error.value = !locationValid.value;
    createEventStore.isAllowedNext = locationValid.value;
  },
  { deep: true }
);
</script>

<template>
  <div class="flex flex-col gap-3">
    <span class="text-primary/90 block">Location Info</span>

    <div class="flex gap-3 w-full items-center">
      <Select
        v-if="!mapVisible"
        v-model="createEventStore.location"
        :options="createEventStore.locations"
        optionLabel="name"
        placeholder="Select Location"
        class="w-full flex-9"
      />
      <FloatLabel variant="on" class="flex-9" v-else>
        <InputText
          id="name"
          v-model="createEventStore.location.name"
          autocomplete="off"
          class="w-full"
        />
        <label for="name">Location Name</label>
      </FloatLabel>
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
    <p v-if="error" class="text-red-500 text-sm mt-1">
      Select or enter a location name
    </p>
    <div class="flex flex-col gap-3" v-if="mapVisible">
      <EventLocationMap v-model="createEventStore.location" />
      <p>
        Latitude: {{ createEventStore.location.latitude.toFixed(5) }},
        Longitude: {{ createEventStore.location.longitude.toFixed(5) }}
      </p>
    </div>
  </div>
</template>
