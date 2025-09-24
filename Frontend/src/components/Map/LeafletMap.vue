<template>
  <div class="leaflet-map-container">
    <div id="map" class="map-style"></div>

    <div v-if="showEventList && events.length > 0" class="event-list-popup">
      <div class="popup-content">
        <button class="close-btn" @click="showEventList = false">✖</button>
        <EventList :events="events" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import "leaflet/dist/leaflet.css";
import * as L from 'leaflet';
import 'leaflet.markercluster/dist/MarkerCluster.css';
import 'leaflet.markercluster/dist/MarkerCluster.Default.css';
import "leaflet.markercluster";
import icon from '../../assets/icon.png';
import { useRouter } from 'vue-router';
import EventList from '../Misc/EventList.vue';

const router = useRouter();
const initialMap = ref(null);
const events = ref([]);
const showEventList = ref(false);

const myIcon = L.icon({
  iconUrl: icon,
  iconSize: [30, 30],
  iconAnchor: [15, 30],
  popupAnchor: [0, -30],
});

onMounted(() => {
  initialMap.value = L.map('map', {
    zoomControl: true,
    zoom: 10,
    zoomAnimation: false,
    fadeAnimation: true,
    markerZoomAnimation: true
  }).setView([24.7136, 46.6753], 10);  // Default view: Riyadh

  L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(initialMap.value);

  // Fetch all locations
  axios.get('http://localhost:8080/locations/All')
      .then(response => {
        const locations = response.data;
        const markers = L.markerClusterGroup();

        locations.forEach(location => {
          const each_marker = new L.marker([location.latitude, location.longitude], { icon: myIcon })
              .bindPopup(`<strong>${location.name}</strong><br>${location.address}`)
              .on('click', () => {
                axios.get(`http://localhost:8080/locations/${location.id}/events`)
                    .then(eventsResponse => {
                      events.value = eventsResponse.data;
                      if (events.value.length > 0) {
                        showEventList.value = true;
                      }
                    })
                    .catch(error => {
                      console.error('Error loading events:', error);
                    });
              });

          markers.addLayer(each_marker);
        });

        initialMap.value.addLayer(markers);
      })
      .catch(error => {
        console.error('Error loading locations:', error);
      });
});
</script>

<style scoped>
.leaflet-map-container {
  position: relative;
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.map-style {
  height: 100%;
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.event-list-popup {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.popup-content {
  background-color: white;
  border-radius: 10px;
  padding: 20px;
  max-width: 90%;
  max-height: 80%;
  overflow-y: auto;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  position: relative;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 15px;
  color: #333;
  cursor: pointer;
}

.close-btn:hover {
  color: red;
}
</style>