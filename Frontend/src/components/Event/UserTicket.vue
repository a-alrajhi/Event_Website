<template>
  <div
    class="max-w-md mx-auto mt-10 bg-white dark:bg-gray-800 rounded-2xl shadow-lg border border-gray-200 dark:border-gray-700 overflow-hidden"
  >
    <!-- Event Image -->
    <div class="h-40 w-full bg-gray-100 dark:bg-gray-700">
      <img
        :src="eventImageUrl"
        alt="Event Banner"
        class="h-full w-full object-cover"
      />
    </div>

    <!-- Content -->
    <div class="p-6 text-center space-y-6">
      <!-- Event Info -->
      <div>
        <h2 class="text-xl font-bold text-gray-900 dark:text-white">
          {{ eventName }}
        </h2>
        <p class="text-sm text-gray-600 dark:text-gray-400">{{ eventDate }}</p>
      </div>

      <!-- QR Code -->
      <div class="relative">
        <div class="absolute inset-0 bg-blue-400/10 rounded-2xl blur-2xl"></div>
        <div
          class="relative z-10 inline-block bg-white dark:bg-gray-700 p-4 rounded-2xl shadow-md border border-gray-200 dark:border-gray-600"
        >
          <VQrcode
            :value="qrCodeValue"
            :size="160"
            :options="{ color: { dark: '#1f2937', light: '#ffffff' } }"
          />
        </div>
      </div>

      <!-- Ticket Info -->
      <div class="space-y-1">
        <p class="text-sm text-gray-500 dark:text-gray-400">Ticket Type</p>
        <p class="text-lg font-bold text-gray-800 dark:text-white">
          {{ ticketType }}
        </p>
      </div>

      <!-- Count -->
      <div class="space-y-1">
        <p class="text-sm text-gray-500 dark:text-gray-400">Count</p>
        <p class="text-lg font-bold text-gray-800 dark:text-white">
          x{{ count }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import VQrcode from "qrcode.vue";

const props = defineProps({
  qrCodeValue: { type: String, required: true },
  ticketType: { type: String, required: true },
  count: { type: Number, default: 1 },
  eventName: { type: String, required: true },
  eventDate: { type: String, required: true },
  eventImageUrl: { type: String, required: true }, // e.g., banner or thumbnail image
});
</script>
