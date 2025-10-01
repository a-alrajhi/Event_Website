<template>
  <div
    class="min-h-screen bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-white flex flex-col font-[var(--font-poppins)]"
  >
    <!-- Navbar -->
    <Navbar />

    <!-- Hero Section -->
    <section
      class="relative w-full h-[400px] flex items-center justify-center text-center"
      :style="{
        backgroundImage:
          'url(https://www.timeoutriyadh.com/cloud/timeoutriyadh/2022/10/23/riyadh-season-opening-ceremony-fireworks.jpg)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
      }"
    >
      <div class="absolute inset-0 bg-[#0b0f1a]/80"></div>
      <div class="relative z-10 max-w-xl px-6">
        <h1
          class="text-3xl md:text-4xl font-bold mb-3 text-white drop-shadow-lg"
        >
          Riyadh Events
        </h1>
        <p class="text-md md:text-lg mb-5 text-gray-200">
          Discover the best events, music, and cultural experiences in Riyadh.
        </p>
        <router-link
          to="/events"
          class="inline-block px-6 py-3 bg-[var(--color-primary)] hover:bg-[var(--color-hover)] rounded-lg shadow-lg font-semibold transition hover:shadow-xl text-white"
        >
          Explore Now
        </router-link>
      </div>
    </section>

    <!-- Info Cards Section -->
    <section
      class="px-6 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8 mt-8"
    >
      <div
        v-for="(card, index) in cards"
        :key="index"
        class="event-card rounded-xl shadow-lg p-6 flex flex-col border border-[var(--color-primary)]/30 hover:shadow-[var(--color-primary)]/50 hover:scale-105 transition-transform duration-300"
      >
        <div class="flex items-center mb-4">
          <component
            :is="card.icon"
            class="w-6 h-6 text-[var(--color-primary)] drop-shadow-md me-3"
          />
          <h3
            class="text-lg md:text-xl font-semibold text-gray-900 dark:text-white"
          >
            {{ card.title }}
          </h3>
        </div>
        <p class="text-gray-600 dark:text-gray-300 flex-1 mb-4">
          {{ card.description }}
        </p>
        <router-link
          :to="card.route"
          class="mt-auto inline-block px-5 py-2 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] hover:from-[var(--color-secondary)] hover:to-[var(--color-primary)] rounded-lg text-white font-medium shadow-md hover:shadow-[var(--color-primary)]/50 transition-all duration-300 text-center"
        >
          Explore
        </router-link>
      </div>
    </section>

    <!-- Slider / Featured Events -->
    <div class="mt-8">
      <SlliderSection :events="events" :is-loading="isLoading" />
    </div>

    <!-- Best Events Section -->
    <div class="mt-8 mb-8">
      <h2
        class="text-3xl font-bold text-center text-[var(--color-primary)] drop-shadow-[0_0_10px_var(--color-primary)]"
      >
        Best Events
      </h2>
      <div class="px-6 mt-6">
        <EnhancedEventCard :events="bestEvents" :is-loading="isLoading" />
      </div>
    </div>

 <!-- Why Choose EventVision Section -->
     <section
      class="px-6 py-16 bg-gradient-to-br from-white/50 to-gray-50 dark:from-gray-800/50 dark:to-gray-900 mt-8"
    >
      <div class="max-w-6xl mx-auto">
        <div class="text-center mb-12">
          <h2
            class="text-3xl md:text-4xl font-bold text-gray-900 dark:text-white mb-4"
          >
            Why Choose
            <span class="text-[var(--color-primary)]">EventVision</span>?
          </h2>
          <p class="text-gray-600 dark:text-gray-300 text-lg max-w-2xl mx-auto">
            Join thousands who trust EventVision for discovering and
            experiencing the best events in Saudi Arabia
          </p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <!-- Feature 1 -->
          <div
            class="flex flex-col items-center text-center p-6 event-card/80 rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-105"
          >
            <div
              class="w-16 h-16 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] rounded-full flex items-center justify-center mb-4 shadow-lg"
            >
              <Shield class="w-8 h-8 text-white" />
            </div>
            <h3
              class="text-xl font-semibold text-gray-900 dark:text-white mb-3"
            >
              Trusted & Secure
            </h3>
            <p class="text-gray-600 dark:text-gray-300 leading-relaxed">
              Your bookings and payments are protected with bank-level security.
              Book with confidence knowing your data is safe.
            </p>
          </div>

          <!-- Feature 2 -->
          <div
            class="flex flex-col items-center text-center p-6 event-card/80 rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-105"
          >
            <div
              class="w-16 h-16 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] rounded-full flex items-center justify-center mb-4 shadow-lg"
            >
              <Zap class="w-8 h-8 text-white" />
            </div>
            <h3
              class="text-xl font-semibold text-gray-900 dark:text-white mb-3"
            >
              Instant Booking
            </h3>
            <p class="text-gray-600 dark:text-gray-300 leading-relaxed">
              Skip the queues and book instantly. Get your tickets delivered
              immediately to your phone - no waiting required.
            </p>
          </div>

          <!-- Feature 3 -->
          <div
            class="flex flex-col items-center text-center p-6 event-card/80 rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-105"
          >
            <div
              class="w-16 h-16 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] rounded-full flex items-center justify-center mb-4 shadow-lg"
            >
              <Crown class="w-8 h-8 text-white" />
            </div>
            <h3
              class="text-xl font-semibold text-gray-900 dark:text-white mb-3"
            >
              Premium Experience
            </h3>
            <p class="text-gray-600 dark:text-gray-300 leading-relaxed">
              Curated events from the best organizers in Saudi Arabia. Only
              quality experiences make it to our platform.
            </p>
          </div>
        </div>


    <!-- Stats Section -->
        <div class="mt-16 grid grid-cols-2 md:grid-cols-4 gap-8">
          <div class="text-center">
            <div
              class="text-3xl md:text-4xl font-bold text-[var(--color-primary)] mb-2"
            >
              50K+
            </div>
            <p class="text-gray-600 dark:text-gray-300">Happy Customers</p>
          </div>
          <div class="text-center">
            <div
              class="text-3xl md:text-4xl font-bold text-[var(--color-primary)] mb-2"
            >
              1000+
            </div>
            <p class="text-gray-600 dark:text-gray-300">Events Hosted</p>
          </div>
          <div class="text-center">
            <div
              class="text-3xl md:text-4xl font-bold text-[var(--color-primary)] mb-2"
            >
              25+
            </div>
            <p class="text-gray-600 dark:text-gray-300">Cities Covered</p>
          </div>
          <div class="text-center">
            <div
              class="text-3xl md:text-4xl font-bold text-[var(--color-primary)] mb-2"
            >
              4.9★
            </div>
            <p class="text-gray-600 dark:text-gray-300">Customer Rating</p>
          </div>
        </div>

        <!-- CTA -->
        <div class="text-center mt-12">
          <router-link
            to="/events"
            class="inline-flex items-center gap-2 px-8 py-4 bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white rounded-lg font-semibold transition-all shadow-lg hover:shadow-xl"
          >
            <Sparkles class="w-5 h-5" />
            Start Your Journey
          </router-link>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <AppFooter />
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import Navbar from "../components/Navbar/Navbar.vue";
import AppFooter from "../components/AppFooter/AppFooter.vue";
import EnhancedEventCard from "../components/Cards/EnhancedEventCard.vue";
import SlliderSection from "../components/Cards/SlliderSection.vue";
import {
  Calendar,
  MapPin,
  Ticket,
  Shield,
  Zap,
  Crown,
  Sparkles,
} from "lucide-vue-next";
import { getEvents } from "../apis/EventDetalisApi";

// Info cards
const cards = ref([
  {
    title: "Upcoming Events",
    description: "Check out the latest events happening in Riyadh.",
    icon: Calendar,
    route: "/events",
  },
  {
    title: "Event Locations",
    description: "Discover the best venues across the city.",
    icon: MapPin,
    route: "/location",
  },
  {
    title: "Event Categories",
    description: "Explore events by music, sports, culture and more.",
    icon: Ticket,
    route: "/events",
  },
]);

// === Events logic inside component ===
const events = ref([]);
const isLoading = ref(true);

const fetchEvents = async () => {
  isLoading.value = true;
  try {
    const eventsResponse = await getEvents(0, 6); // Get first 6 events for home page
    events.value = eventsResponse.content || [];
  } finally {
    isLoading.value = false;
  }
};

onMounted(fetchEvents);

// Best events (for cards or EnhancedEventCard)
const bestEvents = computed(() => events.value.slice(0, 6));
</script>

<style scoped>
.scrollbar-hide::-webkit-scrollbar {
  display: none;
}
.scrollbar-hide {
  -ms-overflow-style: none;
  scrollbar-width: none;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
