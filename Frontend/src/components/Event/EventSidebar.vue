<template>
  <div
    class="max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow-sm bg-gradient-to-r from-neutral-900 to-sky-900 dark:border-gray-700"
  >
    <h5
      class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white"
    >
      <!-- update this once backend is ready -->
      Starting from 100{{ startingPrice }} SAR!
    </h5>

    <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">
      Price may vary depending on the event and availability.
    </p>

    <!-- button -->
    <a
      href="#"
      class="text-white bg-gradient-to-r from-sky-500 to-cyan-800 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-cyan-300 dark:focus:ring-cyan-800 inline-flex items-center px-3 py-2 text-sm font-medium text-center rounded-lg"
    >
      <!-- update this once backend is ready -->
      Book Now{{ bookNowBtn }}

      <!-- drawing arrow from button to right SVG -->
      <svg
        class="rtl:rotate-180 w-3.5 h-3.5 ms-2"
        aria-hidden="true"
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 14 10"
      >
        <path
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M1 5h12m0 0L9 1m4 4L9 9"
        />
      </svg>
    </a>

    <!-- horizontal line -->
    <hr class="my-6 border-gray-300 dark:border-gray-600" />

    <!-- Event's Date section -->
    <div class="mt-6 flex flex-row items-center gap-32">
      <div class="flex items-center">
        <span class="text-sm text-gray-500 dark:text-gray-400">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="size-6 text-gray-400"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5"
            />
          </svg>
        </span>
        <span class="ml-2 font-medium text-gray-900 dark:text-white"
          >Tue 16 Dec{{ date }}</span
        >
      </div>

      <!-- Event starting time section -->
      <div class="flex items-center">
        <span class="text-sm text-gray-500 dark:text-gray-400">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="size-6 text-gray-400"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
            />
          </svg>
        </span>
        <span class="ml-2 font-medium text-gray-900 dark:text-white"
          >21:30{{ eventStartingTime }}</span
        >
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  bookNowBtn: {
    type: Boolean,
    default: true,
  },
  startingPrice: {
    type: Number,
    required: true,
  },
  date: {
    type: String,
    required: true,
  },
  eventStartingTime: {
    type: String,
    required: true,
  },
});
</script>
