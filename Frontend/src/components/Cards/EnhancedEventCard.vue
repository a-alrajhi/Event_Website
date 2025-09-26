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
      <div class="w-full h-48 bg-gray-200 dark:bg-gray-700 rounded-t-xl"></div>
      <div class="p-4 space-y-3">
        <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-3/4"></div>
        <div class="h-3 bg-gray-200 dark:bg-gray-700 rounded w-1/2"></div>
        <div class="h-3 bg-gray-200 dark:bg-gray-700 rounded w-full"></div>
        <div class="h-8 bg-gray-200 dark:bg-gray-700 rounded"></div>
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

        <!-- Overlay with single View Details button -->
        <div
          class="absolute inset-0 bg-black/50 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center"
        >
          <button
            @click.stop="navigateToDetails(event.id)"
            class="bg-[var(--color-primary)] hover:bg-[var(--color-hover)] px-6 py-3 rounded-lg font-medium transition-colors text-white"
          >
            View Details
          </button>
        </div>

        <!-- Save/Like Button -->
        <button
          @click.stop="toggleSaved(event)"
          :disabled="!authStore.isLoggedIn()"
          :class="[
            'absolute top-3 right-3 p-2 rounded-full transition-all',
            !authStore.isLoggedIn()
              ? 'bg-gray-300/50 text-gray-400 cursor-not-allowed'
              : savedEvents.includes(event.id)
                ? 'bg-[var(--color-error)] text-white hover:bg-[var(--color-error)]/80'
                : 'bg-black/30 text-gray-400 hover:bg-black/50 hover:text-white',
          ]"
          :title="
            !authStore.isLoggedIn
              ? 'Please login to save events'
              : savedEvents.includes(event.id)
                ? 'Remove from favorites'
                : 'Add to favorites'
          "
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
          {{ formatPrice(event) }}
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
            <span class="text-gray-600 dark:text-gray-300">{{
              event.rating
            }}</span>
          </div>
        </div>

        <!-- Event Details -->
        <div class="space-y-2 mb-3">
          <div
            class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-300"
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
            class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-300"
          >
            <MapPin class="w-4 h-4 text-[var(--color-primary)] flex-shrink-0" />
            <span class="truncate">{{ event.venue }}</span>
          </div>

          <div
            v-if="event.attendees > 20 || event.spotsLeft < 20"
            class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-300"
          >
            <Users class="w-4 h-4 text-[var(--color-primary)] flex-shrink-0" />
            <span>{{ event.attendees || 0 }} attending</span>
          </div>
          <div
            v-else
            class="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-300"
          >
            <Users class="w-4 h-4 text-[var(--color-primary)] flex-shrink-0" />
            <span>Book this event early!</span>
          </div>
        </div>

        <!-- Event Description -->
        <p class="text-gray-600 dark:text-gray-300 text-sm mb-3 line-clamp-2">
          {{ event.description }}
        </p>

        <!-- Organizer Info -->
        <div
          v-if="event.organizer"
          class="flex items-center gap-2 mb-3 text-xs text-gray-600 dark:text-gray-300"
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
            @click.stop="navigateToTickets(event.id)"
            :disabled="event.soldOut"
            :class="[
              'flex-1 py-2 px-4 rounded-lg font-medium transition-all text-center',
              event.soldOut
                ? 'bg-gray-200 dark:bg-gray-700 text-gray-500 cursor-not-allowed'
                : 'bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white hover:shadow-lg',
            ]"
          >
            {{ event.soldOut ? "Sold Out" : "Book Now" }}
          </button>

          <button
            @click.stop="shareEvent(event)"
            class="p-2 border border-gray-300/30 dark:border-gray-600/30 rounded-lg hover:bg-[var(--color-hover)] transition-colors"
            :title="`Share ${event.title}`"
          >
            <Share2 class="w-4 h-4 text-gray-600 dark:text-gray-300" />
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
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
  },
  isLoading: {
    type: Boolean,
    default: false,
  },
});

// Router and Auth
const router = useRouter();
const authStore = useAuthStore();

// State
const savedEvents = ref([]);

// Emits
const emit = defineEmits(["toggle-save"]);

// Load saved events from localStorage
onMounted(() => {
  const saved = localStorage.getItem("savedEvents");
  if (saved) {
    savedEvents.value = JSON.parse(saved);
  }
});

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

const formatPrice = (event) => {
  if (event.price === 0) return "FREE";

  // If there's a price range with multiple prices, show range
  if (event.priceRange && event.priceRange.length > 1) {
    const prices = event.priceRange
      .map((p) => parseFloat(p) || 0)
      .filter((p) => p > 0);
    if (prices.length > 1) {
      const min = Math.min(...prices);
      const max = Math.max(...prices);
      if (min !== max) {
        return `SAR ${min} - ${max}`;
      }
    }
  }

  // Default single price display
  return `SAR ${event.price}`;
};

const navigateToDetails = (eventId) => {
  router.push(`/events/${eventId}`);
};

const navigateToTickets = (eventId) => {
  handleBookNow(eventId);
};

const handleBookNow = (eventId) => {
  const event = props.events.find((e) => e.id === eventId);
  const slots = event?.slots || [];

  if (slots.length > 0) {
    // If event has slots, go to event details page (which contains EventSidebar)
    navigateToDetails(eventId);
  } else {
    // If no slots, go directly to ticket types page
    router.push(`/events/ticket-types/${eventId}`);
  }
};

// Note: goToTicketPage function removed as it's no longer needed
// EventSidebar.vue now handles slot selection and routing

const toggleSaved = (event) => {
  // Check if user is logged in
  if (!authStore.isLoggedIn) {
    // Show a notification or redirect to login
    if (
      confirm("You need to login to save events. Would you like to login now?")
    ) {
      router.push("/login");
    }
    return;
  }

  const index = savedEvents.value.indexOf(event.id);
  if (index > -1) {
    savedEvents.value.splice(index, 1);
    unbookmark(event.id);
  } else {
    savedEvents.value.push(event.id);
    bookmark(event.id);
  }

  // Save to localStorage
  localStorage.setItem("savedEvents", JSON.stringify(savedEvents.value));

  //emit('toggle-save', { event, saved: !isSaved });
};

const shareEvent = async (event) => {
  const shareData = {
    title: event.title,
    text: `Check out this event: ${event.title}`,
    url: `${window.location.origin}/events/${event.id}`,
  };

  try {
    if (navigator.share) {
      await navigator.share(shareData);
    } else {
      await navigator.clipboard.writeText(shareData.url);
      // You could show a toast notification here
      console.log("Event URL copied to clipboard!");
    }
  } catch (error) {
    console.error("Error sharing event:", error);
  }
};

const handleImageError = (e) => {
  e.target.src =
    "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg";
};

const handleAvatarError = (e) => {
  e.target.src = "https://via.placeholder.com/40x40/4F46E5/ffffff?text=U";
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
