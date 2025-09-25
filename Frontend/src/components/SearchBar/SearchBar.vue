<template>
  <form @submit.prevent="onSubmit">
    <label for="search" class="sr-only">Search</label>
    <div class="relative">
      <!-- Search Icon -->
      <div
        class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none"
      >
        <svg
          class="w-4 h-4 text-gray-500 dark:text-gray-400"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 20 20"
        >
          <path
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
          />
        </svg>
      </div>

      <!-- Search Input -->
      <input
        v-model="query"
        type="search"
        id="search"
        placeholder="Search..."
        @input="onInput"
        class="block w-full p-2 ps-10 text-sm rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-gray-900 dark:text-white placeholder-gray-400 dark:placeholder-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 dark:focus:ring-blue-400 focus:border-blue-500 dark:focus:border-blue-400 transition"
      />
    </div>
  </form>
</template>

<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const emit = defineEmits(["search"]);
const query = ref("");
const route = useRoute();
const router = useRouter();

const isOnEventsPage = route.name === "Events";
console.log(isOnEventsPage);

const onInput = () => {
  if (isOnEventsPage) {
    emit("search", query.value.trim());
  }
};

const onSubmit = () => {
  const q = query.value.trim();
  if (!q) return;

  if (isOnEventsPage) {
    emit("search", q);
  } else {
    router.push({ name: "Events", query: { q } });
  }
};
</script>
