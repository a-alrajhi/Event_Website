<template>
  <div class="flex flex-col font-poppins text-[var(--color-text)] min-h-screen bg-[var(--color-bg)] p-5 overflow-y-auto">

    <!-- Top Navigation -->
    <nav class="bg-[var(--color-card)]/80 backdrop-blur-xl border border-[var(--color-primary)]/20 shadow-lg rounded-2xl mb-6 p-6 sm:ml-64">
      
      <!-- Desktop Layout -->
      <div class="hidden sm:flex items-center justify-between gap-8">
        <!-- Logo & Categories -->
        <div class="flex items-center gap-8">
          <div class="flex items-center gap-3">
            <div class="w-10 h-10 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] rounded-xl flex items-center justify-center">
              <Calendar class="w-5 h-5 text-white" />
            </div>
            <span class="text-xl font-bold text-[var(--color-text)]">EventHub</span>
          </div>
          
          <div class="flex items-center gap-1">
            <div
              v-for="(category, index) in horizontalCategories"
              :key="index"
              class="flex items-center gap-2 px-3 py-2 rounded-xl cursor-pointer transition-all duration-200 hover:bg-[var(--color-primary)]/10 hover:scale-105 text-sm font-medium"
            >
              <component :is="category.icon" class="w-4 h-4 text-[var(--color-primary)]" />
              <span class="text-[var(--color-primary)]">{{ category.name }}</span>
            </div>
          </div>
        </div>

        <!-- Search & Profile -->
        <div class="flex items-center gap-4">
          <div class="w-80">
            <SearchBar class="w-full" />
          </div>
          <div class="relative">
            <img
              class="w-11 h-11 rounded-full border-2 border-[var(--color-primary)]/50 hover:border-[var(--color-primary)] transition-all cursor-pointer"
              src="https://randomuser.me/api/portraits/men/32.jpg"
              alt="Profile"
            />
            <div class="absolute -bottom-0.5 -right-0.5 w-3.5 h-3.5 bg-green-500 rounded-full border-2 border-[var(--color-card)]"></div>
          </div>
        </div>
      </div>

      <!-- Mobile Layout -->
      <div class="sm:hidden space-y-4">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-2">
            <div class="w-8 h-8 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] rounded-lg flex items-center justify-center">
              <Calendar class="w-4 h-4 text-white" />
            </div>
            <span class="text-lg font-bold text-[var(--color-text)]">EventHub</span>
          </div>
          <img
            class="w-9 h-9 rounded-full border-2 border-[var(--color-primary)]/50"
            src="https://randomuser.me/api/portraits/men/32.jpg"
            alt="Profile"
          />
        </div>
        
        <SearchBar class="w-full" />
        
        <div class="flex gap-2 overflow-x-auto scrollbar-hide pb-1">
          <div
            v-for="(category, index) in horizontalCategories"
            :key="index"
            class="flex items-center gap-1.5 px-3 py-1.5 bg-[var(--color-primary)]/10 rounded-xl cursor-pointer transition-colors hover:bg-[var(--color-primary)]/20 text-xs font-medium whitespace-nowrap"
          >
            <component :is="category.icon" class="w-3.5 h-3.5 text-[var(--color-primary)]" />
            <span class="text-[var(--color-primary)]">{{ category.name }}</span>
          </div>
        </div>
      </div>
    </nav>

    <div class="flex">

      <!-- Modern Sidebar -->
      <aside class="hidden sm:flex flex-col fixed top-0 left-0 z-40 w-64 h-screen bg-[var(--color-card)]/90 backdrop-blur-xl border-r border-[var(--color-primary)]/20 shadow-xl">
        
        <div class="flex items-center gap-3 h-20 px-6 border-b border-[var(--color-primary)]/20">
          <div class="w-8 h-8 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-secondary)] rounded-lg flex items-center justify-center">
            <MapPin class="w-4 h-4 text-white" />
          </div>
          <h2 class="text-lg font-semibold text-[var(--color-text)]">Filters</h2>
        </div>

        <div class="flex-1 px-6 py-6 space-y-8 overflow-y-auto">
          
          <!-- Loading State -->
          <div v-if="isLoading" class="flex items-center justify-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-[var(--color-primary)]"></div>
          </div>

          <!-- Categories -->
          <div v-if="!isLoading" class="space-y-4">
            <div class="flex items-center justify-between">
              <h3 class="text-sm font-semibold text-[var(--color-text)] uppercase tracking-wider">Categories</h3>
              <span class="text-xs text-[var(--color-gray)]">{{ sidebarCategories.length }} found</span>
            </div>
            <div class="space-y-2">
              <div v-for="cat in sidebarCategories" :key="cat" class="group">
                <label class="flex items-center gap-3 p-3 rounded-xl hover:bg-[var(--color-primary)]/8 cursor-pointer transition-colors">
                  <Checkbox 
                    v-model="selectedCategories" 
                    :value="cat" 
                    :inputId="cat"
                    class="accent-[var(--color-primary)]" 
                  />
                  <span class="text-sm font-medium text-[var(--color-text)] group-hover:text-[var(--color-primary)]">
                    {{ cat }}
                  </span>
                  <!-- Event count per category -->
                  <span class="text-xs text-[var(--color-gray)] ml-auto">
                    {{ getCategoryCount(cat) }}
                  </span>
                </label>
              </div>
            </div>
          </div>

          <!-- Price Range -->
          <div v-if="!isLoading && priceRange.max > 0" class="space-y-4">
            <div class="flex items-center justify-between">
              <h3 class="text-sm font-semibold text-[var(--color-text)] uppercase tracking-wider">Price Range</h3>
              <span class="text-xs text-[var(--color-primary)] font-medium">
                {{ currentPrice === 0 ? 'All Prices' : `SAR ${currentPrice}` }}
              </span>
            </div>
            <div class="p-4 rounded-xl bg-[var(--color-bg)]/50">
              <Slider 
                v-model="currentPrice" 
                :min="priceRange.min" 
                :max="priceRange.max" 
                class="w-full mb-4" 
              />
              <div class="flex justify-between items-center text-sm">
                <span class="text-[var(--color-gray)]">SAR 0 (All)</span>
                <span class="font-medium text-[var(--color-primary)]">
                  {{ currentPrice === 0 ? 'All Prices' : `SAR ${currentPrice}` }}
                </span>
                <span class="text-[var(--color-gray)]">SAR 1500</span>
              </div>
              <div class="mt-2 text-xs text-[var(--color-gray)] text-center">
                {{ getFilteredByPriceCount() }} events 
                {{ currentPrice === 0 ? 'shown (all price ranges)' : `under SAR ${currentPrice}` }}
              </div>
            </div>
          </div>


        </div>
      </aside>

      <!-- Main Content -->
      <main class="flex-1 ml-0 sm:ml-64 p-6">
        <!-- Results Summary -->
        <div v-if="!isLoading" class="mb-6 flex items-center justify-between">
          <div>
            <h1 class="text-2xl font-bold text-[var(--color-text)]">Events in Saudi Arabia</h1>
            <p class="text-[var(--color-gray)] mt-1">
              {{ filteredEvents.length }} events found
              <span v-if="selectedCategories.length > 0 || currentPrice > 0">
                (filtered)
              </span>
            </p>
          </div>
          <div class="text-sm text-[var(--color-gray)]">
            Price range: SAR {{ priceRange.min }} - SAR {{ priceRange.max }}
          </div>
        </div>

        <EnhancedEventCard 
          :events="filteredEvents" 
          :is-loading="isLoading"
        />

      </main>
    </div>
  </div>
</template>

<script setup>
import SearchBar from "../components/SearchBar/SearchBar.vue";
import EnhancedEventCard from "../components/Cards/EnhancedEventCard.vue";
import { Music, Home, Star, Calendar, Users, MapPin, Ticket, X } from "lucide-vue-next";
import Slider from "primevue/slider";
import Checkbox from "primevue/checkbox";
import { ref, computed, onMounted } from "vue";
import { getEvents, getCategories, getPriceRange } from "../apis/EventDetalisApi";

const horizontalCategories = [
  { name: 'Home', icon: Home },
  { name: 'Music', icon: Music },
  { name: 'Stars', icon: Star },
  { name: 'Events', icon: Calendar },
  { name: 'Locations', icon: MapPin },
  { name: 'Tickets', icon: Ticket },
];

const sidebarCategories = ref([]);
const selectedCategories = ref([]);
const priceRange = ref({ min: 0, max: 1500 });
const currentPrice = ref(0); 
const events = ref([]);
const isLoading = ref(true);

const fetchData = async () => {
  try {
    isLoading.value = true;
    
    // Fetch all data in parallel
    const [eventsData, categoriesData, priceRangeData] = await Promise.all([
      getEvents(),
      getCategories(),
      getPriceRange()
    ]);

    events.value = eventsData;
    sidebarCategories.value = categoriesData;
    priceRange.value = priceRangeData;
    currentPrice.value = 0; 
    
  } catch (error) {
    console.error('Error fetching data:', error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchData();
});

const filteredEvents = computed(() => {
  return events.value.filter(event => {
    // Category filter
    const categoryMatch = selectedCategories.value.length === 0 || 
                         selectedCategories.value.includes(event.category);
    
 
    const priceMatch = currentPrice.value === 0 || event.price <= currentPrice.value;
    
    return categoryMatch && priceMatch;
  });
});


const getCategoryCount = (category) => {
  return events.value.filter(event => event.category === category).length;
};

const getFilteredByPriceCount = () => {
  if (currentPrice.value === 0) {
    return events.value.length;
  }
  return events.value.filter(event => event.price <= currentPrice.value).length;
};




</script>

<style scoped>
.scrollbar-hide::-webkit-scrollbar { display: none; }
.scrollbar-hide { -ms-overflow-style: none; scrollbar-width: none; }
</style>