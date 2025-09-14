<template>
  <div class="flex font-poppins text-gray-100 min-h-screen bg-black p-5 overflow-y-auto max-h-96">
    <!-- Sidebar for desktop -->
    <aside
      class="hidden sm:flex flex-col fixed top-0 left-0 z-40 w-64 h-screen bg-gray-900/90 backdrop-blur-md border-r border-gray-800 shadow-lg"
    >
      <!-- Logo -->
      <div class="flex items-center justify-center h-20 border-b border-gray-800">
        <img src="" alt="Logo" class="h-12" />
      </div>

      <!-- Categories & Slider -->
      <div class="flex-1 px-6 py-6 overflow-y-auto">
        <h3 class="font-bold mb-4 text-purple-400 uppercase tracking-wide text-lg">Categories</h3>
        <div class="flex flex-col gap-3 mb-8">
          <div v-for="cat in categories" :key="cat" class="flex items-center gap-3">
            <Checkbox v-model="selectedCategories" :value="cat" inputId="cat" class="accent-purple-500" />
            <label class="text-gray-100 font-medium">{{ cat }}</label>
          </div>
        </div>

        <h3 class="font-bold mb-4 text-purple-400 uppercase tracking-wide text-lg">Price Range</h3>
        <div class="p-3 bg-gray-800/80 rounded-xl">
          <Slider v-model="price" :min="0" :max="200" class="w-full accent-purple-500" />
          <p class="mt-2 text-sm text-gray-300">
            Selected: <span class="text-green-400 font-semibold">SAR {{ price }}</span>
          </p>
        </div>
      </div>
    </aside>

    <!-- Mobile sidebar toggle -->
    <div class="sm:hidden fixed top-4 left-4 z-50">
      <button @click="mobileSidebarOpen = !mobileSidebarOpen"
              class="p-2 rounded-md bg-gray-900/80 backdrop-blur-md border border-gray-800 hover:bg-gray-800 transition">
        <svg class="w-6 h-6 text-gray-100" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
            d="M4 6h16M4 12h16M4 18h16"></path>
        </svg>
      </button>
    </div>

    <!-- Mobile sidebar -->
    <transition name="slide">
      <aside v-if="mobileSidebarOpen"
             class="sm:hidden fixed top-0 left-0 z-40 w-64 h-screen bg-gray-900/90 backdrop-blur-md border-r border-gray-800 shadow-lg">
        <div class="flex items-center justify-center h-20 border-b border-gray-800">
          <img src="" alt="Logo" class="h-12" />
        </div>
        <div class="px-6 py-6">
          <h3 class="font-bold mb-4 text-purple-400 uppercase tracking-wide text-lg">Categories</h3>
          <div class="flex flex-col gap-3 mb-8">
            <div v-for="cat in categories" :key="cat" class="flex items-center gap-3">
              <Checkbox v-model="selectedCategories" :value="cat" inputId="cat" class="accent-purple-500" />
              <label class="text-gray-100 font-medium">{{ cat }}</label>
            </div>
          </div>

          <h3 class="font-bold mb-4 text-purple-400 uppercase tracking-wide text-lg">Price Range</h3>
          <div class="p-3 bg-gray-800/80 rounded-xl">
            <Slider v-model="price" :min="0" :max="200" class="w-full accent-purple-500" />
            <p class="mt-2 text-sm text-gray-300">
              Selected: <span class="text-green-400 font-semibold">SAR {{ price }}</span>
            </p>
          </div>
        </div>
      </aside>
    </transition>

    <!-- Main Content -->
   <main class="flex-1 ml-0 sm:ml-64 p-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
  <div
    v-for="(event, index) in filteredEvents"
    :key="index"
    class="max-w-sm bg-gray-900 border border-gray-800 rounded-lg shadow-sm p-5 "
  >
    <a href="#">
<img class="rounded-t-lg w-full max-h-full object-cover" :src="event.image" :alt="event.title" />
    </a>
    <div class="p-5">
      <a href="#">
        <h5 class="mb-2 text-xl font-bold tracking-tight text-purple-400 hover:text-purple-500 transition">
          {{ event.title }}
        </h5>
      </a>
      <p class="mb-3 text-gray-300 text-sm">
        {{ event.description }}
      </p>
      <p class="mb-3 text-green-400 font-semibold text-base">SAR {{ event.price }}</p>
      <a href="#"
         class="inline-flex items-center px-3 py-2 text-sm font-medium text-white bg-gradient-to-r from-purple-500 to-indigo-500 rounded-lg hover:from-indigo-500 hover:to-purple-500 focus:ring-4 focus:outline-none focus:ring-purple-600 transition-all">
        Book Now
        <svg class="w-3.5 h-3.5 ms-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 5h12m0 0L9 1m4 4L9 9"/>
        </svg>
      </a>
    </div>
  </div>
</main>

  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import Slider from "primevue/slider";
import Checkbox from "primevue/checkbox";

const categories = ["Sport", "Art", "Music"];
const selectedCategories = ref([]);
const price = ref(100);
const mobileSidebarOpen = ref(false);

const events = ref([
  {
    title: "Rock Music Fest",
    description: "A night of energetic rock performances in Riyadh.",
    price: 50,
    image: "https://images.ctfassets.net/vy53kjqs34an/7bQKwKI1av5hrpwkjqpXXB/30cb20379432985384087b9c66acbc44/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Music",
  },
  {
    title: "Championship Final",
    description: "Catch the exciting sports final in a huge stadium.",
    price: 120,
    image: "https://images.ctfassets.net/vy53kjqs34an/3BoVodVVjMrjWWj1ldotvo/0868b1d371a56ce430263f8d5d0f3b5e/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Sport",
  },
  {
    title: "Cooking Workshop",
    description: "Join chefs to learn international cuisines hands-on.",
    price: 70,
    image: "https://images.ctfassets.net/vy53kjqs34an/3BoVodVVjMrjWWj1ldotvo/0868b1d371a56ce430263f8d5d0f3b5e/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Food",
  },
  {
    title: "Art Gallery Tour",
    description: "Explore contemporary art in the city with an expert guide.",
    price: 30,
    image: "https://images.ctfassets.net/vy53kjqs34an/7bQKwKI1av5hrpwkjqpXXB/30cb20379432985384087b9c66acbc44/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Art",
  },
  {
    title: "Rock Music Fest",
    description: "A night of energetic rock performances in Riyadh.",
    price: 50,
    image: "https://images.ctfassets.net/vy53kjqs34an/7bQKwKI1av5hrpwkjqpXXB/30cb20379432985384087b9c66acbc44/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Music",
  },
  {
    title: "Championship Final",
    description: "Catch the exciting sports final in a huge stadium.",
    price: 120,
    image: "https://images.ctfassets.net/vy53kjqs34an/3BoVodVVjMrjWWj1ldotvo/0868b1d371a56ce430263f8d5d0f3b5e/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Sport",
  },
  {
    title: "Cooking Workshop",
    description: "Join chefs to learn international cuisines hands-on.",
    price: 70,
    image: "https://images.ctfassets.net/vy53kjqs34an/3BoVodVVjMrjWWj1ldotvo/0868b1d371a56ce430263f8d5d0f3b5e/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Food",
  },
  {
    title: "Art Gallery Tour",
    description: "Explore contemporary art in the city with an expert guide.",
    price: 30,
    image: "https://images.ctfassets.net/vy53kjqs34an/7bQKwKI1av5hrpwkjqpXXB/30cb20379432985384087b9c66acbc44/1500x1500.jpg?fm=webp&fit=thumb&w=600&h=600",
    category: "Art",
  },
]);

const filteredEvents = computed(() =>
  events.value.filter(
    (e) =>
      (selectedCategories.value.length === 0 || selectedCategories.value.includes(e.category)) &&
      e.price <= price.value
  )
);
</script>

<style>
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease;
}
.slide-enter-from {
  transform: translateX(-100%);
}
.slide-leave-to {
  transform: translateX(-100%);
}
</style>
