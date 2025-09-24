<script setup>
import { ref, computed, watch, watchEffect, onMounted } from "vue";
import { useCreateEventStore } from "../../stores/createEventStore";

const props = defineProps({
  eventId: {
    type: Number,
    required: true,
  },
});

const createEventStore = useCreateEventStore();

const slots = ref([]);

// Init slots based on edit mode
onMounted(async () => {
  await createEventStore.loadTicketTypes();

  // Wait for ticket types before mapping existing slot data
  if (createEventStore.isEditMode) {
    if (createEventStore.slots.length > 0) {
      slots.value = createEventStore.slots.map((slot) => {
        const dateObj = slot.date ? new Date(slot.date) : null;
        return {
          id: slot.id ?? null,
          date: dateObj,
          dateString: dateObj ? toLocalDateString(dateObj) : '',
          startTime: slot.startTime ?? null,
          endTime: slot.endTime ?? null,
          ticketTypes: slot.ticketTypes.map((tt) => {
            const matchingTT = createEventStore.ticketTypes.find(
              (storeTT) => storeTT.id === tt.ttId
            );
            return {
              id: matchingTT?.id ?? tt.ttId ?? null,
              capacity: tt.capacity ?? 0,
              price: matchingTT?.price ?? 0,
            };
          }),
        };
      });
    }
  } else {
    slots.value = [createEmptySlot()];
  }
});

// Helper to create an empty slot
function createEmptySlot() {
  return {
    date: null,
    dateString: '',
    startTime: null,
    endTime: null,
    ticketTypes: [
      {
        id: null, // selected ticket type id
        capacity: 0,
        price: 0,
      },
    ],
  };
}

// Actions
const addSlot = () => {
  slots.value.push(createEmptySlot());
};

const removeSlot = (index) => {
  slots.value.splice(index, 1);
};

const addTicketType = (slot) => {
  slot.ticketTypes.push({ id: null, capacity: 0, price: 0 });
};

const removeTicketType = (slot, index) => {
  slot.ticketTypes.splice(index, 1);
};

// Validation & Helpers

function isValidTime(timeStr) {
  if (!timeStr) return false;
  const parts = timeStr.split(":");
  if (parts.length < 2 || parts.length > 3) return false;
  const [hours, minutes] = parts.map((p) => Number(p));
  if (isNaN(hours) || isNaN(minutes)) return false;
  return hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60;
}

// This version of getAvailableTTs ensures the current ticket's selection remains, and others can't pick the same
function getAvailableTTs(slot, ticket) {
  const usedIds = slot.ticketTypes
    .filter((other) => other !== ticket && other.id !== null)
    .map((other) => other.id);

  return createEventStore.ticketTypes.filter((tt) => {
    return tt.id === ticket.id || !usedIds.includes(tt.id);
  });
}

// Compute if the ticketType entries are valid: id selected and capacity > 0
const validTT = computed(() => {
  return slots.value.every((slot) =>
    slot.ticketTypes.every(
      (tt) => tt.id !== null && tt.id !== 0 && tt.capacity > 0
    )
  );
});

const cleanTime = (t) => t?.trim().padStart(5, "0").slice(0, 5); // Ensure "9:0" → "09:00"

const hasDuplicateSlots = computed(() => {
  const seen = new Set();
  for (const slot of slots.value) {
    if (!slot.date || !slot.startTime) continue;

    const dateStr = toLocalDateString(slot.date);
    const timeStr = cleanTime(slot.startTime);

    const key = `${dateStr} ${timeStr}`;
    console.log(key);
    if (seen.has(key)) return true;
    seen.add(key);
  }
  return false;
});

const hasPastSlot = computed(() => {
  const now = new Date();
  return slots.value.some((slot) => {
    if (!slot.date || !slot.startTime || slot.id) return false;
    const [h, m] = slot.startTime.split(":").map(Number);
    if (isNaN(h) || isNaN(m)) return false;
    const dt = new Date(slot.date);
    dt.setHours(h, m, 0, 0);
    return dt < now;
  });
});

const hasSlot = computed(() => {
  return (
    slots.value.length > 0 &&
    slots.value.every((slot) => {
      return (
        slot.date &&
        slot.startTime &&
        slot.endTime &&
        isValidTime(slot.startTime) &&
        isValidTime(slot.endTime) &&
        slot.ticketTypes.length > 0
      );
    })
  );
});

const isFormValid = computed(() => {
  return (
    !hasDuplicateSlots.value &&
    !hasPastSlot.value &&
    hasSlot.value &&
    validTT.value
  );
});

// Watchers to update store
watch(isFormValid, (newVal) => {
  createEventStore.isAllowedNext = newVal;
});

watch(
  slots,
  (newSlots) => {
    console.log("local: " + slots.value[0].date);
    if (isFormValid.value) {
      createEventStore.slots = newSlots.map((slot) => ({
        ...slot,
        date: toLocalDateString(slot.date),
      }));
    }
  },
  { deep: true }
);

watchEffect(() => {
  createEventStore.isAllowedNext = isFormValid?.value ?? false;
});

// When the ticket type is changed, update its price
function onTicketTypeChange(ticket) {
  const sel = createEventStore.ticketTypes.find((tt) => tt.id === ticket.id);
  if (sel) {
    ticket.price = sel.price;
  } else {
    ticket.price = 0;
  }
}

function toLocalDateString(date) {
  if (!(date instanceof Date)) return null;
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

// Helper to update slot date from input
function updateSlotDate(slot, dateString) {
  if (dateString) {
    slot.date = new Date(dateString);
    slot.dateString = dateString;
  } else {
    slot.date = null;
    slot.dateString = '';
  }
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <!-- Section Title -->
    <div class="flex items-center gap-3">
      <i class="pi pi-calendar text-xl" style="color: var(--color-primary);"></i>
      <h3 class="text-xl font-semibold" style="color: var(--color-text);">
        Event Slots & Ticket Types
      </h3>
    </div>

    <!-- Slots Container -->
    <div class="space-y-6">
      <div
        v-for="(slot, slotIndex) in slots"
        :key="slotIndex"
        class="admin-card"
      >
        <!-- Slot Header -->
        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center gap-3">
            <div
              class="w-8 h-8 rounded-full flex items-center justify-center text-sm font-medium"
              style="background-color: var(--color-primary); color: var(--color-text);"
            >
              {{ slotIndex + 1 }}
            </div>
            <h4 class="text-lg font-semibold" style="color: var(--color-text);">
              Slot #{{ slotIndex + 1 }}
            </h4>
            <span v-if="slot.id" class="text-xs px-2 py-1 rounded-full" style="background-color: var(--color-bg); color: var(--color-gray);">
              ID: {{ slot.id }}
            </span>
          </div>
          <button
            @click="removeSlot(slotIndex)"
            :disabled="slots.length === 1"
            class="admin-btn-icon-danger"
            :class="{ 'opacity-50 cursor-not-allowed': slots.length === 1 }"
          >
            <i class="pi pi-times"></i>
          </button>
        </div>

        <!-- Date & Time -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          <div class="admin-form-group">
            <label class="admin-form-label">Event Date</label>
            <input
              type="date"
              v-model="slot.dateString"
              @input="updateSlotDate(slot, $event.target.value)"
              class="admin-input"
            />
          </div>
          <div class="admin-form-group">
            <label class="admin-form-label">Start Time</label>
            <input
              v-model="slot.startTime"
              type="time"
              class="admin-input"
              placeholder="HH:MM"
            />
          </div>
          <div class="admin-form-group">
            <label class="admin-form-label">End Time</label>
            <input
              v-model="slot.endTime"
              type="time"
              class="admin-input"
              placeholder="HH:MM"
            />
          </div>
        </div>

        <!-- Divider -->
        <div class="border-t my-4" style="border-color: var(--color-gray);"></div>

        <!-- Ticket Types Section -->
        <div class="mb-4">
          <div class="flex items-center gap-3 mb-4">
            <i class="pi pi-ticket text-lg" style="color: var(--color-primary);"></i>
            <h5 class="text-lg font-medium" style="color: var(--color-text);">
              Ticket Types
            </h5>
          </div>

          <div class="space-y-4">
            <div
              v-for="(ticket, tIndex) in slot.ticketTypes"
              :key="tIndex"
              class="p-4 rounded-lg border"
              style="background-color: var(--color-bg); border-color: var(--color-gray);"
            >
              <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <!-- Ticket Type Selection -->
                <div class="admin-form-group">
                  <label class="admin-form-label">Ticket Type</label>
                  <select
                    v-model="ticket.id"
                    @change="onTicketTypeChange(ticket)"
                    class="admin-select"
                  >
                    <option :value="null" disabled>Select ticket type</option>
                    <option
                      v-for="tt in getAvailableTTs(slot, ticket)"
                      :key="tt.id"
                      :value="tt.id"
                    >
                      {{ tt.name }}
                    </option>
                  </select>
                </div>

                <!-- Capacity -->
                <div class="admin-form-group">
                  <label class="admin-form-label">Capacity</label>
                  <input
                    v-model.number="ticket.capacity"
                    type="number"
                    min="1"
                    class="admin-input"
                    placeholder="Max attendees"
                  />
                </div>

                <!-- Price (Read-only) -->
                <div class="admin-form-group">
                  <label class="admin-form-label">Price (SAR)</label>
                  <div class="flex items-center gap-2">
                    <input
                      :value="ticket.price"
                      type="number"
                      class="admin-input opacity-75"
                      placeholder="Auto-filled"
                      readonly
                    />
                    <button
                      @click="removeTicketType(slot, tIndex)"
                      :disabled="slot.ticketTypes.length === 1"
                      class="admin-btn-icon-danger"
                      :class="{ 'opacity-50 cursor-not-allowed': slot.ticketTypes.length === 1 }"
                    >
                      <i class="pi pi-trash"></i>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Add Ticket Type Button -->
          <button
            @click="addTicketType(slot)"
            :disabled="getAvailableTTs(slot, { id: 0 }).length === 0"
            class="admin-btn-secondary mt-4"
            :class="{ 'opacity-50 cursor-not-allowed': getAvailableTTs(slot, { id: 0 }).length === 0 }"
          >
            <i class="pi pi-plus"></i>
            Add Ticket Type
          </button>
        </div>
      </div>
    </div>

    <!-- Add Slot Button -->
    <div class="flex justify-center">
      <button
        @click="addSlot"
        class="admin-btn-primary"
      >
        <i class="pi pi-plus"></i>
        Add New Slot
      </button>
    </div>

    <!-- Error Messages -->
    <div v-if="hasDuplicateSlots || hasPastSlot" class="space-y-2">
      <div
        v-if="hasDuplicateSlots"
        class="admin-toast error"
      >
        <div class="flex items-center gap-2">
          <i class="pi pi-exclamation-triangle"></i>
          <span>Duplicate slot detected (same date and start time)</span>
        </div>
      </div>
      <div
        v-if="hasPastSlot"
        class="admin-toast error"
      >
        <div class="flex items-center gap-2">
          <i class="pi pi-clock"></i>
          <span>One or more slots are scheduled in the past</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";

.space-y-6 > * + * {
  margin-top: 1.5rem;
}

.space-y-4 > * + * {
  margin-top: 1rem;
}

.space-y-2 > * + * {
  margin-top: 0.5rem;
}
</style>
