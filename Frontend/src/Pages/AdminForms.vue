<script setup>
import { Button, Dialog, InputText } from "primevue";
import { ref } from "vue";
import AdminDialog from "../components/Admin/AdminDialog.vue";
import BasicInfoForm from "../components/Admin/BasicInfoForm.vue";
import { useCreateEventStore } from "../stores/createEventStore";
import { useCreateTTStore } from "../stores/createTTStore";
import LocationForm from "../components/Admin/LocationForm.vue";
import SlotForm from "../components/Admin/slotForm.vue";
import TicketTypeForm from "../components/Admin/TicketTypeForm.vue";

const createRefVisible = ref(false);
const createTTVisible = ref(false);
const createEventStore = useCreateEventStore();
const createTTStore = useCreateTTStore();
const createEventPages = [BasicInfoForm, LocationForm, SlotForm];
const createTTPages = [TicketTypeForm];
</script>

<template>
  <div class="flex gap-3">
    <Button @click="createRefVisible = true">Create Event</Button>
    <Button @click="createTTVisible = true">Create Ticket Type</Button>
  </div>

  <AdminDialog
    header="Create Event"
    v-model:isAllowedNext="createEventStore.isAllowedNext"
    v-model:visible="createRefVisible"
    :pages="createEventPages"
    :save-function="createEventStore.createEvent"
    v-model:isLoading="createEventStore.loading"
  />
  <AdminDialog
    header="Create Ticket Type"
    v-model:isAllowedNext="createTTStore.isAllowedNext"
    v-model:visible="createTTVisible"
    :pages="createTTPages"
    :save-function="createTTStore.createTT"
    v-model:isLoading="createTTStore.loading"
  />
</template>
