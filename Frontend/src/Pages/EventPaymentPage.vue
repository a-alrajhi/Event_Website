<!--
Event Payment Page - Handles payment processing and order summary
@author: Abdulrahman Al Rajhi
@since: 9/18/2025
@version: 1.0
-->

<!-- EventPaymentPage -->

<template>
  <!-- Breadcrumb component step 2 -->
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
        <div class="flex-1 h-0.5 bg-green-300 mx-4"></div>

        <!-- Step 2: Payment (Active) -->
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
                d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z"
              ></path>
            </svg>
            <div
              class="absolute inset-0 rounded-full bg-blue-600 animate-ping opacity-25"
            ></div>
          </div>
          <div class="mt-4 text-center">
            <h3 class="font-bold text-gray-900 text-lg">Payment</h3>
            <p class="text-sm text-blue-600 font-medium">Current Step</p>
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
              d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z"
            ></path>
          </svg>
        </div>
        <h3 class="font-bold text-gray-900 text-xl mb-2">Payment</h3>
        <p class="text-sm text-blue-600 font-medium">Step 2 of 3</p>
      </div>
    </div>
  </div>

  <!-- Main Content Area -->
  <div class="max-w-[105rem] mx-auto mt-12 px-6">
    <div class="flex flex-col md:flex-row gap-6">
      <!-- Left Section (Order Info) -->
      <div class="flex-1 bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold mb-4">Order Summary</h2>

        <!-- Event card -->
        <div class="bg-white shadow-md rounded-xl overflow-hidden">
          <!-- Image -->
          <img
            :src="paymentStore.event?.photoUrl"
            :alt="paymentStore.event?.name"
            class="h-48 w-full object-cover"
          />

          <div class="p-4">
            <!-- Common Info -->
            <h3 class="text-lg font-semibold text-gray-800">
              {{ paymentStore.event?.name }}
            </h3>
            <p class="text-sm text-gray-600 line-clamp-2 mt-1">
              {{ paymentStore.event?.description }}
            </p>

            <!-- Event Date and Time -->
            <div class="mt-6 flex justify-between items-center w-full">
              <div class="flex items-center">
                <span class="text-sm text-gray-500">
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
                <span class="ml-2 font-medium text-gray-500">
                  {{ eventDate }}
                </span>
              </div>

              <!-- Event starting time section -->
              <div class="flex items-center">
                <span class="text-sm text-gray-500">
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
                <span class="ml-2 font-medium text-gray-500">
                  {{ eventTime }}
                </span>
              </div>
            </div>

            <!-- Horizontal divider -->
            <div class="border-t border-gray-200 mx-4 my-4"></div>

            <!-- Ticket Summary -->
            <div class="space-y-3">
              <h4 class="text-md font-semibold text-gray-700 mb-3 text-center">
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

        <!-- Location Map -->
        <div class="mt-10">
          <EventLocationMap :location="paymentStore.event?.location" />
        </div>

        <!-- SubRules and remineder -->
        <SubRulesAndReminders />
      </div>

      <!-- Vertical Divider (hidden on mobile) -->
      <div class="hidden md:block w-px bg-gray-200"></div>
      <!-- Right Section (Payment Form) -->
      <div class="flex-1 bg-white rounded-xl shadow-md p-6">
        <h2 class="text-xl font-bold mb-4">Payment Methods</h2>

        <PaymentAccordion />
        <!-- Total Summary -->
        <TotalPayment />
      </div>
    </div>
  </div>
</template>

<script setup>
/**
 * EventPaymentPage - Payment processing and checkout
 */
import { usePaymentStore } from "../stores/paymentStore";
import { computed } from "vue";
import dayjs from "dayjs";
import EventLocationMap from "../components/Event/EventLocationMap.vue";
import SubRulesAndReminders from "../components/Event/SubRulesAndReminders.vue";
import PaymentAccordion from "../Utils/PaymentAccordion.vue";
import TotalPayment from "../Utils/TotalPayment.vue";

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
