<script setup>
<<<<<<< HEAD
// import Home from './Pages/Home.vue';
 import EventBrowse from './Pages/EventBrowse.vue';

</script>
=======
import AdminLayoutWrapper from "./components/Admin/AdminLayoutWrapper.vue";
import { useRoute } from "vue-router";
>>>>>>> 22bfa572dc39378e976dc4d2ba12cd7394615925

const route = useRoute();
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <div v-if="route.fullPath.includes('/admin')">
      <AdminLayoutWrapper />
    </div>
    <main class="flex-grow" v-else>
      <router-view />
<<<<<<< HEAD
       <!-- <Home/> -->
        <EventBrowse/>
      

=======
>>>>>>> 22bfa572dc39378e976dc4d2ba12cd7394615925
    </main>
  </div>
</template>
