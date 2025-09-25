<!-- src/components/SliderSection.vue/SliderSection.vue -->
<template>
  <section class="py-12 bg-[var(--color-bg)] font-[var(--font-poppins)]">
    <div class="max-w-6xl mx-auto px-4 sm:px-6">
      <h2
        class="text-3xl font-bold mb-10 text-center text-[var(--color-primary)] drop-shadow-[0_0_15px_var(--color-primary)]"
      >
        Featured Events
      </h2>

      <Swiper
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
          class="bg-[var(--color-card)] border border-[var(--color-gray)]/30 rounded-2xl overflow-hidden shadow-[0_10px_25px_var(--color-primary)/40] transition duration-300"
        >
        <RouterLink
        :to="'/event/'+event.id">
          
          
          <div class="flex justify-center overflow-hidden">
            <img
            :src="event.image"
            :alt="event.title"
            class="w-full h-74 object-cover transform transition-transform duration-500"
            />
          </div>

          <div class="p-5 text-[var(--color-text)]">
            <h3 class="text-xl font-bold mb-2 text-[var(--color-primary)]">
              {{ event.title }}
            </h3>
            <p class="text-sm text-[var(--color-gray)] mb-3 line-clamp-2">
              {{ event.description }}
            </p>
            <span class="block text-xs text-[var(--color-text)]/80">
              <MapPin class="w-4 h-4 inline-block mr-1 text-[var(--color-primary)]" />
              {{ event.location }}
              <Calendar class="w-4 h-4 inline-block ml-2 mr-1 text-[var(--color-primary)]" />
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
import { ref, onMounted } from 'vue';
import { Swiper, SwiperSlide } from 'swiper/vue';
import { Pagination, Navigation } from 'swiper/modules';
import { MapPin, Calendar } from 'lucide-vue-next';
import { getEvents } from '../../apis/EventDetalisApi';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';

const featuredEvents = ref([]);

const formatDate = (date) => {
  return date
    ? new Date(date).toLocaleDateString('en-US', {
        month: 'short',
        day: 'numeric',
        weekday: 'short',
      })
    : 'TBD';
};

onMounted(async () => {
  const events = await getEvents();
  featuredEvents.value = events.slice(0, 5).map(e => ({
    ...e,
    location: 'Saudi Arabia',
    date: e.date || '2025-10-15', 
  }));
});
</script>

<style scoped>

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>