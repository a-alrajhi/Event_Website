<template>
  <div class="min-h-screen bg-[var(--color-bg)] text-[var(--color-text)] flex flex-col space-y-4 font-[var(--font-poppins)]">
    
    <!-- Navbar -->
    <Navbar />

    <!-- Hero Section -->
    <section
      class="relative w-full h-[400px] flex items-center justify-center text-center"
      :style="{
        backgroundImage: 'url(https://www.timeoutriyadh.com/cloud/timeoutriyadh/2022/10/23/riyadh-season-opening-ceremony-fireworks.jpg)',
        backgroundSize: 'cover',
        backgroundPosition: 'center'
      }"
    >
      <div class="absolute inset-0 bg-[#0b0f1a]/80"></div>
      <div class="relative z-10 max-w-xl px-6">
        <h1 class="text-3xl md:text-4xl font-bold mb-3 text-white drop-shadow-lg">Riyadh Events</h1>
        <p class="text-md md:text-lg mb-5 text-gray-200">
          Discover the best events, music, and cultural experiences in Riyadh.
        </p>
        <button
          class="px-6 py-3 bg-[var(--color-primary)] hover:bg-[var(--color-hover)] rounded-lg shadow-lg font-semibold transition hover:shadow-xl text-white"
        >
          Explore Now
        </button>
      </div>
    </section>

    <!-- Info Cards Section -->
    <section class="px-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
      <div
        v-for="(card, index) in cards"
        :key="index"
        class="bg-[var(--color-card)] rounded-xl shadow-lg p-6 flex flex-col border border-[var(--color-primary)]/30 hover:shadow-[var(--color-primary)]/50 hover:scale-105 transition-transform duration-300"
      >
        <div class="flex items-center mb-4">
          <component :is="card.icon" class="w-6 h-6 text-[var(--color-primary)] drop-shadow-md me-3" />
          <h3 class="text-lg md:text-xl font-semibold text-[var(--color-text)]">{{ card.title }}</h3>
        </div>
        <p class="text-[var(--color-gray)] flex-1 mb-4">{{ card.description }}</p>
        <button
          class="mt-auto px-5 py-2 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] hover:from-[var(--color-secondary)] hover:to-[var(--color-primary)] rounded-lg text-[var(--color-text)] font-medium shadow-md hover:shadow-[var(--color-primary)]/50 transition-all duration-300"
        >
          Learn More
        </button>
      </div>
    </section>

    <!-- Slider / Featured Events -->
    <SlliderSection :events="events" :is-loading="isLoading" />

    <!-- Best Events Section -->
    <h2 class="text-3xl font-bold text-center text-[var(--color-primary)] drop-shadow-[0_0_10px_var(--color-primary)]">
      Best Events
    </h2>
    <div class="px-6">
      <EnhancedEventCard :events="bestEvents" :is-loading="isLoading" />
    </div>

    <!-- Contact Section -->
    <section class="px-6 py-12 bg-[var(--color-bg)] flex flex-col items-center space-y-6">
      <h2 class="text-2xl md:text-3xl font-bold text-[var(--color-primary)] drop-shadow-lg">
        Contact Us
      </h2>
      <p class="text-[var(--color-text)] text-center max-w-md">
        Have a question or want to get in touch? Send us a message!
      </p>
      <div class="flex flex-col sm:flex-row gap-4 w-full max-w-md">
        <input
          type="text"
          placeholder="Your message..."
          class="flex-1 px-4 py-2 rounded-lg border border-[var(--color-primary)] bg-[var(--color-bg)] text-[var(--color-text)] placeholder-[var(--color-gray)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]"
        />
        <button class="px-5 py-2 bg-[var(--color-primary)] hover:bg-[var(--color-hover)] rounded-lg font-semibold transition shadow-md hover:shadow-lg text-[var(--color-text)]">
          Send
        </button>
      </div>
    </section>

    <!-- Footer -->
    <AppFooter />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import Navbar from '../components/Navbar/Navbar.vue';
import AppFooter from '../components/AppFooter/AppFooter.vue';
import EnhancedEventCard from '../components/Cards/EnhancedEventCard.vue';
import SlliderSection from '../components/Cards/SlliderSection.vue';
import { Calendar, MapPin, Ticket } from 'lucide-vue-next';
import { getEvents } from '../apis/EventDetalisApi';

// Info cards
const cards = ref([
  { title: 'Upcoming Events', description: 'Check out the latest events happening in Riyadh.', icon: Calendar },
  { title: 'Event Locations', description: 'Discover the best venues across the city.', icon: MapPin },
  { title: 'Book Tickets', description: 'Secure your spot and enjoy the experience.', icon: Ticket },
]);

// === Events logic inside component ===
const events = ref([]);
const isLoading = ref(true);

const fetchEvents = async () => {
  isLoading.value = true;
  try {
    events.value = await getEvents();
  } finally {
    isLoading.value = false;
  }
};

onMounted(fetchEvents);

// Best events (for cards or EnhancedEventCard)
const bestEvents = computed(() => events.value.slice(0, 6));
</script>

<style scoped>
.scrollbar-hide::-webkit-scrollbar { display: none; }
.scrollbar-hide { -ms-overflow-style: none; scrollbar-width: none; }

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>