<template>
  <!-- Navbar -->

  <!-- old nav styling -->
  <!-- class="dark:bg-[var(--color-bg)] border-b dark:border-gray-800 text-[var(--color-text)] shadow-md"
  > -->
  <nav
    class="bg-white/80 dark:bg-gray-800/80 backdrop-blur-md border-b border-gray-200 dark:border-gray-700 text-gray-900 dark:text-white shadow-lg sticky top-0 z-50"
  >
    <div
      class="max-w-screen-xl flex flex-col sm:flex-row sm:items-center justify-between mx-auto p-4 gap-4"
    >
      <!-- Logo -->
      <router-link to="/" class="flex items-center space-x-3 hover:opacity-80 transition-opacity">
        <div class="w-8 h-8 bg-gradient-to-br from-[var(--color-primary)] via-[var(--color-secondary)] to-[var(--color-hover)] rounded-lg flex items-center justify-center shadow-lg">
          <svg class="w-5 h-5 text-white" viewBox="0 0 24 24" fill="currentColor">
            <path d="M17 12h-5v5h5v-5zM16 6V4l-2-2h-4L8 4v2H5c-1.1 0-2 .9-2 2v11c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-3zM10 4h4v2h-4V4zm9 15H5V8h14v11z"/>
            <circle cx="9" cy="12" r="1"/>
            <circle cx="15" cy="15" r="1"/>
          </svg>
        </div>
        <span
          class="text-xl md:text-2xl font-bold text-[var(--color-primary)] drop-shadow-md"
          >EventVision</span
        >
      </router-link>

      <!-- Right Side: Search + Profile Photo -->
      <div class="flex items-center space-x-4 flex-wrap sm:flex-nowrap">
        <!-- Search -->
        <div class="flex-1 min-w-[200px] mt-2 sm:mt-0">
          <SearchBar />
        </div>

        <!-- Profile Photo or Login Button -->
        <div class="relative z-50">
          <div v-if="authStore.isLoggedIn" class="relative">
            <button
              @click="showDropdown = !showDropdown"
              class="w-10 h-10 rounded-full border-2 border-[var(--color-primary)] hover:border-[var(--color-hover)] transition-colors cursor-pointer focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)] focus:ring-offset-2 bg-[var(--color-primary)] flex items-center justify-center"
            >
         <User class="w-6 h-6 text-white" />
            </button>

            <!-- Dropdown Menu -->
            <div
              v-if="showDropdown"
              class="absolute right-0 mt-2 w-48 event-card rounded-xl shadow-lg border border-[var(--color-primary)]/20 z-[9999]"
            >
              <div class="py-2">
                <router-link
                  to="/user-profile"
                  @click="showDropdown = false"
                  class="flex items-center gap-3 px-4 py-2 text-gray-900 dark:text-white hover:bg-[var(--color-primary)]/10 transition-colors"
                >
                  <User class="w-4 h-4 text-[var(--color-primary)]" />
                  Profile
                </router-link>
                <router-link
                  to="/admin/statistics"
                  v-if="authStore.role == 'ADMIN'"
                  @click="showDropdown = false"
                  class="flex items-center gap-3 px-4 py-2 text-gray-900 dark:text-white hover:bg-[var(--color-primary)]/10 transition-colors"
                >
                  <ShieldUser class="w-4 h-4 text-[var(--color-primary)]" />
                  Admin Dashboard
                </router-link>
                <hr class="border-[var(--color-primary)]/20 my-1" />
                <button
                  @click="handleLogout"
                  class="flex items-center gap-3 px-4 py-2 text-[var(--color-error)] hover:bg-[var(--color-error)]/10 transition-colors w-full text-left"
                >
                  <LogOut class="w-4 h-4" />
                  Logout
                </button>
              </div>
            </div>
          </div>

          <router-link
            v-else
            to="/login"
            class="bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white px-4 py-2 rounded-lg font-medium transition-colors"
          >
            Login
          </router-link>
        </div>
      </div>
    </div>
  </nav>

  <!-- Small Categories Navbar -->
  <section
    class="bg-gray-50 dark:bg-gray-900 py-3 px-4 sm:px-6 flex justify-start sm:justify-center gap-4 sm:gap-6 overflow-x-auto scrollbar-hide border-b border-gray-200 dark:border-gray-800 sticky top-[73px] z-40"
  >
    <div
      v-for="(category, index) in categories"
      :key="index"
      class="flex flex-col items-center cursor-pointer transition text-gray-900 dark:text-white hover:text-[var(--color-primary)] min-w-[70px]"
      @click="navigateTo(category.route)"
    >
      <component
        :is="category.icon"
        class="w-6 h-6 mb-1 text-[var(--color-primary)] drop-shadow-md"
      />
      <span class="text-xs sm:text-sm font-medium text-center">{{
        category.name
      }}</span>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../../stores/authStore';
import SearchBar from "../SearchBar/SearchBar.vue";
import {
  Home,
  Calendar,
  User,
  Music,
  MapPin,
  Star,
  Users,
  LogOut,
  ShieldUser
} from "lucide-vue-next";

const router = useRouter();
const authStore = useAuthStore();
const showDropdown = ref(false);

const categories = [
  { name: "Home", icon: Home, route: "/" },
  { name: "Events", icon: Calendar, route: "/events" },
  { name: "Music", icon: Music, route: "/events?category=music" },
  { name: "Sports", icon: Star, route: "/events?category=sports" },
  { name: "FAQ", icon: Users, route: "/faq" },
  { name: "Locations", icon: MapPin, route: "/location" },
];

const navigateTo = (route) => {
  if (route) {
    router.push(route);
  }
};

const handleLogout = () => {
  showDropdown.value = false;
  authStore.logout();
};

// Close dropdown when clicking outside
const handleClickOutside = (event) => {
  // Check if the click is outside the profile dropdown container
  const profileContainer = event.target.closest('.relative.z-50');
  if (!profileContainer && showDropdown.value) {
    showDropdown.value = false;
  }
};

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<style scoped>
/* Hide horizontal scrollbar */
.scrollbar-hide::-webkit-scrollbar {
  display: none;
}
.scrollbar-hide {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
</style>
