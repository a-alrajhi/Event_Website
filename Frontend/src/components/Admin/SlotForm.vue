<script setup>
import { ref, computed, watch } from "vue";
import { DatePicker, InputText, InputNumber, Button, Divider } from "primevue";
import { useCreateEventStore } from "../../stores/createEventStore";

const props = defineProps({
  eventId: {
    type: Number,
    required: true,
  },
});

const createEventStore = useCreateEventStore();

const slots = ref([createEmptySlot()]);

function createEmptySlot() {
  return {
    date: null,
    startTime: null,
    endTime: null,
    ticketTypes: [
      {
        name: "",
        price: 0,
        capacity: 0,
      },
    ],
  };
}

const addSlot = () => slots.value.push(createEmptySlot());
const removeSlot = (index) => slots.value.splice(index, 1);

const addTicketType = (slot) => {
  slot.ticketTypes.push({ name: "", price: 0, capacity: 0 });
};
const removeTicketType = (slot, index) => {
  slot.ticketTypes.splice(index, 1);
};

const hasDuplicateSlots = computed(() => {
  const seen = new Set();
  for (const slot of slots.value) {
    if (!slot.date || !slot.startTime) continue;

    const key = `${slot.date.toDateString()} ${slot.startTime}`;
    if (seen.has(key)) return true;
    seen.add(key);
  }
  return false;
});

const hasPastSlot = computed(() => {
  const currentTime = new Date();
  return slots.value.some((slot) => {
    if (!slot.date || !slot.startTime) return false;

    const [hours, minutes] = slot.startTime.split(":").map(Number);
    const slotDateTime = new Date(slot.date);
    slotDateTime.setHours(hours, minutes, 0, 0);

    return slotDateTime < currentTime;
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
        slot.ticketTypes.length > 0 &&
        isValidTime(slot.startTime) &&
        isValidTime(slot.endTime)
      );
    })
  );
});

const isFormValid = computed(() => {
  console.log(hasDuplicateSlots.value);
  return !hasDuplicateSlots.value && !hasPastSlot.value && hasSlot.value;
});

watch(isFormValid, (newVal) => {
  createEventStore.isAllowedNext = newVal;
});

// const submitSlots = () => {
//   if (!isFormValid.value) return;

//   const payload = slots.value.map((slot) => ({
//     date: slot.date.toISOString().split("T")[0],
//     startTime: slot.startTime,
//     endTime: slot.endTime,
//     ticketTypes: slot.ticketTypes,
//   }));

//   console.log("SUBMITTING:", payload);
// };

function isValidTime(timeStr) {
  const [hours, minutes] = timeStr.split(":").map(Number);
  return (
    !isNaN(hours) &&
    !isNaN(minutes) &&
    hours >= 0 &&
    hours < 24 &&
    minutes >= 0 &&
    minutes < 60
  );
}
</script>

<template>
  <div class="flex flex-col gap-3">
    <span class="text-primary/90 block">Slot Info</span>
    <template
      v-for="(slot, slotIndex) in slots"
      :key="slotIndex"
      class="border rounded-lg border-solid border-primary/80"
    >
      <div class="p-2">
        <h4 class="mb-2">Slot #{{ slotIndex + 1 }}</h4>
        <div class="flex flex-col gap-3">
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

        <h4 class="text-md font-semibold mb-3">Ticket Types</h4>
        <div
          v-for="(ticket, tIndex) in slot.ticketTypes"
          :key="tIndex"
          class="flex flex-col gap-3 mb-3"
        >
          <h4 class="mb-1">Ticket Type #{{ tIndex + 1 }}</h4>
          <div class="flex gap-3">
            <InputText v-model="ticket.name" placeholder="Ticket Type Name" />
            <InputNumber v-model="ticket.capacity" placeholder="Capacity" />
          </div>
          <div class="flex gap-3">
            <InputNumber
              v-model="ticket.price"
              inputId="price"
              placeholder="Price in SAR"
              mode="currency"
              currency="SAR"
              class="flex-3"
            />
            <Button
              icon="pi pi-trash"
              severity="secondary"
              @click="removeTicketType(slot, tIndex)"
            />
          </div>
        </div>
      </div>
      <Button
        icon="pi pi-plus"
        label="Add Ticket Type"
        @click="addTicketType(slot)"
      />
      <Divider />
    </template>

    <div class="flex justify-between items-center mt-4">
      <Button icon="pi pi-plus" label="Add Slot" @click="addSlot" />
    </div>

    <div v-if="hasDuplicateSlots" class="text-red-500">
      Duplicate slot detected (same date and time)
    </div>
    <div v-if="hasPastSlot" class="text-red-500">
      One or more slots are in the past
    </div>
  </div>
</template>
