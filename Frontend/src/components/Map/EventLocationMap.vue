<script setup>
import { ref, watch, onMounted } from "vue";
import L from "leaflet";

const props = defineProps({
  modelValue: {
    type: Object, // { latitude: Number, longitude: Number }
    default: () => ({ latitude: 24.7136, longitude: 46.6753 }), // Riyadh default
  },
  visible: {
    type: Boolean,
    default: true,
  },
});

const emit = defineEmits(["update:modelValue"]);

let map = null;
let marker = null;

const mapContainer = ref(null);

onMounted(() => {
  map = L.map(mapContainer.value).setView(
    [props.modelValue.latitude, props.modelValue.longitude],
    13
  );

  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
  }).addTo(map);

  marker = L.marker([props.modelValue.latitude, props.modelValue.longitude], {
    draggable: true,
  }).addTo(map);

  marker.on("dragend", (event) => {
    const latLng = event.target.getLatLng();
    emit("update:modelValue", { latitude: latLng.lat, longitude: latLng.lng });
  });

  map.on("click", (e) => {
    marker.setLatLng(e.latlng);
    emit("update:modelValue", {
      latitude: e.latlng.lat,
      longitude: e.latlng.lng,
    });
  });
});

watch(
  () => props.modelValue,
  (newVal) => {
    if (marker) {
      marker.setLatLng([newVal.latitude, newVal.longitude]);
      map.setView([newVal.latitude, newVal.longitude], 13);
    }
  }
);
</script>

<template>
  <div
    ref="mapContainer"
    style="
      height: 400px;
      width: 100%;
      border-radius: 8px;
      border: 1px solid #ccc;
    "
    :class="visible ? '' : 'hidden'"
  ></div>
</template>