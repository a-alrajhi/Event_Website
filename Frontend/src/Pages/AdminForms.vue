<script setup>
import { Button, Dialog, InputText } from "primevue";
import { ref } from "vue";
import AdminDialog from "../components/Admin/AdminDialog.vue";
import BasicInfoForm from "../components/Admin/BasicInfoForm.vue";
import { useCreateEventStore } from "../stores/createEventStore";
import LocationForm from "../components/Admin/LocationForm.vue";
import SlotForm from "../components/Admin/slotForm.vue";

const createRefVisible = ref(false);
const createEventStore = useCreateEventStore();
const createEventPages = [BasicInfoForm, LocationForm, SlotForm];
</script>

<template>
  <Button @click="createRefVisible = true">Create Event</Button>
  <AdminDialog
    header="Create Event"
    v-model:isAllowedNext="createEventStore.isAllowedNext"
    v-model:visible="createRefVisible"
    :pages="createEventPages"
    :save-function="createEventStore.createEvent"
    v-model:isLoading="createEventStore.loading"
  />
</template>
