<template>
  <div>
    <div id="map" style="height:90vh;"></div>
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
import markerShadow from '../../assets/marker-shadow.png';

const initialMap = ref(null);

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
  }).setView([24.7136, 46.6753], 10);  // select Riyadh location

  L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(initialMap.value);

  axios.get('http://localhost:8080/locations/All')
      .then(response => {
        const locations = response.data;
        const markers = L.markerClusterGroup();

        locations.forEach(location => {
          const each_marker = new L.marker([location.latitude, location.longitude], { icon: myIcon })
              .bindPopup(`<strong>${location.name}</strong><br>${location.address}`);
          markers.addLayer(each_marker);
        });

        initialMap.value.addLayer(markers);
      })
      .catch(error => {
        console.error('Error loading locations:', error);
      });
});
</script>
