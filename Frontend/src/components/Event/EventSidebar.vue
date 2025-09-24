<!--
Event Sidebar Component
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->
<template>
  <div
    class="max-w-sm p-6 bg-white border rounded-lg shadow-sm bg-gradient-to-r from-neutral-900 to-sky-900 border-gray-700 hover:scale-[1.02] transition-all duration-500"
  >
    <h5 class="mb-2 text-2xl font-bold tracking-tight text-gray-200">
      <!-- update this once backend is ready -->
      Starting from {{ startingPrice }} SAR!
    </h5>

    <p class="mb-3 font-normal text-gray-300">
      Price may vary depending on the event and availability.
    </p>

    <!-- button -->
    <!-- <RouterLink :to="slotsCounter(slots) ? `/event/slots/${eventId}` : `/event/ticket-types/${eventId}`" -->
    <button
      type="button"
      @click="handleBookNow"
      class="text-gray-100 bg-gradient-to-r from-sky-500 to-cyan-800 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-cyan-300 inline-flex items-center px-3 py-2 text-sm font-medium text-center rounded-lg hover:scale-[1.02] transition-all duration-300"
    >
      Book Now

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

      <!-- multiple slots available  go to slots page-->
      <!-- else ticket page-->
      <!-- </RouterLink> -->
    </button>

    <!-- Slot Picker Dialog -->
    <SlotsPickerDialog
      v-model="showSlotDialog"
      :slots="slots"
      @confirm="goToTicketPage"
    />

    <!-- horizontal line -->
    <hr class="my-6 border-gray-600" />

    <!-- Event's Date section -->
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
        <span class="ml-2 font-medium text-gray-100">
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
        <span class="ml-2 font-medium text-gray-100">
          {{ eventTime }}
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import dayjs from "dayjs";
import SlotsPickerDialog from "../Slots/SlotsPickerDialog.vue";

const props = defineProps({
  slots: { type: Array, required: true },
  startingPrice: {
    type: [Number, null],
    default: 0,
  },
  eventId: { type: Number, required: true },
});

const router = useRouter();
const showSlotDialog = ref(false);

function handleBookNow() {
  if (props.slots.length === 1) {
    goToTicketPage(props.slots[0]); // go directly to ticket page
  } else {
    showSlotDialog.value = true;
  }
}

function goToTicketPage(selectedSlot) {
  if (!selectedSlot?.id) return;

  router.push({
    name: "EventTicketTypes",
    params: { eventId: props.eventId },
    query: { slotId: selectedSlot.id },
  });
}

// Get the first slot or default to "TBD"
const firstSlot = computed(() => props.slots?.[0] ?? "TBD");

// Compute event date and time based on the first slot
const eventDateTime = computed(() => {
  if (!firstSlot.value) return null;
  return dayjs(
    `${firstSlot.value.date} ${firstSlot.value.startTime}`,
    "YYYY-MM-DD HH:mm:ss"
  );
});

const eventDate = computed(() => {
  return eventDateTime.value ? eventDateTime.value.format("ddd D MMM") : "TBD";
});

const eventTime = computed(() => {
  return eventDateTime.value ? eventDateTime.value.format("HH:mm") : "TBD";
});
</script>
