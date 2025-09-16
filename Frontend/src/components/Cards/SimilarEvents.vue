<!-- src/components/SimilarEvents.vue -->
<template>
  <div class="bg-[var(--color-card)] rounded-xl p-6">
    <h3 class="text-xl font-bold text-[var(--color-primary)] mb-4">Similar Events You Might Like</h3>
    
    <div class="space-y-3">
      <div
        v-for="similar in similarEvents"
        :key="similar.id"
        class="flex gap-3 p-3 bg-[var(--color-bg)]/30 rounded-lg hover:bg-[var(--color-bg)]/50 transition-colors cursor-pointer"
        @click="$emit('select-event', similar)"
      >
        <img :src="similar.image" :alt="similar.title" class="w-16 h-16 rounded-lg object-cover" />
        <div class="flex-1">
          <h4 class="font-medium text-[var(--color-text)] mb-1">{{ similar.title }}</h4>
          <p class="text-sm text-[var(--color-gray)] mb-1">{{ similar.venue }}</p>
          <p class="text-sm text-[var(--color-primary)] font-semibold">
            {{ similar.price === 0 ? 'FREE' : `SAR ${similar.price}` }}
          </p>
        </div>
        <div class="text-right">
          <p class="text-xs text-[var(--color-gray)]">{{ formatDate(similar.date) }}</p>
          <button class="text-xs text-[var(--color-primary)] hover:underline mt-1">
            View Details
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

defineProps({
  currentEvent: Object,
});

const emit = defineEmits(['select-event']);

const similarEvents = ref([
  {
    id: 1,
    title: 'Rock Concert Night',
    venue: 'Riyadh Arena',
    price: 150,
    date: '2025-10-15',
    image: 'https://images.ctfassets.net/vy53kjqs34an/6d2v6RY1V5tIANuuxuiMkg/66a11289e338311d7f0f35745a6589c6/1280x1280__compressed.jpg?fm=webp&fit=thumb&w=600&h=600',
  },
  {
    id: 2,
    title: 'Jazz Evening',
    venue: 'Cultural Center',
    price: 0,
    date: '2025-10-20',
    image: 'https://images.ctfassets.net/vy53kjqs34an/6d2v6RY1V5tIANuuxuiMkg/66a11289e338311d7f0f35745a6589c6/1280x1280__compressed.jpg?fm=webp&fit=thumb&w=600&h=600',
  },
  {
    id: 3,
    title: 'Electronic Music Festival',
    venue: 'Boulevard City',
    price: 200,
    date: '2025-10-25',
    image: 'https://images.ctfassets.net/vy53kjqs34an/6d2v6RY1V5tIANuuxuiMkg/66a11289e338311d7f0f35745a6589c6/1280x1280__compressed.jpg?fm=webp&fit=thumb&w=600&h=600',
  },
]);

const formatDate = (date) => {
  return new Date(date).toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
  });
};
</script>

<style >

</style>