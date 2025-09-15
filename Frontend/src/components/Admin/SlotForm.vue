<script setup>
import { ref, computed, watch, watchEffect, onMounted } from "vue";
import {
  DatePicker,
  InputText,
  InputNumber,
  Button,
  Divider,
  Select,
} from "primevue";
import { useCreateEventStore } from "../../stores/createEventStore";

const props = defineProps({
  eventId: {
    type: Number,
    required: true,
  },
});

onMounted(async () => {
  await createEventStore.loadTicketTypes();
});

const createEventStore = useCreateEventStore();

// Helper to create an empty slot
function createEmptySlot() {
  return {
    date: null,
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

// State
const slots = ref(
  createEventStore.slots.length > 0
    ? createEventStore.slots.map((slot) => ({
        ...slot,
        date: slot.date ? new Date(slot.date) : null,
        ticketTypes: slot.ticketTypes.map((tt) => ({ ...tt })),
      }))
    : [createEmptySlot()]
);

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
  if (parts.length !== 2) return false;
  const [hours, minutes] = parts.map((p) => Number(p));
  if (isNaN(hours) || isNaN(minutes)) return false;
  return hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60;
}

// This version of getAvailableTTs ensures the current ticket's selection remains, and others can't pick the same
function getAvailableTTs(slot, ticket) {
  return createEventStore.ticketTypes.filter((tt) => {
    // Always allow the option that matches this ticket's current id (so it doesn't disappear)
    if (ticket.id === tt.id) {
      return true;
    }
    // Otherwise, filter out any tt.id that is already used by *other* ticket entries in this slot
    return !slot.ticketTypes.some(
      (other) => other !== ticket && other.id === tt.id
    );
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

// Other validations
const hasDuplicateSlots = computed(() => {
  const seen = new Set();
  for (const slot of slots.value) {
    if (!slot.date || !slot.startTime) continue;
    const key = `${slot.date.toISOString().split("T")[0]} ${slot.startTime}`;
    if (seen.has(key)) return true;
    seen.add(key);
  }
  return false;
});

const hasPastSlot = computed(() => {
  const now = new Date();
  return slots.value.some((slot) => {
    if (!slot.date || !slot.startTime) return false;
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
    if (isFormValid.value) {
      createEventStore.slots = newSlots;
      console.log(createEventStore.slots);
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
</script>

<template>
  <div class="flex flex-col gap-3">
    <span class="text-primary/90 block">Slot Info</span>

    <div
      v-for="(slot, slotIndex) in slots"
      :key="slotIndex"
      class="border rounded-lg border-solid border-primary/80 p-2 mb-4"
    >
      <h4 class="mb-2">Slot #{{ slotIndex + 1 }}</h4>

      <!-- Date & Time -->
      <div class="flex flex-col gap-3 mb-3">
        <div class="flex gap-3">
          <DatePicker
            class="w-full flex-9"
            v-model="slot.date"
            placeholder="Select Date"
            showIcon
          />
          <Button
            icon="pi pi-times"
            severity="danger"
            @click="removeSlot(slotIndex)"
            :disabled="slots.length === 1"
          />
        </div>
        <div class="flex gap-3">
          <InputText
            v-model="slot.startTime"
            placeholder="Start Time (HH:mm)"
          />
          <InputText v-model="slot.endTime" placeholder="End Time (HH:mm)" />
        </div>
      </div>

      <Divider />

      <!-- Ticket Types -->
      <h4 class="text-md font-semibold mb-3">Ticket Types</h4>

      <div
        v-for="(ticket, tIndex) in slot.ticketTypes"
        :key="tIndex"
        class="flex flex-col gap-3 mb-3"
      >
        <div class="flex gap-3">
          <Select
            v-model="ticket.id"
            :options="getAvailableTTs(slot, ticket)"
            optionLabel="name"
            optionValue="id"
            placeholder="Select Ticket Type"
            class="w-full flex-9"
            @change="onTicketTypeChange(ticket)"
          />
          <InputNumber
            v-model="ticket.capacity"
            placeholder="Capacity"
            class="flex-1"
            :min="1"
          />
        </div>

        <div class="flex gap-3 items-center">
          <InputNumber
            v-model="ticket.price"
            inputId="price"
            placeholder="Price in SAR"
            mode="currency"
            currency="SAR"
            class="flex-3"
            disabled
          />
          <Button
            icon="pi pi-trash"
            severity="secondary"
            @click="removeTicketType(slot, tIndex)"
            :disabled="slot.ticketTypes.length === 1"
          />
        </div>
      </div>

      <Button
        icon="pi pi-plus"
        label="Add Ticket Type"
        @click="addTicketType(slot)"
        :disabled="getAvailableTTs(slot, { id: 0 }).length === 0"
      />
    </div>

    <div class="flex justify-between items-center mt-4">
      <Button icon="pi pi-plus" label="Add Slot" @click="addSlot" />
    </div>

    <div v-if="hasDuplicateSlots" class="text-red-500 mt-2">
      Duplicate slot detected (same date and start time)
    </div>
    <div v-if="hasPastSlot" class="text-red-500 mt-2">
      One or more slots are in the past
    </div>
  </div>
</template>
