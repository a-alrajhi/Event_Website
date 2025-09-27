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
                <span class="font-medium">{{ totalElements }}</span>
                events found
                <span
                  v-if="selectedCategories.length > 0 || currentPrice > 0"
                  class="text-[var(--color-primary)]"
                >
                  (filtered)
                </span>
                <span v-if="totalPages > 1" class="text-gray-500 dark:text-gray-400">
                  • Page {{ currentPage }} of {{ totalPages }}
                </span>
              </p>
            </div>

            <div class="flex items-center gap-4">
              <div class="flex items-center gap-2">
                <label class="text-sm font-medium text-gray-700 dark:text-gray-300">
                  Show:
                </label>
                <select
                  v-model="itemsPerPage"
                  @change="currentPage = 1"
                  class="px-3 py-1 rounded-lg border text-sm bg-white dark:bg-gray-800 border-gray-300 dark:border-gray-600 text-gray-900 dark:text-white focus:border-[var(--color-primary)] focus:ring-1 focus:ring-[var(--color-primary)]"
                >
                  <option value="5">5</option>
                  <option value="10">10</option>
                  <option value="20">20</option>
                </select>
                <span class="text-sm text-gray-600 dark:text-gray-300">per page</span>
              </div>
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
            :events="paginatedEvents"
            :is-loading="isLoading"
            @toggle-save="handleToggleSave"
          />

          <!-- Pagination -->
          <div v-if="!isLoading && totalPages > 1" class="mt-12">
            <!-- Pagination Container -->
            <div class="event-card/80 backdrop-blur-sm rounded-2xl p-6 mx-auto max-w-fit shadow-lg border border-[var(--color-primary)]/20">
              <!-- Pagination Controls -->
              <div class="flex items-center justify-center gap-3">
                <!-- Previous Button -->
                <button
                  @click="currentPage = Math.max(1, currentPage - 1)"
                  :disabled="isFirstPage"
                  :class="[
                    'px-4 py-2.5 rounded-xl font-medium transition-all duration-300 flex items-center gap-2 shadow-sm',
                    isFirstPage
                      ? 'bg-gray-100 dark:bg-gray-800 text-gray-400 cursor-not-allowed opacity-50'
                      : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-200 hover:bg-[var(--color-primary)] hover:text-white hover:shadow-lg hover:scale-105'
                  ]"
                >
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
                  </svg>
                  <span class="hidden sm:inline">Previous</span>
                </button>

                <!-- Page Numbers -->
                <div class="flex items-center gap-2">
                  <!-- First page and ellipsis -->
                  <template v-if="currentPage > 3">
                    <button
                      @click="currentPage = 1"
                      class="w-11 h-11 rounded-xl font-semibold transition-all duration-300 bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-200 hover:bg-[var(--color-primary)] hover:text-white hover:shadow-lg hover:scale-105 shadow-sm"
                    >
                      1
                    </button>
                    <span v-if="currentPage > 4" class="px-1 text-gray-400 dark:text-gray-500 font-medium">•••</span>
                  </template>

                  <!-- Visible page range -->
                  <button
                    v-for="page in getVisiblePages()"
                    :key="page"
                    @click="currentPage = page"
                    :class="[
                      'w-11 h-11 rounded-xl font-semibold transition-all duration-300 shadow-sm',
                      currentPage === page
                        ? 'bg-[var(--color-primary)] text-white shadow-lg scale-110 ring-2 ring-[var(--color-primary)]/30'
                        : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-200 hover:bg-[var(--color-primary)] hover:text-white hover:shadow-lg hover:scale-105'
                    ]"
                  >
                    {{ page }}
                  </button>

                  <!-- Last page and ellipsis -->
                  <template v-if="currentPage < totalPages - 2">
                    <span v-if="currentPage < totalPages - 3" class="px-1 text-gray-400 dark:text-gray-500 font-medium">•••</span>
                    <button
                      @click="currentPage = totalPages"
                      class="w-11 h-11 rounded-xl font-semibold transition-all duration-300 bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-200 hover:bg-[var(--color-primary)] hover:text-white hover:shadow-lg hover:scale-105 shadow-sm"
                    >
                      {{ totalPages }}
                    </button>
                  </template>
                </div>

                <!-- Next Button -->
                <button
                  @click="currentPage = Math.min(totalPages, currentPage + 1)"
                  :disabled="isLastPage"
                  :class="[
                    'px-4 py-2.5 rounded-xl font-medium transition-all duration-300 flex items-center gap-2 shadow-sm',
                    isLastPage
                      ? 'bg-gray-100 dark:bg-gray-800 text-gray-400 cursor-not-allowed opacity-50'
                      : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-200 hover:bg-[var(--color-primary)] hover:text-white hover:shadow-lg hover:scale-105'
                  ]"
                >
                  <span class="hidden sm:inline">Next</span>
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
                  </svg>
                </button>
              </div>

              <!-- Pagination Info -->
              <div class="mt-4 text-center">
                <div class="text-sm text-gray-600 dark:text-gray-300">
                  Showing <span class="font-semibold text-[var(--color-primary)]">{{ currentPageInfo.start }}</span>
                  to <span class="font-semibold text-[var(--color-primary)]">{{ currentPageInfo.end }}</span>
                  of <span class="font-semibold text-[var(--color-primary)]">{{ currentPageInfo.total }}</span> events
                </div>
              </div>
            </div>
          </div>

          <!-- No Results -->
          <div
            v-if="!isLoading && events.length === 0"
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
import { getEvents, getCategories, getPriceRange } from "../apis/EventDetalisApi";
import DateCard from "../components/Cards/DateCard.vue";

const route = useRoute();
const router = useRouter();

const events = ref([]); // Current page events
const allEvents = ref([]); // All events for filtering
const isLoading = ref(true);
const selectedCategories = ref([]);
const priceRange = ref({ min: 0, max: 200 });
const currentPrice = ref(0);
const sidebarCategories = ref([]);
const searchInput = ref("");
const selectedDate = ref(null);

// Backend pagination state
const currentPage = ref(1);
const itemsPerPage = ref(10);
const totalElements = ref(0);
const totalPages = ref(0);
const isFirstPage = ref(true);
const isLastPage = ref(true);

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

    // First load: get all events for filtering + categories/price range
    if (currentPage.value === 1 && !allEvents.value.length) {
      const [allEventsResponse, categoriesData, priceRangeData] = await Promise.all([
        getEvents(0, 1000), // Get all events for filtering
        getCategories(),
        getPriceRange()
      ]);

      allEvents.value = allEventsResponse.content || [];
      sidebarCategories.value = categoriesData;
      priceRange.value = priceRangeData;

      // Apply URL filters after data is loaded
      applyUrlFilters();
    }

    // Apply filters and calculate pagination
    const filtered = getFilteredEvents();

    // Update pagination based on filtered results
    totalElements.value = filtered.length;
    totalPages.value = Math.ceil(filtered.length / itemsPerPage.value);
    isFirstPage.value = currentPage.value === 1;
    isLastPage.value = currentPage.value === totalPages.value;

    // Get events for current page
    const start = (currentPage.value - 1) * itemsPerPage.value;
    const end = start + itemsPerPage.value;
    events.value = filtered.slice(start, end);

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
    // Find matching category (case insensitive)
    const matchingCategory = sidebarCategories.value.find(
      (cat) => cat.toLowerCase() === categoryParam.toLowerCase()
    );

    if (matchingCategory) {
      selectedCategories.value = [matchingCategory];
    }
  }

  if (searchQuery && typeof searchQuery === "string") {
    searchInput.value = searchQuery.trim();
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

// Reset pagination when filters change and refetch data
watch([selectedCategories, currentPrice, searchInput, selectedDate], () => {
  currentPage.value = 1;
  fetchData(); // Refetch data with new filters
}, { deep: true });

// Refetch data when pagination parameters change (but not when filters change)
watch([currentPage, itemsPerPage], () => {
  fetchData();
});

onMounted(fetchData);

// Function to get filtered events from all events
const getFilteredEvents = () => {
  if (!Array.isArray(allEvents.value)) return [];

  return allEvents.value.filter(event => {
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
};

// For sidebar counts (computed property)
const filteredEvents = computed(() => getFilteredEvents());

// Backend pagination computed properties
const currentPageInfo = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value + 1;
  const end = Math.min(start + itemsPerPage.value - 1, totalElements.value);
  return { start, end, total: totalElements.value };
});

const paginatedEvents = computed(() => {
  return Array.isArray(events.value) ? events.value : []; // Backend already provides paginated results
});


const getCategoryCount = (category) =>
  Array.isArray(allEvents.value) ? allEvents.value.filter((event) => event.category === category).length : 0;

const getFilteredByPriceCount = () => {
  if (!Array.isArray(allEvents.value)) return 0;
  if (currentPrice.value === 0) return allEvents.value.length;

  return allEvents.value.filter(event => {
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
  selectedDate.value = null;
  currentPage.value = 1; // Reset to first page when clearing filters
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

// Function to get visible page numbers for pagination
const getVisiblePages = () => {
  const pages = [];
  const start = Math.max(1, currentPage.value - 2);
  const end = Math.min(totalPages.value, currentPage.value + 2);

  for (let i = start; i <= end; i++) {
    pages.push(i);
  }

  return pages;
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
