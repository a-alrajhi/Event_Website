<!-- EventCard.vue -->
<template>
  <div class="bg-white shadow-md rounded-xl overflow-hidden">
    <!-- Image -->
    <img
      :src="event.photoUrl"
      :alt="event.name"
      class="h-48 w-full object-cover"
    />

    <div class="p-4">
      <!-- Common Info -->
      <h3 class="text-lg font-semibold text-gray-800">{{ event.name }}</h3>
      <p class="text-sm text-gray-600 line-clamp-2 mt-1">
        {{ event.description }}
      </p>

      <!-- Default (like similar events list) -->
      <RouterLink
        v-if="variant === 'default'"
        :to="`/event/${event.id}`"
        class="cursor-pointer mt-3 inline-block text-sm text-blue-600 hover:underline"
      >
        View Event →
      </RouterLink>

      <!-- Payment Variant -->
      <div v-else-if="variant === 'payment'"></div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  event: { type: Object, required: true },
  variant: { type: String, default: "default" },
});
</script>
