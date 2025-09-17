<!-- src/components/EnhancedEventCard.vue -->
<template>
  <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
    <div
      v-for="event in filteredEvents"
      :key="event.id"
      class="bg-[var(--color-card)] rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-[1.02] relative overflow-hidden group"
    >
      <!-- Event Image with Overlay -->
      <div class="relative overflow-hidden">
        <img
          :src="event.image"
          :alt="event.title"
          class="w-full h-48 object-cover group-hover:scale-110 transition-transform duration-300"
        />
        
        <!-- Overlay with quick actions -->
        <div class="absolute inset-0 bg-black/50 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center gap-2">
          <button
            @click="$emit('quick-book', event)"
            class="bg-[var(--color-primary)] hover:bg-[var(--color-hover)] px-4 py-2 rounded-lg font-medium transition-colors text-[var(--color-text)]"
          >
            Quick Book
          </button>
          <button
            @click="$emit('view-details', event)"
            class="bg-[var(--color-bg)] hover:bg-[var(--color-hover)] px-4 py-2 rounded-lg font-medium transition-colors text-[var(--color-text)]"
          >
            View Details
          </button>
        </div>

        <!-- Save/Like Button -->
        <button
          @click="toggleSaved(event)"
          :class="[
            'absolute top-3 right-3 p-2 rounded-full transition-all',
            savedEvents.includes(event.id) ? 'bg-[var(--color-error)] text-[var(--color-text)]' : 'bg-black/30 text-[var(--color-gray)] hover:bg-black/50'
          ]"
        >
          <Heart :class="['w-4 h-4', savedEvents.includes(event.id) && 'fill-current']" />
        </button>

        <!-- Price Badge -->
        <div class="absolute top-3 left-3 bg-[var(--color-primary)] px-2 py-1 rounded-full text-sm font-bold text-[var(--color-text)]">
          {{ event.price === 0 ? 'FREE' : `SAR ${event.price}` }}
        </div>

        <!-- Urgency Indicator -->
        <div v-if="event.spotsLeft && event.spotsLeft < 20" class="absolute bottom-3 left-3 bg-[var(--color-error)] px-2 py-1 rounded-full text-xs font-medium animate-pulse text-[var(--color-text)]">
          Only {{ event.spotsLeft }} spots left!
        </div>
      </div>

      <!-- Event Info -->
      <div class="p-4">
        <!-- Event Title & Rating -->
        <div class="flex justify-between items-start mb-2">
          <h3 class="text-lg font-semibold text-[var(--color-text)] group-hover:text-[var(--color-primary)] transition-colors">
            {{ event.title }}
          </h3>
          <div v-if="event.rating" class="flex items-center gap-1 text-sm">
            <Star class="w-4 h-4 fill-[var(--color-primary)] text-[var(--color-primary)]" />
            <span class="text-[var(--color-gray)]">{{ event.rating }}</span>
          </div>
        </div>

        <!-- Event Details -->
        <div class="space-y-2 mb-3">
          <div class="flex items-center gap-2 text-sm text-[var(--color-gray)]">
            <Calendar class="w-4 h-4 text-[var(--color-primary)]" />
            <span>{{ formatDate(event.date) }}</span>
            <Clock class="w-4 h-4 text-[var(--color-primary)] ml-2" />
            <span>{{ event.time }}</span>
          </div>
          
          <div class="flex items-center gap-2 text-sm text-[var(--color-gray)]">
            <MapPin class="w-4 h-4 text-[var(--color-primary)]" />
            <span>{{ event.venue }}</span>
          </div>

          <div class="flex items-center gap-2 text-sm text-[var(--color-gray)]">
            <Users class="w-4 h-4 text-[var(--color-primary)]" />
            <span>{{ event.attendees || 0 }} attending</span>
          </div>
        </div>

        <!-- Event Description -->
        <p class="text-[var(--color-gray)] text-sm mb-3 line-clamp-2">{{ event.description }}</p>

        <!-- Organizer Info -->
        <div v-if="event.organizer" class="flex items-center gap-2 mb-3 text-xs text-[var(--color-gray)]">
          <img :src="event.organizer.avatar" :alt="event.organizer.name" class="w-5 h-5 rounded-full" />
          <span>by {{ event.organizer.name }}</span>
          <div v-if="event.organizer.verified" class="bg-blue-500 rounded-full p-0.5">
            <Check class="w-2.5 h-2.5 text-[var(--color-text)]" />
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="flex gap-2">
          <button
            @click="$emit('book-now', event)"
            :disabled="event.soldOut"
            :class="[
              'flex-1 py-2 px-4 rounded-lg font-medium transition-all text-center',
              event.soldOut
                ? 'bg-[var(--color-bg)] text-[var(--color-gray)] cursor-not-allowed'
                : 'bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-[var(--color-text)] hover:shadow-lg'
            ]"
          >
            {{ event.soldOut ? 'Sold Out' : 'Book Now' }}
          </button>
          
          <button
            @click="shareEvent(event)"
            class="p-2 border border-[var(--color-gray)]/30 rounded-lg hover:bg-[var(--color-hover)] transition-colors"
          >
            <Share2 class="w-4 h-4 text-[var(--color-text)]" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { Heart, Share2, MapPin, Calendar, Clock, Users, Star, Check } from 'lucide-vue-next';
import { getEvents } from '../../apis/EventDetalisApi';

const events = ref([]);
const savedEvents = ref([]);

onMounted(async () => {
  const fetchedEvents = await getEvents();
  events.value = fetchedEvents.map(event => ({
    ...event,
    date: event.date || '2025-10-15',
    time: event.time || 'TBD',
    venue: event.venue || 'Saudi Arabia',
    rating: event.rating || 4.5,
    attendees: event.attendees || 100,
    spotsLeft: event.spotsLeft || 50,
  }));
});

const filteredEvents = computed(() => {
  return events.value;
});

const emit = defineEmits(['quick-book', 'view-details', 'book-now', 'toggle-save']);

const toggleSaved = (event) => {
  const isSaved = savedEvents.value.includes(event.id);
  if (isSaved) {
    savedEvents.value = savedEvents.value.filter(id => id !== event.id);
  } else {
    savedEvents.value.push(event.id);
  }
  emit('toggle-save', { event, saved: !isSaved });
};

const shareEvent = (event) => {
  if (navigator.share) {
    navigator.share({
      title: event.title,
      text: event.description,
      url: window.location.href,
    });
  } else {
    navigator.clipboard.writeText(window.location.href);
    alert('Link copied to clipboard!');
  }
};

const formatDate = (date) => {
  return date
    ? new Date(date).toLocaleDateString('en-US', {
        month: 'short',
        day: 'numeric',
        weekday: 'short',
      })
    : 'TBD';
};
</script>

<style >

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>