<script setup>
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const links = [
  { label: "Users", path: "/admin/users", icon: "pi pi-users" },
  { label: "Events", path: "/admin/resources", icon: "pi pi-calendar" },
  {
    label: "Statistics",
    path: "/admin/statistics",
    icon: "pi pi-chart-bar",
  },
];

const isActive = (path) => route.path.startsWith(path);
</script>

<template>
  <!-- Header -->
  <div
    class="px-6 py-6 border-b font-semibold text-2xl flex items-center gap-3"
    style="border-color: var(--color-gray); color: var(--color-text);"
  >
    <i class="pi pi-cog text-primary"></i>
    Admin Panel
  </div>

  <!-- Navigation -->
  <nav class="flex-1 p-4">
    <ul class="flex flex-col gap-2">
      <li
        v-for="link in links"
        :key="link.path"
        @click="router.push(link.path)"
        class="group cursor-pointer transition-all duration-200 rounded-xl"
      >
        <div
          class="flex items-center gap-4 px-4 py-4 rounded-xl transition-all duration-200"
          :class="isActive(link.path)
            ? 'admin-nav-active'
            : 'admin-nav-item'"
        >
          <i
            :class="link.icon + ' text-lg'"
            :style="isActive(link.path)
              ? 'color: var(--color-text);'
              : 'color: var(--color-gray);'"
          ></i>
          <span
            class="text-lg font-medium"
            :style="isActive(link.path)
              ? 'color: var(--color-text);'
              : 'color: var(--color-gray);'"
          >
            {{ link.label }}
          </span>
        </div>
      </li>
    </ul>
  </nav>

  <!-- Footer -->
  <div
    class="p-4 border-t text-center text-sm"
    style="border-color: var(--color-gray); color: var(--color-gray);"
  >
    Event Management System
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";

.admin-nav-item {
  background-color: transparent;
}

.admin-nav-item:hover {
  background-color: var(--color-primary);
  transform: translateX(4px);
}

.admin-nav-item:hover i,
.admin-nav-item:hover span {
  color: var(--color-text) !important;
}

.admin-nav-active {
  background-color: var(--color-primary);
  box-shadow: 0 4px 12px rgba(139, 69, 255, 0.3);
}

.text-primary {
  color: var(--color-primary);
}
</style>
