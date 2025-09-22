<!--
Event Ticket Types Component - Handles ticket selection and quantity management
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->

<template>
  <!-- Main Container -->
  <div
    class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50"
  >
    <div class="container mx-auto px-4 py-8">
      <!-- Header Section -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">
          Select Your Tickets
        </h1>
        <p class="text-gray-600">
          Choose the number of tickets you'd like to purchase
        </p>
      </div>

      <!-- Breadcrumb Stepper -->
      <!-- Ready to use component -->
      <div class="max-w-5xl mx-auto mb-12">
        <div
          class="bg-white rounded-2xl shadow-xl border border-gray-100 p-8 overflow-hidden relative"
        >
          <!-- Background decoration -->
          <div
            class="absolute top-0 right-0 w-32 h-32 bg-gradient-to-bl from-blue-50 to-transparent rounded-bl-full opacity-50"
          ></div>
          <div
            class="absolute bottom-0 left-0 w-24 h-24 bg-gradient-to-tr from-purple-50 to-transparent rounded-tr-full opacity-50"
          ></div>

          <!-- Desktop Version -->
          <div
            class="hidden md:flex items-center justify-between relative z-10"
          >
            <!-- Step 1: Ticket Selection (Active) -->
            <div class="flex flex-col items-center">
              <div
                class="w-16 h-16 bg-gradient-to-r from-blue-600 to-blue-700 rounded-full flex items-center justify-center shadow-lg relative"
              >
                <svg class="w-8 h-8 text-white" viewBox="0 0 32 32">
                  <path
                    fill="currentColor"
                    d="M28.46 9H27a1 1 0 0 0 0 2h1v12H12a2 2 0 0 0 0-.35 1 1 0 0 0-2 0 .35.35 0 0 1-.35.35h-5.3a.35.35 0 0 1-.35-.35v-11.3a.35.35 0 0 1 .35-.35h5.3a.35.35 0 0 1 .35.35 1 1 0 1 0 2 0 2 2 0 0 0 0-.35h11a1 1 0 0 0 0-2H11a1 1 0 0 0-.51.16A2.32 2.32 0 0 0 9.65 9h-5.3A2.35 2.35 0 0 0 2 11.35v11.3A2.35 2.35 0 0 0 4.35 25h5.3a2.32 2.32 0 0 0 .84-.16A1 1 0 0 0 11 25h17.46A1.54 1.54 0 0 0 30 23.46V10.54A1.54 1.54 0 0 0 28.46 9z"
                  />
                  <path
                    fill="currentColor"
                    d="M11 15a1 1 0 0 0 1-1 1 1 0 0 0-1-1 1 1 0 0 0-1 1 1 1 0 0 0 1 1zM10 17.11a1 1 0 0 0 2 0V17a1 1 0 0 0-2 0zM11 21a1 1 0 0 0 0-2 1 1 0 0 0 0 2zM20 18a1 1 0 0 0 1 1h4a1 1 0 0 0 0-2h-4a1 1 0 0 0-1 1zM25 20h-7a1 1 0 0 0 0 2h7a1 1 0 0 0 0-2z"
                  />
                </svg>
                <div
                  class="absolute inset-0 rounded-full bg-blue-600 animate-ping opacity-25"
                ></div>
              </div>
              <div class="mt-4 text-center">
                <h3 class="font-bold text-gray-900 text-lg">
                  Ticket Selection
                </h3>
                <p class="text-sm text-blue-600 font-medium">Current Step</p>
              </div>
            </div>

            <!-- Connector Line -->
            <div class="flex-1 h-0.5 bg-gray-200 mx-4"></div>

            <!-- Step 2: Payment -->
            <div class="flex flex-col items-center">
              <div
                class="w-16 h-16 bg-white border-4 border-gray-300 rounded-full flex items-center justify-center shadow-md"
              >
                <svg
                  class="w-8 h-8 text-gray-400"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z"
                  ></path>
                </svg>
              </div>
              <div class="mt-4 text-center">
                <h3 class="font-bold text-gray-500 text-lg">Payment</h3>
                <p class="text-sm text-gray-400 font-medium">Next Step</p>
              </div>
            </div>

            <!-- Connector Line -->
            <div class="flex-1 h-0.5 bg-gray-200 mx-4"></div>

            <!-- Step 3: Confirmation -->
            <div class="flex flex-col items-center">
              <div
                class="w-16 h-16 bg-white border-4 border-gray-300 rounded-full flex items-center justify-center shadow-md"
              >
                <svg
                  class="w-8 h-8 text-gray-400"
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
              <div class="mt-4 text-center">
                <h3 class="font-bold text-gray-500 text-lg">Confirmation</h3>
                <p class="text-sm text-gray-400 font-medium">Final Step</p>
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
                  d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a1 1 0 001 1h1a1 1 0 011-1V9a1 1 0 00-1-1H4V7a2 2 0 012-2h10a2 2 0 012 2v1h-1a1 1 0 00-1 1v1a1 1 0 001 1h1v3a2 2 0 01-2 2H5a2 2 0 01-2-2v-1h1a1 1 0 001-1v-1a1 1 0 00-1-1H3V7z"
                ></path>
              </svg>
            </div>
            <h3 class="font-bold text-gray-900 text-xl mb-2">
              Ticket Selection
            </h3>
            <p class="text-sm text-blue-600 font-medium">Step 1 of 3</p>
          </div>
        </div>
      </div>

      <!-- Ticket Selection Section -->
      <section class="max-w-4xl mx-auto">
        <!-- Loading State -->
        <div v-if="loading" class="flex justify-center items-center py-16">
          <div
            class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"
          ></div>
        </div>

        <!-- Error State -->
        <div
          v-else-if="error"
          class="bg-red-50 border border-red-200 rounded-xl p-6 text-center"
        >
          <div class="text-red-600 font-semibold mb-2">
            Oops! Something went wrong
          </div>
          <p class="text-red-500">{{ error }}</p>
        </div>

        <!-- Ticket List -->
        <!-- If all states passed -->
        <div v-else class="space-y-6">
          <div
            v-for="ticketInfo in ticketTypes"
            :key="ticketInfo.id"
            class="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1"
          >
            <!-- Current Ticket Info Container -->
            <div class="p-6">
              <!-- Responsive layout: stacked on mobile, side-by-side on large screens -->
              <div
                class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0"
              >
                <!-- ==== LEFT SIDE: TICKET INFORMATION ==== -->
                <div class="flex-1">
                  <div class="flex items-start space-x-4">
                    <!-- Ticket Icon -->
                    <div class="flex-shrink-0">
                      <div
                        class="w-12 h-12 bg-gradient-to-r from-neutral-900 to-sky-900 rounded-xl flex items-center justify-center"
                      >
                        <!-- SVG ticket icon -->
                        <!-- NEW custom ticket icon -->
                        <svg class="w-6 h-6 text-white" viewBox="0 0 32 32">
                          <path
                            fill="currentColor"
                            d="M28.46 9H27a1 1 0 0 0 0 2h1v12H12a2 2 0 0 0 0-.35 1 1 0 0 0-2 0 .35.35 0 0 1-.35.35h-5.3a.35.35 0 0 1-.35-.35v-11.3a.35.35 0 0 1 .35-.35h5.3a.35.35 0 0 1 .35.35 1 1 0 1 0 2 0 2 2 0 0 0 0-.35h11a1 1 0 0 0 0-2H11a1 1 0 0 0-.51.16A2.32 2.32 0 0 0 9.65 9h-5.3A2.35 2.35 0 0 0 2 11.35v11.3A2.35 2.35 0 0 0 4.35 25h5.3a2.32 2.32 0 0 0 .84-.16A1 1 0 0 0 11 25h17.46A1.54 1.54 0 0 0 30 23.46V10.54A1.54 1.54 0 0 0 28.46 9z"
                          />
                          <path
                            fill="currentColor"
                            d="M11 15a1 1 0 0 0 1-1 1 1 0 0 0-1-1 1 1 0 0 0-1 1 1 1 0 0 0 1 1zM10 17.11a1 1 0 0 0 2 0V17a1 1 0 0 0-2 0zM11 21a1 1 0 0 0 0-2 1 1 0 0 0 0 2zM20 18a1 1 0 0 0 1 1h4a1 1 0 0 0 0-2h-4a1 1 0 0 0-1 1zM25 20h-7a1 1 0 0 0 0 2h7a1 1 0 0 0 0-2z"
                          />
                        </svg>
                      </div>
                    </div>

                    <div class="flex-1 min-w-0">
                      <h3 class="text-lg font-bold text-gray-900 mb-1">
                        {{ ticketInfo.name }}
                      </h3>

                      <!-- Ticket Description -->
                      <p class="text-sm text-gray-600 mb-2">
                        {{ ticketInfo.name + " event ticket" }}
                      </p>

                      <!-- Ticket Price -->
                      <div class="flex items-center space-x-4">
                        <span class="text-2xl font-bold text-blue-600">{{
                          formatCurrency(ticketInfo.price)
                        }}</span>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- Quantity Selector -->
                <div class="lg:flex-shrink-0">
                  <!-- Gray Container -->
                  <div class="bg-gray-50 rounded-xl p-4">
                    <label
                      class="block text-sm font-medium text-gray-700 mb-3 text-center"
                    >
                      Quantity
                    </label>

                    <!-- Quantity Controls Container -->
                    <div class="flex items-center space-x-3">
                      <!-- Decrease Button -->
                      <button
                        @click="decrease(ticketInfo)"
                        :disabled="(ticketQuantities[ticketInfo.id] || 0) <= 0"
                        class="w-10 h-10 rounded-full bg-white border-2 border-gray-200 flex items-center justify-center hover:border-blue-500 hover:bg-blue-50 transition-colors duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
                      >
                        <span class="text-gray-900">-</span>
                      </button>

                      <!-- Quantity Display -->
                      <div class="w-16 text-center">
                        <span class="text-xl font-bold text-gray-900">{{
                          ticketQuantities[ticketInfo.id] || 0
                        }}</span>
                      </div>
                      <!-- Increase Button -->
                      <button
                        @click="increase(ticketInfo)"
                        :disabled="(ticketQuantities[ticketInfo.id] || 0) >= 50"
                        class="w-10 h-10 rounded-full bg-white border-2 border-gray-200 flex items-center justify-center hover:border-blue-500 hover:bg-blue-50 transition-colors duration-200"
                      >
                        <span class="text-gray-900">+</span>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <!-- only show when tickets > 1 -->
        <div
          v-if="totalTickets > 0"
          class="mt-8 bg-gradient-to-r from-blue-50 to-purple-50 rounded-2xl p-6 border border-blue-200"
        >
          <div
            class="flex flex-col sm:flex-row sm:items-center sm:justify-between space-y-4 sm:space-y-0"
          >
            <!-- Left side summary info -->
            <div>
              <h3 class="text-lg font-semibold text-gray-900">Order Summary</h3>

              <p class="text-sm text-gray-600">
                {{ totalTickets }} ticket{{ totalTickets > 1 ? "s" : "" }}
                selected
              </p>
            </div>

            <!-- Right side total price -->
            <div class="text-right">
              <div class="text-2xl font-bold text-gray-900">
                {{ formatCurrency(totalAmount) }}
              </div>
              <div class="text-sm text-gray-600">Total Amount</div>
            </div>
          </div>
        </div>

        <!-- Page Buttons -->
        <div class="mt-8 flex flex-col sm:flex-row gap-4 justify-center">
          <!-- Back To Event Button -->
          <button
            type="button"
            @click="goToEventPage"
            class="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700"
          >
            Back To Event
          </button>

          <!-- Confirm Button -->
          <button
            type="button"
            @click="goToPaymentPage"
            :disabled="totalTickets === 0"
            class="px-8 py-3 bg-gradient-to-r from-neutral-900 to-sky-900 text-white font-semibold rounded-xl shadow-lg hover:shadow-xl hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none"
          >
            <div class="flex items-center justify-center space-x-2">
              <span v-if="totalTickets === 0">Select Tickets</span>
              <span v-else> Confirm Selection </span>
            </div>
          </button>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getAllTicketTypesForEvent } from "../../apis/eventApi";
import { getFullEventDetails } from "../../apis/eventApi";
import { usePaymentStore } from "../../stores/paymentStore";

const route = useRoute();
const paymentStore = usePaymentStore();

const slotId = route.query?.slotId || null;
const eventId = route.params.eventId || null;

// list of all ticket types
const ticketTypes = ref([]);
const ticketQuantities = ref({});
// show loading spinner (state)
const loading = ref(true);
// show error message when fetching fails(state)
const error = ref(null);
const router = useRouter();

/**
 * Initialize component - fetch ticket types and event details
 */
onMounted(async () => {
  try {
    console.log("fetching ticket types for event:", eventId, "slot:", slotId);
    ticketTypes.value = await getAllTicketTypesForEvent(eventId);

    // fetch full event details and store in pinia store so we can access it later
    const event = await getFullEventDetails(eventId);
    paymentStore.event = event;

    console.log("fetched ticket types:", ticketTypes.value);
  } catch (err) {
    error.value = "Failed to load ticket types.";
    console.error(err);
  } finally {
    loading.value = false;
  }
});

/**
 * Navigate to payment page with selected tickets
 */
function goToPaymentPage() {
  if (!eventId) {
    console.error("No eventId found in route params.");
    return;
  }

  // build detailed ticket list
  const selectedTicketsDetailed = ticketTypes.value
    // getting all tickets with quantity > 0
    .filter((ticket) => ticketQuantities.value[ticket.id] > 0)
    .map((ticket) => ({
      // mapping to detailed object
      id: ticket.id,
      name: ticket.name,
      price: ticket.price,
      quantity: ticketQuantities.value[ticket.id],
      subtotal: ticket.price * ticketQuantities.value[ticket.id],
    }));

  // save to pinia store so can use them later
  paymentStore.setCheckoutData({
    event: paymentStore.event,
    eventId,
    slotId,
    tickets: selectedTicketsDetailed, // assign filtered tickets to tickets
    location: paymentStore.event?.location,
  });

  router.push({
    name: "EventPaymentPage",
    params: { eventId },
  });
}

/**
 * Navigate back to event details page
 */
function goToEventPage() {
  if (!eventId) {
    console.error("No eventId found in route params.");
    return;
  }

  router.push({
    name: "EventDetails",
    params: { id: eventId },
  });
}

/**
 * Increase ticket quantity
 */
function increase(ticket) {
  ticketQuantities.value[ticket.id] =
    (ticketQuantities.value[ticket.id] || 0) + 1;
}

/**
 * Decrease ticket quantity
 */
function decrease(ticket) {
  if ((ticketQuantities.value[ticket.id] || 0) > 0) {
    ticketQuantities.value[ticket.id]--;
  }
}

/**
 * Format currency value
 */
function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "SAR",
  }).format(amount);
}

/**
 * Calculate total number of tickets
 */
const totalTickets = computed(() => {
  return Object.values(ticketQuantities.value).reduce(
    (sum, qty) => sum + (qty || 0),
    0
  );
});

/**
 * Calculate total amount for all selected tickets
 */
const totalAmount = computed(() => {
  return ticketTypes.value.reduce((sum, ticket) => {
    const qty = ticketQuantities.value[ticket.id] || 0;
    return sum + ticket.price * qty;
  }, 0);
});
</script>
