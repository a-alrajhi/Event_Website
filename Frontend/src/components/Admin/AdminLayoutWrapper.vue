<script setup>
import AdminSidebar from "./AdminSidebar.vue";
</script>

<template>
  <div class="flex min-h-screen">
    <!-- Make sidebar grow with content height -->
    <aside
      class="w-64 text-white flex flex-col shadow-lg sticky top-0 self-start"
    >
      <AdminSidebar />
    </aside>

    <!-- Content -->
    <main class="flex-1 bg-gray-50 p-6 overflow-y-auto">
      <router-view />
    </main>
  </div>
</template>
