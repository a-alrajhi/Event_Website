<script setup>
// import Home from './Pages/Home.vue';
 import EventBrowse from './Pages/EventBrowse.vue';

</script>


<template>
  <div class="min-h-screen flex flex-col">
    <main class="flex-grow">
      <router-view />
       <!-- <Home/> -->
        <EventBrowse/>
      

    </main>
  </div>
</template>
