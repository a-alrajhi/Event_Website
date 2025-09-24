<script setup>
import AdminSidebar from "./AdminSidebar.vue";
</script>

<template>
  <div class="flex min-h-screen" style="background-color: var(--color-bg);">
    <!-- Sidebar -->
    <aside
      class="w-64 min-h-screen flex flex-col shadow-2xl sticky top-0 self-start admin-scrollbar"
      style="background-color: var(--color-card); color: var(--color-text);"
    >
      <AdminSidebar />
    </aside>

    <!-- Main Content -->
    <main
      class="flex-1 p-8 overflow-y-auto admin-scrollbar"
      style="background-color: var(--color-bg);"
    >
      <router-view />
    </main>
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";
</style>
