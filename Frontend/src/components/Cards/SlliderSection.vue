<!-- src/components/SliderSection.vue/SliderSection.vue -->
<template>
  <section class="py-12 font-[var(--font-poppins)]">
    <div class="max-w-6xl mx-auto px-4 sm:px-6">
      <h2
        class="text-3xl font-bold mb-10 text-center text-[var(--color-primary)] drop-shadow-[0_0_15px_var(--color-primary)]"
      >
        Featured Events
      </h2>

      <!-- Loading State -->
      <div v-if="isLoading" class="rounded-2xl shadow-[0_0_25px_var(--color-primary)/50] px-5 sm:px-10 pb-6">
        <div class="event-card border border-gray-300/30 dark:border-gray-600/30 rounded-2xl overflow-hidden animate-pulse">
          <div class="h-64 bg-gray-200 dark:bg-gray-700"></div>
          <div class="p-6 space-y-3">
            <div class="h-4 bg-gray-200 dark:bg-gray-700 rounded w-3/4"></div>
            <div class="h-3 bg-gray-200 dark:bg-gray-700 rounded w-1/2"></div>
            <div class="h-3 bg-gray-200 dark:bg-gray-700 rounded w-full"></div>
          </div>
        </div>
      </div>

      <!-- Events Slider -->
      <Swiper
        v-else
        :modules="[Pagination, Navigation]"
        :slides-per-view="1"
        :space-between="20"
        navigation
        pagination
        class="rounded-2xl shadow-[0_0_25px_var(--color-primary)/50] px-5 sm:px-10 pb-6"
      >
        <SwiperSlide
          v-for="event in featuredEvents"
          :key="event.id"
          class="event-card border border-gray-300/30 dark:border-gray-600/30 rounded-2xl overflow-hidden shadow-[0_10px_25px_var(--color-primary)/40] transition duration-300"
        >
        <RouterLink
        :to="'/events/'+event.id">
          
          
          <div class="flex justify-center overflow-hidden">
            <img
            :src="event.image"
            :alt="event.title"
            class="w-full h-74 object-cover transform transition-transform duration-500"
            />
          </div>

          <div class="p-5">
            <h3 class="text-xl font-bold mb-2 text-[var(--color-primary)]">
              {{ event.title }}
            </h3>
            <p
              class="text-sm text-gray-600 dark:text-gray-400 mb-3 line-clamp-2"
            >
              {{ event.description }}
            </p>
            <span class="block text-xs text-gray-700 dark:text-gray-300">
              <MapPin
                class="w-4 h-4 inline-block mr-1 text-[var(--color-primary)]"
              />
              {{ event.location }}
              <Calendar
                class="w-4 h-4 inline-block ml-2 mr-1 text-[var(--color-primary)]"
              />
              {{ formatDate(event.date) }}
            </span>
          </div>
        </RouterLink>
        </SwiperSlide>
      </Swiper>
    </div>
  </section>
</template>

<script setup>
import { computed } from "vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Pagination, Navigation } from "swiper/modules";
import { MapPin, Calendar } from "lucide-vue-next";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

const props = defineProps({
  events: {
    type: Array,
    default: () => []
  },
  isLoading: {
    type: Boolean,
    default: false
  }
});

const featuredEvents = computed(() => {
  if (!Array.isArray(props.events)) return [];
  return props.events.slice(0, 5).map((e) => ({
    ...e,
    location: "Saudi Arabia",
    date: e.date || "2025-10-15",
  }));
});

const formatDate = (date) => {
  return date
    ? new Date(date).toLocaleDateString("en-US", {
        month: "short",
        day: "numeric",
        weekday: "short",
      })
    : "TBD";
};
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
