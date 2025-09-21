<template>
  <Card class="p-2 rounded-md shadow-sm min-w-8 bg-white dark:bg-gray-800">
    <template #title>
      <h2
        class="text-xs font-medium text-gray-500 dark:text-gray-400 uppercase tracking-wide"
      >
        {{ props.title }}
      </h2>
    </template>
    <template #content>
      <p class="text-lg font-bold text-gray-900 dark:text-white">
        {{ props.stat }}
      </p>
    </template>
  </Card>
</template>

<script setup>
import { Card } from "primevue";
const props = defineProps({
  title: {
    required: true,
    type: String,
  },
  stat: {
    default: 0,
  },
});
</script>
