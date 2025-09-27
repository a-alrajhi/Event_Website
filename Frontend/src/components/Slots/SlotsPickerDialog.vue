<!--
Slots Picker Dialog Component
@author: Abdulrahman Al Rajhi
@since: 9/27/2025
@version: 1.0
-->

<template>
  <!-- if showSlotDialog is true, show this Dialog -->

  <!-- Forces the dialog to render directly inside <body> (not inside the sidebar). -->
  <Teleport to="body">
    <Transition name="modalDialog" appear>
      <div
        v-if="modelValue"
        class="fixed inset-0 flex items-center justify-center bg-black/60 backdrop-blur-sm z-50 p-4"
      >
        <div
          class="bg-white dark:bg-gray-800 rounded-2xl shadow-2xl max-w-md w-full max-h-[90vh] overflow-hidden transform transition-all duration-300"
          @click.stop
        >
          <!-- Modal Header -->
          <div
            class="bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-hover)] px-6 py-4 text-white"
          >
            <div class="flex items-center justify-between">
              <h2 class="text-xl font-bold">Select Your Time Slot</h2>
              <button
                @click="$emit('update:modelValue', false)"
                class="p-1 rounded-full hover:bg-white/20 transition-colors duration-200"
              >
                <svg
                  class="w-9 h-9"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M6 18L18 6M6 6l12 12"
                  />
                </svg>
              </button>
            </div>
          </div>

          <!-- Modal Body -->
          <div class="p-6 overflow-y-auto max-h-[calc(90vh-140px)]">
            <!-- Dates list Selection -->
            <div class="mb-6">
              <h3
                class="text-lg font-semibold text-gray-800 dark:text-white mb-3"
              >
                Choose a Date
              </h3>
              <div class="grid gap-2">
                <div
                  v-for="(timeSlots, date) in groupedSlots"
                  :key="date"
                  class="p-3 border-2 rounded-xl cursor-pointer transition-all duration-200 hover:shadow-md"
                  :class="[
                    selectedDate === date
                      ? 'border-[var(--color-primary)] bg-blue-50 dark:bg-blue-900/30 text-[var(--color-primary)] dark:text-blue-300 shadow-md'
                      : 'border-gray-200 dark:border-gray-600 hover:border-[var(--color-primary)] dark:hover:border-[var(--color-primary)] hover:bg-gray-50 dark:hover:bg-gray-700',
                  ]"
                  @click="selectDate(date)"
                >
                  <div class="flex items-center justify-between">
                    <span class="font-medium">{{ formatDate(date) }}</span>

                    <!-- show how many slots available -->
                    <span class="text-sm text-gray-500 dark:text-gray-400"
                      >{{ timeSlots.length }} slots</span
                    >
                  </div>
                </div>
              </div>
            </div>

            <!-- Time slots for the selected date -->
            <Transition name="slide-fade" mode="out-in">
              <div v-if="selectedDate" key="time-slots" class="mb-6">
                <!-- subtitle -->
                <h3
                  class="text-lg font-semibold text-gray-800 dark:text-white mb-3"
                >
                  Available Times for {{ formatDate(selectedDate) }}
                </h3>

                <div class="grid grid-cols-2 gap-2">
                  <!-- Time slot button -->
                  <button
                    class="p-3 text-left border-2 rounded-lg transition-all duration-200 hover:shadow-md focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)] focus:ring-opacity-50"
                    v-for="slot in groupedSlots[selectedDate]"
                    :key="slot.id"
                    :class="[
                      selectedSlot && selectedSlot.id === slot.id
                        ? 'border-[var(--color-primary)] bg-green-50 dark:bg-green-900/30 text-[var(--color-primary)] dark:text-green-300 shadow-md'
                        : 'border-gray-200 dark:border-gray-600 hover:border-[var(--color-primary)] dark:hover:border-[var(--color-primary)] hover:bg-gray-50 dark:hover:bg-gray-700',
                    ]"
                    @click="selectSlot(slot)"
                  >
                    <!-- Time slot details -->
                    <div class="text-sm font-medium">
                      {{ slot.startTime }} - {{ slot.endTime }}
                    </div>

                    <!-- Selected indicator -->
                    <div
                      v-if="selectedSlot && selectedSlot.id === slot.id"
                      class="flex items-center mt-1"
                    >
                      <svg
                        class="w-4 h-4 text-green-600 dark:text-green-400 mr-1"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                          clip-rule="evenodd"
                        />
                      </svg>
                      <span class="text-xs">Selected</span>
                    </div>
                  </button>
                </div>
              </div>
            </Transition>
          </div>

          <!-- Modal Footer -->
          <div
            class="bg-gray-50 dark:bg-gray-700 px-6 py-4 flex gap-3 justify-end border-t border-gray-200 dark:border-gray-600"
          >
            <!-- cancel button -->
            <button
              @click="$emit('update:modelValue', false)"
              class="px-4 py-2 text-gray-600 dark:text-gray-300 hover:text-gray-800 dark:hover:text-white font-medium transition-colors duration-200 cursor-pointer"
            >
              Cancel
            </button>

            <!-- confirm button -->
            <div>
              <button
                :disabled="!selectedSlot"
                @click="confirm"
                class="px-6 py-2 bg-gradient-to-r from-[var(--color-primary)] to-[var(--color-hover)] hover:from-[var(--color-hover)] hover:to-[var(--color-primary)] text-white font-medium rounded-lg shadow-lg transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed hover:shadow-xl hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 cursor-pointer"
              >
                <span v-if="selectedSlot">Confirm Booking</span>
                <span v-else>Select a Time Slot</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { computed, ref } from "vue";
import dayjs from "dayjs";

const props = defineProps({
  slots: { type: Array, required: true },
  modelValue: { type: Boolean, required: true },
});

// modelValue == value of showSlotDialog
const emits = defineEmits(["update:modelValue", "confirm"]);

const selectedSlot = ref(null);
const selectedDate = ref(null);

function selectDate(date) {
  selectedDate.value = date;
  selectedSlot.value = null; // Reset slot selection when date changes
}

function selectSlot(slot) {
  selectedSlot.value = slot;
}

function confirm() {
  if (selectedSlot.value) {
    emits("confirm", selectedSlot.value); // goes to goToTicketPage()
    emits("update:modelValue", false); // close the modal
    selectedSlot.value = null; // reset selection
    selectedDate.value = null; // reset date
  }
}

// For grouping slots by date
/**
{
  "2025-09-20": [ {id: 1, startTime: "10:00", endTime: "11:00"}, ... ],
  "2025-09-21": [ {id: 2, startTime: "14:00", endTime: "15:00"}, ... ]
}
 */
const groupedSlots = computed(() => {
  return props.slots.reduce((acc, slot) => {
    if (!acc[slot.date]) acc[slot.date] = [];
    acc[slot.date].push(slot);
    return acc;
  }, {});
});

function formatDate(date) {
  return dayjs(date).format("ddd, MMM D");
}
</script>

<style scoped>
/* Modal transition animations */
.modalDialog-enter-active,
.modalDialog-leave-active {
  transition: opacity 0.3s ease;
}

.modalDialog-enter-active .bg-white {
  transition: transform 0.3s ease;
}

.modalDialog-enter-from,
.modalDialog-leave-to {
  opacity: 0;
}

.modal-enter-from .bg-white {
  transform: scale(0.9) translateY(-20px);
}

/* Slide fade transition for time slots */
.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
}

.slide-fade-enter-from {
  opacity: 0;
  transform: translateX(-10px);
}

.slide-fade-leave-to {
  opacity: 0;
  transform: translateX(10px);
}
</style>
