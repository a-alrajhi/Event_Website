<script setup>
import LeafletMap from "./components/Map/LeafletMap.vue";
import EventLocationMap from "./components/Event/EventLocationMap.vue";
import EventDetailsPage from "./Pages/EventDetailsPage.vue";
</script>


<template>
  <div class="min-h-screen flex flex-col">
    <main class="flex-grow">
     <router-view />
<!--
     <Dashboard />
-->
<!--      <leafletMap />-->

    </main>
  </div>
</template>
