<!--
Event Location Map
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->
<template>
  <!-- Location Map Section -->
  <div class="relative p-8 rounded-3xl overflow-hidden">
    <!-- Glassmorphic container -->
    <div
      class="relative bg-gradient-to-br from-white/90 to-white/70 dark:from-gray-800/90 dark:to-gray-900/70 backdrop-blur-md rounded-2xl border border-white/40 dark:border-gray-600/40 overflow-hidden hover:scale-[1.02] transition-all duration-500"
    >
      <!-- HEADER -->
      <div
        class="relative bg-gradient-to-r from-emerald-600/95 to-green-700/95 backdrop-blur-md p-6 border-b border-emerald-500/30"
      >
        <div
          class="absolute inset-0 bg-gradient-to-r from-green-600/10 to-emerald-600/10"
        ></div>
        <div class="relative flex items-center">
          <div class="relative">
            <div class="absolute inset-0 bg-white/20 rounded-lg blur-sm"></div>
            <div
              class="relative w-12 h-12 bg-white/90 backdrop-blur-sm rounded-lg flex items-center justify-center shadow-lg border border-white/50"
            >
              <svg
                class="w-7 h-7 text-emerald-700 drop-shadow-sm"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2.5"
                  d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
                ></path>
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2.5"
                  d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"
                ></path>
              </svg>
            </div>
          </div>
          <div class="ml-4">
            <h3
              class="text-2xl font-black text-white tracking-tight drop-shadow-lg"
            >
              Event Location
            </h3>
            <p class="text-emerald-100 font-medium">
              Find your way to the venue
            </p>
          </div>
        </div>
      </div>

      <!-- Map Container -->
      <div class="relative p-6">
        <div
          class="relative rounded-2xl overflow-hidden shadow-2xl border border-gray-200/50 dark:border-gray-600/50"
        >
          <!-- Map iframe with styling -->
          <iframe
            :src="mapSrc"
            width="100%"
            height="400"
            style="border: 0"
            allowfullscreen=""
            loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"
            title="Event Location Map"
            class="rounded-2xl"
          >
          </iframe>
        </div>

        <!-- Location Info -->
        <div
          class="mt-6 relative p-6 rounded-2xl bg-gradient-to-br from-gray-50/80 to-white/60 dark:from-gray-700/80 dark:to-gray-800/60 backdrop-blur-sm border border-gray-200/30 dark:border-gray-600/30 shadow-lg"
        >
          <!-- Location Details -->
          <div class="flex items-start space-x-4">
            <div class="relative">
              <div
                class="absolute inset-0 bg-emerald-400/20 rounded-lg blur-sm"
              ></div>
              <div
                class="relative w-10 h-10 bg-emerald-500/90 backdrop-blur-sm rounded-lg flex items-center justify-center shadow-md border border-emerald-400/50"
              >
                <svg
                  class="w-5 h-5 text-white drop-shadow-sm"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z"
                  />
                </svg>
              </div>
            </div>
            <div class="flex-1">
              <h4
                class="text-xl font-black text-gray-900 dark:text-white mb-2 tracking-tight"
              >
                {{ location.name }}
              </h4>
              <p
                class="text-gray-700 dark:text-gray-300 font-medium text-lg mb-3"
              >
                {{ location.address }}
              </p>

              <!-- Additional Info -->
              <div
                v-if="location.additionalInfo"
                class="relative p-4 rounded-xl bg-gradient-to-r from-blue-500/10 to-indigo-600/20 dark:from-blue-600/20 dark:to-indigo-700/30 backdrop-blur-sm border border-blue-300/30 dark:border-blue-500/40"
              >
                <div class="flex items-center">
                  <svg
                    class="w-5 h-5 text-blue-600 dark:text-blue-400 mr-2 flex-shrink-0"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                    ></path>
                  </svg>
                  <p
                    class="text-blue-800 dark:text-blue-300 font-medium text-sm italic"
                  >
                    {{ location.additionalInfo }}
                  </p>
                </div>
              </div>

              <!-- Action Buttons -->
              <div class="flex flex-wrap gap-3 mt-4">
                <a
                  :href="`https://www.google.com/maps/dir/?api=1&destination=${location.latitude},${location.longitude}`"
                  target="_blank"
                  class="inline-flex items-center px-4 py-2 rounded-xl bg-gradient-to-r from-emerald-500 to-green-600 text-white font-bold text-sm shadow-lg hover:shadow-xl hover:scale-105 transition-all duration-300 border border-emerald-400/50"
                >
                  <svg
                    class="w-4 h-4 mr-2"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-1.447-.894L15 4m0 13V4m-6 3l6-3"
                    ></path>
                  </svg>
                  Get Directions
                </a>

                <button
                  @click="openInMaps"
                  class="inline-flex items-center px-4 py-2 rounded-xl bg-gradient-to-r from-blue-500 to-indigo-600 text-white font-bold text-sm shadow-lg hover:shadow-xl hover:scale-105 transition-all duration-300 border border-blue-400/50 cursor-pointer"
                >
                  <svg
                    class="w-4 h-4 mr-2"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M10 6H6a2 2 0 00-2 2v10a2 2 0 002 2h10a2 2 0 002-2v-4M14 4h6m0 0v6m0-6L10 14"
                    ></path>
                  </svg>
                  Open in Maps
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  location: { type: Object, required: true },
});

const mapSrc = `https://www.google.com/maps?q=${props.location.latitude},${props.location.longitude}&output=embed`;

const openInMaps = () => {
  const latitude = props.location.latitude;
  const longitude = props.location.longitude;

  window.open(
    `https://www.google.com/maps/search/?api=1&query=${latitude},${longitude}`,
    "_blank"
  );
};
</script>
