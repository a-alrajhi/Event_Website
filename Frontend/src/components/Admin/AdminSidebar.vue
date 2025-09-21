<script setup>
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const links = [
  { label: "Users", path: "/admin/users", icon: "pi pi-users text-xl" },
  { label: "Events", path: "/admin/resources", icon: "pi pi-calendar text-xl" },
  {
    label: "Statistics",
    path: "/admin/statistics",
    icon: "pi pi-chart-bar text-xl",
  },
];

const isActive = (path) => route.path.startsWith(path);
</script>

<template>
  <div class="px-4 py-4 text-3xl font-semibold border-b border-white/40">
    Admin Panel
  </div>
  <nav class="flex-1 p-4">
    <ul class="flex flex-col gap-2">
      <li
        v-for="link in links"
        :key="link.path"
        @click="router.push(link.path)"
        class="flex items-center gap-3 px-4 py-2 rounded cursor-pointer transition"
        :class="isActive(link.path) ? 'bg-primary/60' : 'hover:bg-primary/90 '"
      >
        <i :class="link.icon"></i>
        <span class="text-xl">{{ link.label }}</span>
      </li>
    </ul>
  </nav>
</template>
