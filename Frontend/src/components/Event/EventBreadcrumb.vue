<!--
Event Breadcrumb Component
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->
<template>
  <!-- Color  -->
  <!-- class="flex items-center justify-between p-4 bg-white border border-gray-200 rounded-lg shadow-sm bg-gradient-to-r from-neutral-900 to-sky-900 dark:border-gray-700 mb-6" -->
  <div
    class="flex items-center justify-between p-4 border border-transparent rounded-lg shadow-none bg-transparent mb-6"
  >
    <!-- Tailwind CSS Breadcrumb Component -->
    <nav class="flex" aria-label="Breadcrumb">
      <ol
        class="inline-flex items-center space-x-1 md:space-x-2 rtl:space-x-reverse"
      >
        <li class="inline-flex items-center">
          <a
            href="#"
            class="inline-flex items-center text-sm font-medium text-gray-800 hover:text-blue-600 dark:text-gray-400 dark:hover:text-gray-500"
          >
            <svg
              class="w-3 h-3 me-2.5"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                d="m19.707 9.293-2-2-7-7a1 1 0 0 0-1.414 0l-7 7-2 2a1 1 0 0 0 1.414 1.414L2 10.414V18a2 2 0 0 0 2 2h3a1 1 0 0 0 1-1v-4a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v4a1 1 0 0 0 1 1h3a2 2 0 0 0 2-2v-7.586l.293.293a1 1 0 0 0 1.414-1.414Z"
              />
            </svg>
            Home
          </a>
        </li>
        <li>
          <div class="flex items-center">
            <svg
              class="rtl:rotate-180 w-3 h-3 text-gray-400 mx-1"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 6 10"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m1 9 4-4-4-4"
              />
            </svg>
            <a
              href="#"
              class="ms-1 text-sm font-medium text-gray-700 hover:text-blue-600 md:ms-2 dark:text-gray-400 dark:hover:text-gray-500"
              >Events</a
            >
          </div>
        </li>
        <li aria-current="page">
          <div class="flex items-center">
            <svg
              class="rtl:rotate-180 w-3 h-3 text-gray-400 mx-1"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 6 10"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m1 9 4-4-4-4"
              />
            </svg>
            <span
              class="ms-1 text-sm font-medium text-gray-500 md:ms-2 dark:text-gray-400"
              >{{ pagePath.name }}</span
            >
          </div>
        </li>
      </ol>
    </nav>
    <div class="flex items-center space-x-3">
      <!-- share button -->
      <button
        @click="handleShare"
        class="flex items-center space-x-1 text-gray-700 hover:text-blue-600 dark:text-gray-400 dark:hover:text-gray-500"
        aria-label="Share"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="size-5"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M7.217 10.907a2.25 2.25 0 1 0 0 2.186m0-2.186c.18.324.283.696.283 1.093s-.103.77-.283 1.093m0-2.186 9.566-5.314m-9.566 7.5 9.566 5.314m0 0a2.25 2.25 0 1 0 3.935 2.186 2.25 2.25 0 0 0-3.935-2.186Zm0-12.814a2.25 2.25 0 1 0 3.933-2.185 2.25 2.25 0 0 0-3.933 2.185Z"
          />
        </svg>
        <span class="text-sm font-medium">Share</span>
      </button>

      <!-- Bookmark button -->
      <button
        @click="handleFavorite"
        class="flex items-center space-x-1 text-gray-700 hover:text-blue-600 dark:text-gray-400 dark:hover:text-gray-500"
        aria-label="Bookmark"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          :fill="isFavorited ? 'currentColor' : 'none'"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="size-5"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M17.593 3.322c1.1.128 1.907 1.077 1.907 2.185V21L12 17.25 4.5 21V5.507c0-1.108.806-2.057 1.907-2.185a48.507 48.507 0 0 1 11.186 0Z"
          />
        </svg>
        <span class="text-sm font-medium">
          {{ isFavorited ? "Remove Favorite" : "Add to Favorites" }}
        </span>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const props = defineProps({
  pagePath: {
    type: Object,
    required: true,
  },
});

/**
 * Handle the share button click event.
 */
function handleShare() {
  const shareData = {
    title: "Check this out",
    text: "Here is something interesting for you!",
    url: window.location.href,
  };

  if (navigator.share) {
    navigator.share(shareData).catch((error) => {
      console.error("Error sharing:", error);
    });
  } else {
    alert("Share not supported on this browser");
  }
}

const isFavorited = ref(false);

function handleFavorite() {
  isFavorited.value = !isFavorited.value;

  if (isFavorited.value) {
    alert("Added to favorites!");
  } else {
    alert("Removed from favorites.");
  }
}
</script>
