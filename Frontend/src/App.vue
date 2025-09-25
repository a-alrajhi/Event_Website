<script setup>
import AdminLayoutWrapper from "./components/Admin/AdminLayoutWrapper.vue";
import { useRoute } from "vue-router";
import LeafletMap from "./components/Map/LeafletMap.vue";

const route = useRoute();
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div v-if="route.path.split('/')[1] === 'admin'">
      <AdminLayoutWrapper />
    </div>
    <main class="flex-grow" v-else>
 <router-view />
<!--      <leaflet-map />-->
    </main>
  </div>
</template>
