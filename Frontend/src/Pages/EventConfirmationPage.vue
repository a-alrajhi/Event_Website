<!--
Event Confirmation Page
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->

<template>
  <!-- Breadcrumb component step 3 -->
  <div class="max-w-[105rem] mx-auto mb-8 px-4">
    <div
      class="bg-white rounded-xl shadow-md border border-gray-100 p-3 overflow-hidden relative"
    >
      <!-- Background decoration -->
      <div
        class="absolute top-0 right-0 w-32 h-32 bg-gradient-to-bl from-blue-50 to-transparent rounded-bl-full opacity-50"
      ></div>
      <div
        class="absolute bottom-0 left-0 w-24 h-24 bg-gradient-to-tr from-purple-50 to-transparent rounded-tr-full opacity-50"
      ></div>

      <!-- Desktop Version -->
      <div class="hidden md:flex items-center justify-between relative z-10">
        <!-- Step 1: Ticket Selection (Completed) -->
        <div class="flex flex-col items-center">
          <div
            class="w-16 h-16 bg-green-500 rounded-full flex items-center justify-center shadow-md"
          >
            <svg
              class="w-8 h-8 text-white"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 13l4 4L19 7"
              ></path>
            </svg>
          </div>
          <div class="mt-4 text-center">
            <h3 class="font-bold text-green-600 text-lg">Ticket Selection</h3>
            <p class="text-sm text-green-500 font-medium">Completed</p>
          </div>
        </div>

        <!-- Connector Line -->
        <div
          class="flex-1 h-1 bg-gradient-to-r from-green-400/60 to-green-300/80 mx-6 rounded-full shadow-md"
        ></div>

        <!-- Step 2: Payment (Completed) -->
        <div class="flex flex-col items-center">
          <div
            class="w-16 h-16 bg-green-500 rounded-full flex items-center justify-center shadow-md"
          >
            <svg
              class="w-8 h-8 text-white"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 13l4 4L19 7"
              ></path>
            </svg>
          </div>
          <div class="mt-4 text-center">
            <h3 class="font-bold text-green-600 text-lg">Payment</h3>
            <p class="text-sm text-green-500 font-medium">Completed</p>
          </div>
        </div>

        <!-- Connector Line -->
        <div
          class="flex-1 h-1 bg-gradient-to-r from-green-400/60 to-blue-400/80 mx-6 rounded-full shadow-md"
        ></div>

        <!-- Step 3: Confirmation (Active) -->
        <div class="flex flex-col items-center">
          <div
            class="w-16 h-16 bg-gradient-to-r from-blue-600 to-blue-700 rounded-full flex items-center justify-center shadow-lg relative"
          >
            <svg
              class="w-8 h-8 text-white"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
              ></path>
            </svg>
            <div
              class="absolute inset-0 rounded-full bg-blue-600 animate-ping opacity-25"
            ></div>
          </div>
          <div class="mt-4 text-center">
            <h3 class="font-bold text-gray-900 text-lg">Confirmation</h3>
            <p class="text-sm text-blue-600 font-medium">Current Step</p>
          </div>
        </div>
      </div>

      <!-- Mobile Version -->
      <div class="md:hidden text-center">
        <div
          class="w-20 h-20 bg-gradient-to-r from-blue-600 to-blue-700 rounded-full flex items-center justify-center shadow-lg mx-auto mb-4"
        >
          <svg
            class="w-10 h-10 text-white"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            ></path>
          </svg>
        </div>
        <h3 class="font-bold text-gray-900 text-xl mb-2">Confirmation</h3>
        <p class="text-sm text-blue-600 font-medium">Step 3 of 3</p>
      </div>
    </div>
  </div>

  <!-- Main Content Area -->
  <div class="max-w-[105rem] mx-auto mt-12 px-6">
    <div class="flex flex-col md:flex-row gap-6">
      <!-- Left Section (Order Info) -->
      <div class="flex-1 bg-white rounded-2xl shadow-md p-6">
        <!-- HEADER -->

        <!-- Thank You Message -->
        <!-- outer container -->
        <div class="relative mb-8 p-8 rounded-3xl overflow-hidden">
          <!-- Main msg container card -->
          <div
            class="relative bg-gradient-to-br from-white/90 to-white/60 backdrop-blur-md rounded-2xl shadow-md border border-white/30 p-8 hover:scale-[1.02] transition-all duration-300"
          >
            <div class="flex items-center justify-center mb-6">
              <!-- Success Icon -->
              <div class="relative">
                <div
                  class="absolute inset-0 bg-green-400/30 rounded-full blur-lg"
                ></div>
                <div
                  class="relative w-20 h-20 bg-gradient-to-br from-green-400/90 to-green-600/90 backdrop-blur-md rounded-full flex items-center justify-center shadow-xl border border-green-300/50 animate-bounce"
                >
                  <svg
                    class="w-10 h-10 text-white drop-shadow-lg"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="3"
                      d="M5 13l4 4L19 7"
                    ></path>
                  </svg>
                </div>
              </div>
            </div>

            <!-- msg -->
            <div class="text-center">
              <h2
                class="text-4xl font-black mb-4 bg-gradient-to-r from-green-600 to-blue-600 bg-clip-text text-transparent"
              >
                🎉 Thank You for Your Purchase! 🎉
              </h2>
              <p class="text-lg text-gray-700 font-medium leading-relaxed">
                Your booking has been confirmed successfully. Get ready for an
                amazing experience!
              </p>
            </div>
          </div>
        </div>

        <!-- Event card -->
        <div
          class="relative bg-gradient-to-br from-white/90 to-white/60 backdrop-blur-md rounded-2xl border border-white/30 p-8"
        >
          <div
            class="shadow-md rounded-xl overflow-hidden hover:scale-[1.02] transition-all duration-300"
          >
            <!-- Event Details HEADER -->
            <div
              class="relative bg-gradient-to-r from-gray-800/95 to-gray-900/95 backdrop-blur-md border-b border-gray-600/30"
            >
              <div
                class="absolute inset-0 bg-gradient-to-r from-blue-600/10 to-purple-600/10"
              ></div>
              <div class="relative p-6">
                <h3
                  class="text-2xl font-black text-white tracking-tight flex items-center"
                >
                  <svg
                    class="w-7 h-7 mr-3 text-blue-400 drop-shadow-lg"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2.5"
                      d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
                    ></path>
                  </svg>
                  Event Details
                </h3>
              </div>
            </div>
            <!-- Image -->
            <div class="relative">
              <img
                :src="paymentStore.event?.photoUrl"
                :alt="paymentStore.event?.name"
                class="h-80 w-full object-cover"
              />
              <!-- Gradient overlay -->
              <div
                class="absolute inset-0 bg-gradient-to-t from-black/40 to-transparent"
              ></div>
            </div>

            <!-- Event Details Body -->
            <div
              class="relative bg-gradient-to-br from-gray-800/10 to-gray-50/90 backdrop-blur-md p-6"
            >
              <!-- Common Info -->
              <div class="mb-6">
                <h4
                  class="text-2xl font-black text-gray-900 mb-3 tracking-tight"
                >
                  {{ paymentStore.event?.name }}
                </h4>
                <p class="text-gray-700 leading-relaxed font-medium">
                  {{ paymentStore.event?.description }}
                </p>
              </div>

              <!-- Event Date and Time -->
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <!-- Date Card -->
                <div
                  class="relative p-4 rounded-2xl bg-gradient-to-br from-blue-500/10 to-blue-600/20 backdrop-blur-sm border border-blue-300/30 hover:scale-105 transition-all duration-300"
                >
                  <div class="flex items-center">
                    <div class="relative">
                      <div
                        class="absolute inset-0 bg-blue-400/20 rounded-lg blur-sm"
                      ></div>
                      <div
                        class="relative w-12 h-12 bg-blue-500/90 backdrop-blur-md rounded-lg flex items-center justify-center shadow-lg border border-blue-400/50"
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke-width="2.5"
                          stroke="currentColor"
                          class="w-6 h-6 text-white drop-shadow-sm"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5"
                          />
                        </svg>
                      </div>
                    </div>
                    <div class="ml-4">
                      <p
                        class="text-xs text-blue-600 font-bold uppercase tracking-wide"
                      >
                        Event Date
                      </p>
                      <p class="text-lg font-black text-gray-900">
                        {{ eventDate }}
                      </p>
                    </div>
                  </div>
                </div>

                <!-- Time Card -->
                <div
                  class="relative p-4 rounded-2xl bg-gradient-to-br from-purple-500/10 to-purple-600/20 backdrop-blur-sm border border-purple-300/30 hover:scale-105 transition-all duration-300"
                >
                  <div class="flex items-center">
                    <div class="relative">
                      <div
                        class="absolute inset-0 bg-purple-400/20 rounded-lg blur-sm"
                      ></div>
                      <div
                        class="relative w-12 h-12 bg-purple-500/90 backdrop-blur-md rounded-lg flex items-center justify-center shadow-lg border border-purple-400/50"
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                          stroke-width="2.5"
                          stroke="currentColor"
                          class="w-6 h-6 text-white drop-shadow-sm"
                        >
                          <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            d="M12 6v6h4.5m4.5 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                          />
                        </svg>
                      </div>
                    </div>
                    <div class="ml-4">
                      <p
                        class="text-xs text-purple-600 font-bold uppercase tracking-wide"
                      >
                        Start Time
                      </p>
                      <p class="text-lg font-black text-gray-900">
                        {{ eventTime }}
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Horizontal divider -->
              <div class="border-t border-gray-200 mx-4 my-4"></div>

              <!-- Ticket Summary -->
              <div class="space-y-3">
                <h4
                  class="text-md font-semibold text-gray-700 mb-3 text-center"
                >
                  Selected Tickets
                </h4>

                <!-- ticket container -->
                <div class="space-y-2">
                  <!-- getting all tickets -->
                  <div
                    v-for="ticket in paymentStore.tickets"
                    :key="ticket.id"
                    class="bg-gray-50 rounded-lg p-3 border border-gray-200 hover:bg-gray-100 transition-colors duration-200"
                  >
                    <div class="flex items-center justify-between">
                      <div class="flex-1">
                        <h5 class="font-medium text-gray-900">
                          {{ ticket.name }}
                        </h5>
                        <p class="text-sm text-gray-600">
                          {{ formatCurrency(ticket.price) }} each
                        </p>
                      </div>

                      <div class="flex items-center space-x-3">
                        <div
                          class="bg-white rounded-full px-3 py-1 border border-gray-300"
                        >
                          <span class="text-sm font-semibold text-gray-700"
                            >{{ ticket.quantity }}x</span
                          >
                        </div>
                        <div class="text-right">
                          <div class="font-bold text-gray-900">
                            {{ formatCurrency(ticket.price * ticket.quantity) }}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Location Map -->
        <div class="mt-10">
          <EventLocationMap :location="paymentStore.event?.location" />
        </div>
      </div>
    </div>

    <!-- Ticket Component Section -->
    <div
      class="relative bg-gradient-to-br from-white/90 to-white/70 backdrop-blur-md rounded-2xl shadow-xl border border-white/40 p-8 mb-8 mt-8 hover:scale-[1.02] transition-all duration-300"
    >
      <div class="text-center">
        <h1
          class="text-4xl font-black text-gray-900 mb-4 tracking-tight flex items-center justify-center"
        >
          <svg
            class="w-8 h-8 mr-4 text-emerald-600 drop-shadow-lg"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2.5"
              d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"
            ></path>
          </svg>
          🎫 Your Digital Tickets
        </h1>
        <p class="text-lg text-gray-700 font-medium">
          Save these QR codes on your device for easy access at the event
        </p>
      </div>
    </div>

    <!-- iterating through the returned ticketDTO list and show them  -->
    <div v-for="ticket in newlyGeneratedTickets" :key="ticket.id" class="mt-6">
      <TicketComponent :ticket="ticket" />
    </div>
  </div>
  <div class="mt-12">
    <AppFooter />
  </div>
</template>

<script setup>
import { usePaymentStore } from "../stores/paymentStore";
import { computed, ref, onMounted } from "vue";
import dayjs from "dayjs";
import EventLocationMap from "../components/Event/EventLocationMap.vue";
import TicketComponent from "../components/Event/TicketComponent.vue";
import AppFooter from "../components/AppFooter/AppFooter.vue";

import { generateTickets } from "../apis/slotsAndTicketTypes";
// getting new generated tickets from response
const newlyGeneratedTickets = ref([]);

onMounted(async () => {
  newlyGeneratedTickets.value = await generateTickets();
  console.log("Generated Tickets: ", newlyGeneratedTickets.value);
});

const paymentStore = usePaymentStore();
console.log("Carried Tickets from paymentStore: ", paymentStore.tickets);

const selectedSlot = computed(() => {
  if (!paymentStore.event?.slots) return null;
  return paymentStore.event.slots.find(
    (slot) => String(slot.id) === String(paymentStore.slotId)
  );
});

// Compute event date and time based on the first slot
const eventDateTime = computed(() => {
  if (!selectedSlot.value) return null;
  return dayjs(
    `${selectedSlot.value.date} ${selectedSlot.value.startTime}`,
    "YYYY-MM-DD HH:mm:ss"
  );
});

const eventDate = computed(() => {
  return eventDateTime.value ? eventDateTime.value.format("ddd D MMM") : "TBD";
});

const eventTime = computed(() => {
  return eventDateTime.value ? eventDateTime.value.format("HH:mm") : "TBD";
});

function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "SAR",
  }).format(amount);
}
</script>
