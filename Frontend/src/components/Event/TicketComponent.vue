<template>
  <div class="max-w-[105rem] mx-auto mt-12 px-6">
    <div class="flex flex-col md:flex-row gap-6">
      <div class="bg-amber-100 shadow-md rounded-xl overflow-hidden">
        <h3 class="font-bold">Ticket Code: {{ ticket.ticketCode }}</h3>
        <p>Type ID: {{ ticket.slotTicketTypeCapacityDTO.ticketTypeId }}</p>
        <p>User ID: {{ ticketType.name }}</p>

        <!-- QR Code -->
        <VQrcode :value="ticket.ticketCode" :size="150" />
      </div>
    </div>
  </div>
</template>

<script setup>
import VQrcode from "qrcode.vue";
import { usePaymentStore } from "../../stores/paymentStore";
import { computed } from "vue";

const paymentStore = usePaymentStore();

const props = defineProps({
  ticket: {
    type: Object,
    required: true,
  },
});

const ticketType = computed(() => {
  return paymentStore.tickets.find(
    (t) => t.id === props.ticket.slotTicketTypeCapacityDTO.ticketTypeId
  );
});

console.log("Ticket Type: ", ticketType.value);
</script>
