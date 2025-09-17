<script setup>
import Dashboard   from "./Pages/Dashboard.vue";
import LeafletMap from "./components/Map/LeafletMap.vue";

</script>


<template>
  <div class="min-h-screen flex flex-col">
    <main class="flex-grow">
     <router-view />
     <Dashboard />
      <leafletMap />

    </main>
  </div>
</template>
