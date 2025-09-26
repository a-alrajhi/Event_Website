<template>
  <div
    class="min-h-screen bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-white font-[var(--font-poppins)]"
  >
    <!-- Navbar Component -->
    <Navbar />

    <!-- Main Content -->
    <main class="container mx-auto px-4 py-6">
      <div class="flex flex-col lg:flex-row gap-8">
        <!-- Sidebar Filters -->
        <aside class="lg:w-80 space-y-6">
          <!-- Categories Filter -->
          <div
            class="event-card/90 backdrop-blur-sm rounded-2xl p-6 shadow-lg border border-[var(--color-primary)]/10"
          >
            <div class="flex items-center justify-between mb-4">
              <h2 class="text-lg font-semibold text-gray-900 dark:text-white">
                Categories
              </h2>
              <span
                class="text-xs text-gray-600 dark:text-gray-300 bg-[var(--color-primary)]/10 px-2 py-1 rounded-full"
              >
                {{ sidebarCategories.length }} available
              </span>
            </div>

            <div v-if="isLoading" class="space-y-3">
              <div
                v-for="i in 4"
                :key="i"
                class="h-4 bg-gray-200 dark:bg-gray-700 rounded animate-pulse"
              ></div>
            </div>

            <div v-else class="space-y-3 max-h-60 overflow-y-auto">
              <label
                v-for="cat in sidebarCategories"
                :key="cat"
                class="flex items-center gap-3 p-3 rounded-xl hover:bg-[var(--color-primary)]/8 cursor-pointer transition-all group"
              >
                <Checkbox
                  v-model="selectedCategories"
                  :value="cat"
                  :inputId="cat"
                  class="accent-[var(--color-primary)]"
                />
                <span
                  class="text-sm font-medium text-gray-900 dark:text-white group-hover:text-[var(--color-primary)] flex-1"
                >
                  {{ cat }}
                </span>
                <span
                  class="text-xs text-gray-600 dark:text-gray-300 bg-gray-100 dark:bg-gray-800 px-2 py-1 rounded-full"
                >
                  {{ getCategoryCount(cat) }}
                </span>
              </label>
            </div>
          </div>

          <!-- Price Filter -->
          <div v-if="!isLoading && priceRange.max > 0" class="event-card/90 backdrop-blur-sm rounded-2xl p-6 shadow-lg border border-[var(--color-primary)]/10">
            <div class="flex items-center justify-between mb-4">
              <h2 class="text-lg font-semibold text-gray-900 dark:text-white">
                Price Range
              </h2>
              <div class="bg-[var(--color-primary)]/10 px-3 py-1 rounded-full">
                <span class="text-sm font-medium text-[var(--color-primary)]">
                  {{ currentPrice === 0 ? "All" : `≤ SAR ${currentPrice}` }}
                </span>
              </div>
            </div>

            <div class="space-y-4">
              <Slider
                v-model="currentPrice"
                :min="0"
                :max="priceRange.max"
                class="w-full"
              />

              <div class="flex justify-between items-center text-sm">
                <span class="text-[var(--color-gray)]">Free</span>
                <span class="font-semibold text-[var(--color-primary)]">
                  SAR {{ priceRange.max }}
                </span>
              </div>

              <div
                class="bg-gray-100/50 dark:bg-gray-800/50 rounded-xl p-3 text-center"
              >
                <span class="text-sm text-gray-600 dark:text-gray-300">
                  {{ getFilteredByPriceCount() }} events
                  {{
                    currentPrice === 0
                      ? "in all price ranges"
                      : `under SAR ${currentPrice}`
                  }}
                </span>
              </div>
            </div>
          </div>

          <!-- Clear Filters -->
          <button
            v-if="selectedCategories.length > 0 || currentPrice > 0"
            @click="clearFilters"
            class="w-full bg-[var(--color-error)]/10 hover:bg-[var(--color-error)]/20 text-[var(--color-error)] border border-[var(--color-error)]/30 rounded-xl p-3 transition-all font-medium"
          >
            Clear All Filters
          </button>
        </aside>

        <!-- Events Grid -->
        <section class="flex-1">
          <!-- Results Header -->
          <div
            class="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-6 gap-4"
          >
            <div>
              <h2 class="text-2xl font-bold text-gray-900 dark:text-white mb-1">
                Events in Saudi Arabia
              </h2>
              <p class="text-gray-600 dark:text-gray-300">
                <span class="font-medium">{{ filteredEvents.length }}</span>
                events found
                <span
                  v-if="selectedCategories.length > 0 || currentPrice > 0"
                  class="text-[var(--color-primary)]"
                >
                  (filtered)
                </span>
              </p>
            </div>

            <div class="flex items-center gap-4">
              <div class="text-sm text-gray-600 dark:text-gray-300">
                Price range: SAR 0 - SAR {{ priceRange.max }}
              </div>
            </div>
          </div>

          <!-- Quick Category Chips -->
          <div class="flex flex-wrap gap-2 mb-6">
            <button
              v-for="cat in sidebarCategories.slice(0, 6)"
              :key="cat"
              @click="toggleQuickCategory(cat)"
              :class="[
                'px-4 py-2 rounded-full text-sm font-medium transition-all border',
                selectedCategories.includes(cat)
                  ? 'bg-[var(--color-primary)] text-white border-[var(--color-primary)] shadow-lg'
                  : 'event-card text-gray-900 dark:text-white border-[var(--color-primary)]/30 hover:bg-[var(--color-primary)]/10',
              ]"
            >
              {{ cat }} ({{ getCategoryCount(cat) }})
            </button>
          </div>

          <div v-if="!isLoading" class="event-card/90 mb-6">
            <div class="flex flex-wrap gap-y-3 overflow-x-auto scrollbar-hide justify-between">
              <DateCard
                v-for="date in upcomingDates"
                :key="date.toISOString()"
                :date="date"
                :model-value="selectedDate"
                @select="(date) => {
                  selectedDate = selectedDate && selectedDate.toDateString() === date.toDateString() 
                    ? null 
                    : date
                }"
              />
            </div>
          </div>

          <!-- Events Grid -->
          <EnhancedEventCard
            :events="filteredEvents"
            :is-loading="isLoading"
            @toggle-save="handleToggleSave"
          />

          <!-- No Results -->
          <div
            v-if="!isLoading && filteredEvents.length === 0"
            class="text-center py-16"
          >
            <div
              class="w-20 h-20 bg-[var(--color-primary)]/10 rounded-full flex items-center justify-center mx-auto mb-4"
            >
              <Search class="w-8 h-8 text-[var(--color-primary)]" />
            </div>
            <h3
              class="text-xl font-semibold text-gray-900 dark:text-white mb-2"
            >
              No events found
            </h3>
            <p class="text-gray-600 dark:text-gray-300 mb-4">
              Try adjusting your filters or search terms
            </p>
            <button
              @click="clearFilters"
              class="bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white px-6 py-3 rounded-lg transition-colors"
            >
              Clear Filters
            </button>
          </div>
        </section>
      </div>
    </main>
  </div>
</template>

<script setup>
import Navbar from "../components/Navbar/Navbar.vue";
import EnhancedEventCard from "../components/Cards/EnhancedEventCard.vue";
import { Calendar, Search } from "lucide-vue-next";
import Slider from "primevue/slider";
import Checkbox from "primevue/checkbox";
import { ref, computed, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getEvents } from "../apis/EventDetalisApi";
import DateCard from "../components/Cards/DateCard.vue";

const route = useRoute();
const router = useRouter();

const events = ref([]);
const isLoading = ref(true);
const selectedCategories = ref([]);
const priceRange = ref({ min: 0, max: 200 });
const currentPrice = ref(0);
const sidebarCategories = ref([]);
const searchInput = ref("");
const selectedDate = ref(null);

const upcomingDates = computed(() => {
  const dates = [];
  const today = new Date();
  for (let i = 0; i < 12; i++) {
    const d = new Date(today);
    d.setDate(today.getDate() + i);
    dates.push(d);
  }
  return dates;
});

const fetchData = async () => {
  try {
    isLoading.value = true;
    const eventsData = await getEvents();
    events.value = eventsData;
    sidebarCategories.value = [...new Set(eventsData.map(event => event.category))].filter(Boolean);
    // Calculate price range from all event prices including price ranges
    const allPrices = [];
    eventsData.forEach(event => {
      if (event.priceRange && event.priceRange.length > 0) {
        // Add all prices from the price range
        event.priceRange.forEach(p => {
          const price = parseFloat(p);
          if (price > 0) allPrices.push(price);
        });
      } else if (event.price > 0) {
        // Fallback to single price
        allPrices.push(event.price);
      }
    });

    priceRange.value = allPrices.length ?
      { min: 0, max: Math.ceil(Math.max(...allPrices) * 1.2) } :
      { min: 0, max: 200 };
    currentPrice.value = 0;

    // Apply category filter from URL query params
    applyUrlFilters();
  } catch (error) {
    console.error("Error fetching data:", error);
  } finally {
    isLoading.value = false;
  }
};

const applyUrlFilters = () => {
  // Check for category in URL query params
  const categoryParam = route.query.category;
  const searchQuery = route.query.q;
  if (categoryParam && typeof categoryParam === "string") {
    const categoryName =
      categoryParam.charAt(0).toUpperCase() + categoryParam.slice(1);

    // Find matching category (case insensitive)
    const matchingCategory = sidebarCategories.value.find(
      (cat) => cat.toLowerCase() === categoryParam.toLowerCase()
    );

    if (matchingCategory) {
      selectedCategories.value = [matchingCategory];
    }

    if (searchQuery && typeof searchQuery === "string") {
      searchInput.value = searchQuery.trim();
    }
  }
};

// Watch route changes to apply filters
watch(
  () => route.query,
  () => {
    if (!isLoading.value) {
      applyUrlFilters();
    }
  },
  { deep: true }
);

watch(
  () => route.query.q,
  (newQ) => {
    if (typeof newQ === "string") {
      searchInput.value = newQ.trim();
    }
  },
  { immediate: true }
);

onMounted(fetchData);

const filteredEvents = computed(() => {
  return events.value.filter(event => {
    const categoryMatch =
      selectedCategories.value.length === 0 || selectedCategories.value.includes(event.category);

    const searchMatch =
      !searchInput.value ||
      event.title.toLowerCase().includes(searchInput.value.toLowerCase());

    let priceMatch = true;
    if (currentPrice.value > 0) {
      if (event.priceRange && event.priceRange.length > 0) {
        const prices = event.priceRange.map(p => parseFloat(p) || 0).filter(p => p > 0);
        priceMatch = prices.length ? Math.min(...prices) <= currentPrice.value : true;
      } else {
        priceMatch = event.price <= currentPrice.value;
      }
    }

    let dateMatch = true;
    if (selectedDate.value && event.dates && event.dates.length > 0) {
      const selectedStr = selectedDate.value.toDateString();
      dateMatch = event.dates.some(d => new Date(d).toDateString() === selectedStr);
    }

    return categoryMatch && priceMatch && searchMatch && dateMatch;
  });
});


const getCategoryCount = (category) =>
  events.value.filter((event) => event.category === category).length;

const getFilteredByPriceCount = () => {
  if (currentPrice.value === 0) return events.value.length;

  return events.value.filter(event => {
    // For events with price ranges, check if ANY price in the range is within the filter
    if (event.priceRange && event.priceRange.length > 0) {
      const prices = event.priceRange.map(p => parseFloat(p) || 0).filter(p => p > 0);
      if (prices.length > 0) {
        return Math.min(...prices) <= currentPrice.value;
      }
      return true; // Free events always match
    } else {
      // Fallback to single price
      return event.price <= currentPrice.value;
    }
  }).length;
};

const clearFilters = () => {
  selectedCategories.value = [];
  currentPrice.value = 0;
  router.replace({ name: "Events" });
  searchInput.value = "";
  selectedDate.value = ""
};

const toggleQuickCategory = (category) => {
  const index = selectedCategories.value.indexOf(category);
  if (index > -1) {
    selectedCategories.value.splice(index, 1);
  } else {
    selectedCategories.value.push(category);
  }
};

const handleToggleSave = (data) => {
  // Handle bookmark toggle if needed
  console.log("Bookmark toggled:", data);
};
</script>

<style scoped>
.scrollbar-hide::-webkit-scrollbar {
  display: none;
}

.scrollbar-hide {
  -ms-overflow-style: none;
  scrollbar-width: none;
}

/* Custom scrollbar for filter sidebar */
.overflow-y-auto::-webkit-scrollbar {
  width: 4px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: transparent;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: var(--color-primary);
  border-radius: 2px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: var(--color-hover);
}

/* Smooth transitions */
* {
  transition-property:
    background-color, border-color, color, fill, stroke, opacity, box-shadow,
    transform;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 150ms;
}
</style>
