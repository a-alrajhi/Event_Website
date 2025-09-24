<!-- src/components/EnhancedEventCard.vue -->
<template>
  <!-- Loading State -->
  <div
    v-if="isLoading"
    class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
  >
    <div
      v-for="i in 6"
      :key="i"
      class="event-card rounded-xl shadow-lg animate-pulse"
    >
      <div class="w-full h-48 bg-gray-200 dark:bg-gray-600 rounded-t-xl"></div>
      <div class="p-4 space-y-3">
        <div class="h-4 bg-gray-200 dark:bg-gray-600 rounded w-3/4"></div>
        <div class="h-3 bg-gray-200 dark:bg-gray-600 rounded w-1/2"></div>
        <div class="h-3 bg-gray-200 dark:bg-gray-600 rounded w-full"></div>
        <div class="h-8 bg-gray-200 dark:bg-gray-600 rounded"></div>
      </div>
    </div>
  </div>

  <!-- Events Grid -->
  <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
    <div
      v-for="event in events"
      :key="event.id"
      class="event-card rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-[1.02] relative overflow-hidden group"
    >
      <!-- Event Image with Overlay -->
      <div class="relative overflow-hidden">
        <img
          :src="event.image"
          :alt="event.title"
          class="w-full h-48 object-cover group-hover:scale-110 transition-transform duration-300"
          @error="handleImageError"
        />

        <!-- Overlay with quick actions -->
        <div
          class="absolute inset-0 bg-black/50 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center gap-2"
        >
          <button
            @click="$emit('quick-book', event)"
            class="bg-[var(--color-primary)] hover:bg-[var(--color-hover)] px-4 py-2 rounded-lg font-medium transition-colors text-white"
          >
            Quick Book
          </button>
          <button
            @click="$emit('view-details', event)"
            class="bg-white/20 hover:bg-white/30 backdrop-blur-sm px-4 py-2 rounded-lg font-medium transition-colors text-white border border-white/30"
          >
            View Details
          </button>
        </div>

        <!-- Save/Like Button -->
        <button
          v-if="authStore.isLoggedIn()"
          @click="toggleSaved(event)"
          :class="[
            'absolute top-3 right-3 p-2 rounded-full transition-all',
            savedEvents.includes(event.id)
              ? 'bg-[var(--color-error)] text-[var(--color-text)]'
              : 'bg-black/30 text-[var(--color-gray)] hover:bg-black/50',
          ]"
        >
          <Heart
            :class="[
              'w-4 h-4',
              savedEvents.includes(event.id) && 'fill-current',
            ]"
          />
        </button>

        <!-- Price Badge -->
        <div
          class="absolute top-3 left-3 bg-[var(--color-primary)] px-3 py-1 rounded-full text-sm font-bold text-white shadow-lg"
        >
          {{ event.price === 0 ? "FREE" : `SAR ${event.price}` }}
        </div>

        <!-- Category Badge -->
        <div
          v-if="event.category"
          class="absolute bottom-3 right-3 bg-[var(--color-secondary)]/90 px-2 py-1 rounded-full text-xs font-medium text-white"
        >
          {{ event.category }}
        </div>

        <!-- Urgency Indicator -->
        <div
          v-if="event.spotsLeft && event.spotsLeft < 20"
          class="absolute bottom-3 left-3 bg-[var(--color-error)] px-2 py-1 rounded-full text-xs font-medium animate-pulse text-white"
        >
          Only {{ event.spotsLeft }} spots left!
        </div>
      </div>

      <!-- Event Info -->
      <div class="p-4">
        <!-- Event Title & Rating -->
        <div class="flex justify-between items-start mb-2">
          <h3
            class="text-lg font-semibold text-gray-900 dark:text-white group-hover:text-[var(--color-primary)] transition-colors line-clamp-1"
          >
            {{ event.title }}
          </h3>
          <div
            v-if="event.rating"
            class="flex items-center gap-1 text-sm ml-2 flex-shrink-0"
          >
            <Star
              class="w-4 h-4 fill-[var(--color-primary)] text-[var(--color-primary)]"
            />
            <span class="text-gray-600 dark:text-gray-400">{{
              event.rating
            }}</span>
          </div>
        </div>

        <!-- Event Details -->
        <div class="space-y-2 mb-3">
          <div
            class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400"
          >
            <Calendar
              class="w-4 h-4 text-[var(--color-primary)] flex-shrink-0"
            />
            <span>{{ formatDate(event.date) }}</span>
            <Clock
              class="w-4 h-4 text-[var(--color-primary)] ml-2 flex-shrink-0"
            />
            <span>{{ event.time }}</span>
          </div>

          <div
            class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400"
          >
            <MapPin class="w-4 h-4 text-[var(--color-primary)] flex-shrink-0" />
            <span class="truncate">{{ event.venue }}</span>
          </div>

          <div
            class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-400"
          >
            <Users class="w-4 h-4 text-[var(--color-primary)] flex-shrink-0" />
            <span>{{ event.attendees || 0 }} attending</span>
          </div>
        </div>

        <!-- Event Description -->
        <p class="text-gray-600 dark:text-gray-400 text-sm mb-3 line-clamp-2">
          {{ event.description }}
        </p>

        <!-- Organizer Info -->
        <div
          v-if="event.organizer"
          class="flex items-center gap-2 mb-3 text-xs text-gray-600 dark:text-gray-400"
        >
          <img
            :src="event.organizer.avatar"
            :alt="event.organizer.name"
            class="w-5 h-5 rounded-full"
            @error="handleAvatarError"
          />
          <span>by {{ event.organizer.name }}</span>
          <div
            v-if="event.organizer.verified"
            class="bg-blue-500 rounded-full p-0.5"
          >
            <Check class="w-2.5 h-2.5 text-white" />
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
                ? 'bg-gray-100 dark:bg-gray-700 text-gray-500 dark:text-gray-400 cursor-not-allowed'
                : 'bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white hover:shadow-lg',
            ]"
          >
            {{ event.soldOut ? "Sold Out" : "Book Now" }}
          </button>

          <button
            @click="shareEvent(event)"
            class="p-2 border border-gray-300 dark:border-gray-600 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors"
            :title="`Share ${event.title}`"
          >
            <Share2 class="w-4 h-4 text-gray-600 dark:text-gray-400" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import {
  Heart,
  Share2,
  MapPin,
  Calendar,
  Clock,
  Users,
  Star,
  Check,
} from "lucide-vue-next";
import { bookmark, unbookmark } from "../../apis/bookmarkapi";
import { useAuthStore } from "../../stores/authStore";

// Props
const props = defineProps({
  events: {
    type: Array,
    default: () => [],
    default: () => [],
  },
  isLoading: {
    type: Boolean,
    default: false,
  },
});

// State
const savedEvents = ref([]);

// Emits
const emit = defineEmits([
  "quick-book",
  "view-details",
  "book-now",
  "toggle-save",
  "clear-filters",
]);

const authStore = useAuthStore();
// Methods
const formatDate = (date) => {
  if (!date) return "TBD";

  try {
    return new Date(date).toLocaleDateString("en-US", {
      month: "short",
      day: "numeric",
      weekday: "short",
    });
  } catch {
    return "TBD";
  }
};

const toggleSaved = (event) => {
  const isSaved = savedEvents.value.includes(event.id);
  if (isSaved) {
    savedEvents.value = savedEvents.value.filter((id) => id !== event.id);
    unbookmark(event.id);
  } else {
    savedEvents.value.push(event.id);
    bookmark(event.id);
  }
  emit("toggle-save", { event, saved: !isSaved });
};
</script>

<style scoped>
.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
