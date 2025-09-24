<!--
Event Similar Events Component
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.1 - Fixed light/dark mode classes
-->
<template>
  <div class="my-8">
    <h2 class="text-2xl font-bold text-gray-800 dark:text-gray-200 mb-4">
      Explore Similar Events!
    </h2>

    <Swiper
      :modules="[Navigation, Pagination, Autoplay]"
      :slides-per-view="1"
      :space-between="20"
      :autoplay="{ delay: 3000, disableOnInteraction: true }"
      :breakpoints="{
        640: { slidesPerView: 1 },
        768: { slidesPerView: 2 },
        1024: { slidesPerView: 3 },
      }"
      :pagination="{ clickable: true }"
      navigation
      class="pb-10"
    >
      <SwiperSlide
        v-for="event in similarEvents"
        :key="event.id"
        class="h-auto py-2"
      >
        <div
          class="bg-white dark:bg-gray-800 shadow-md rounded-xl overflow-hidden hover:scale-[1.02] transition-all duration-300"
        >
          <img
            :src="event.photoUrl"
            :alt="event.name"
            class="h-48 w-full object-cover"
          />
          <div class="p-4">
            <h3 class="text-lg font-semibold text-gray-800 dark:text-gray-200">
              {{ event.name }}
            </h3>
            <p
              class="text-sm text-gray-600 dark:text-gray-400 line-clamp-2 mt-1"
            >
              {{ event.description }}
            </p>
            <RouterLink
              :to="`/event/${event.id}`"
              class="cursor-pointer mt-3 inline-block text-sm text-blue-600 dark:text-blue-400 hover:underline"
            >
              View Event →
            </RouterLink>
          </div>
        </div>
      </SwiperSlide>
    </Swiper>
  </div>
</template>

<script setup>
import { ref, watchEffect } from "vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import { Autoplay, Pagination, Navigation } from "swiper/modules";

import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";

import { getEventsByCategory } from "../../apis/eventApi";

const props = defineProps({
  categoryId: Number,
  curEventId: Number,
});

const similarEvents = ref([]);

watchEffect(async () => {
  // Defensive check for valid input
  if (
    typeof props.categoryId !== "number" ||
    typeof props.curEventId !== "number"
  ) {
    similarEvents.value = [];
    return;
  }

  try {
    const events = await getEventsByCategory(props.categoryId);
    similarEvents.value = events.filter((e) => e.id !== props.curEventId);
  } catch (error) {
    console.error("Failed to fetch similar events:", error);
    similarEvents.value = [];
  }
});
</script>
