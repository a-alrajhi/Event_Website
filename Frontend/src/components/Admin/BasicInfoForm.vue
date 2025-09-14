<script setup>
import { InputText } from "primevue";
</script>

<template>
  <span class="text-surface-500 dark:text-surface-500 block mb-8"
    >Basic Info</span
  >
  <div class="flex items-center gap-4 mb-4">
    <label for="username" class="font-semibold w-24">Event Name</label>
    <InputText id="username" class="flex-auto" autocomplete="off" />
  </div>
  <div class="flex items-center gap-4 mb-8">
    <label for="email" class="font-semibold w-24">Event Arabic Name</label>
    <InputText id="email" class="flex-auto" autocomplete="off" />
  </div>
</template>
