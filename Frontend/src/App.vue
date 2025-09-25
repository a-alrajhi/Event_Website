<script setup>
import AdminLayoutWrapper from "./components/Admin/AdminLayoutWrapper.vue";
import { useRoute } from "vue-router";

const route = useRoute();
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div v-if="route.fullPath.includes('/admin')">
      <AdminLayoutWrapper />
    </div>
    <main class="flex-grow" v-else>
 <router-view />
<!--      <leaflet-map />-->
    </main>
  </div>
</template>
