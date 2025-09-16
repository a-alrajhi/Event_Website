<script setup>
import { useCreateEventStore } from "../../stores/createEventStore";
import EventLocationMap from "../Map/EventLocationMap.vue";
import { ref, computed, watch, watchEffect, onMounted } from "vue";
import { Select, Button, InputText, FloatLabel } from "primevue";

const createEventStore = useCreateEventStore();

onMounted(async () => {
  await createEventStore.loadLocations();
});

const mapVisible = ref(false);
const coords = ref({ latitude: 24.7136, longitude: 46.6753 });

const openMap = () => {
  if (!createEventStore.location) {
    createEventStore.location = {
      name: "",
      additionalInfo: "",
      latitude: 24.7136,
      longitude: 46.6753,
    };
  }
  coords.value = {
    latitude: createEventStore.location.latitude,
    longitude: createEventStore.location.longitude,
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
    typeof createEventStore.location.name === "string" &&
    createEventStore.location.name.trim() !== ""
  );
});

watchEffect(() => {
  createEventStore.isAllowedNext = locationValid.value;
});

watch(coords, () => {
  if (mapVisible.value) {
    createEventStore.location = {
      ...createEventStore.location,
      ...coords.value,
    };
  }
});
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
      <div class="flex gap-3 flex-9" v-else>
        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="location-name"
            v-model="createEventStore.location.name"
            autocomplete="off"
            class="w-full"
          />
          <label for="location-name">Location Name</label>
        </FloatLabel>
        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="location-info"
            v-model="createEventStore.location.additionalInfo"
            autocomplete="off"
            class="w-full"
          />
          <label for="location-info">Additional Info (Optional)</label>
        </FloatLabel>
      </div>

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
    <div class="flex flex-col gap-3" v-if="mapVisible">
      <EventLocationMap v-model="coords" />
      <p>
        Latitude: {{ createEventStore.location.latitude.toFixed(5) }},
        Longitude: {{ createEventStore.location.longitude.toFixed(5) }}
      </p>
    </div>
  </div>
</template>
