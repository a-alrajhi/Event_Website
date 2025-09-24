<template>
  <!-- Navbar -->
  <nav
    class="dark:bg-[var(--color-bg)] border-b dark:border-gray-800 text-[var(--color-text)] shadow-md"
  >
    <div
      class="max-w-screen-xl flex flex-col sm:flex-row sm:items-center justify-between mx-auto p-4 gap-4"
    >
      <!-- Logo -->
      <a href="/" class="flex items-center space-x-3">
        <img
          src="https://flowbite.com/docs/images/logo.svg"
          class="h-8"
          alt="Logo"
        />
        <span
          class="text-xl md:text-2xl font-bold text-[var(--color-primary)] drop-shadow-md"
          >EventApp</span
        >
      </a>

      <!-- Right Side: Search + Profile Photo -->
      <div class="flex items-center space-x-4 flex-wrap sm:flex-nowrap">
        <!-- Search -->
        <div class="flex-1 min-w-[200px] mt-2 sm:mt-0">
          <SearchBar />
        </div>

        <!-- Profile Photo -->
        <div>
          <img
            class="w-10 h-10 rounded-full border-2 border-[var(--color-primary)]"
            src="https://randomuser.me/api/portraits/men/32.jpg"
            alt="Profile Photo"
          />
        </div>
      </div>
    </div>
  </nav>

  <!-- Small Categories Navbar -->
  <section
    class="dark:bg-[var(--color-bg)] py-3 px-4 sm:px-6 flex justify-start sm:justify-center gap-4 sm:gap-6 overflow-x-auto scrollbar-hide border-b border-gray-200 dark:border-gray-800"
  >
    <div
      v-for="(category, index) in categories"
      :key="index"
      class="flex flex-col items-center cursor-pointer transition dark:text-[var(--color-text)] hover:text-[var(--color-primary)] min-w-[70px]"
    >
      <router-link :to="category.route"
        ><component
          :is="category.icon"
          class="w-6 h-6 mb-1 text-[var(--color-primary)] drop-shadow-md"
      /></router-link>
      <span class="text-xs sm:text-sm font-medium text-center">{{
        category.name
      }}</span>
    </div>
  </section>
</template>

<script>
import SearchBar from "../SearchBar/SearchBar.vue";
import { RouterLink } from "vue-router";
import {
  Music,
  Home,
  Star,
  Calendar,
  Users,
  MapPin,
  Ticket,
} from "lucide-vue-next";

export default {
  name: "Navbar",
  components: { SearchBar },
  data() {
    return {
      categories: [
        { name: "Home", icon: Home, route: "/" },
        { name: "Music", icon: Music, route: "/events?category=music" },
        { name: "Stars", icon: Star, route: "/" },
        { name: "Events", icon: Calendar, route: "/events" },
        { name: "Community", icon: Users, route: "/" },
        { name: "Locations", icon: MapPin, route: "/" },
        { name: "Tickets", icon: Ticket, route: "/" },
      ],
    };
  },
};
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
