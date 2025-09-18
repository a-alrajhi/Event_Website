<script setup>
import { ref, onMounted } from "vue";
import { useCreateEventStore } from "../stores/createEventStore";

import DataTable from "primevue/datatable";
import Column from "primevue/column";
import { Button } from "primevue";
import AdminDialog from "../components/Admin/AdminDialog.vue";
import ConfirmOperationDialog from "../components/Admin/ConfirmOperationDialog.vue";
import BasicInfoForm from "../components/Admin/BasicInfoForm.vue";
import LocationForm from "../components/Admin/LocationForm.vue";
import SlotForm from "../components/Admin/slotForm.vue";

const eventStore = useCreateEventStore();

const editEventVisible = ref(false);
const createEventVisible = ref(false);
const deleteEventVisible = ref(false);

onMounted(() => {
  eventStore.fetchEvents(); // you’ll need this action in your store
});

const refreshEvents = () => {
  eventStore.fetchEvents();
};

const startEdit = async (eventData) => {
  eventStore.reset();
  eventStore.isEditMode = true;
  eventStore.eventId = eventData.id;
  await eventStore.loadEventDetails(eventData.id);
  editEventVisible.value = true;
};

const startCreate = () => {
  eventStore.reset();
  eventStore.isEditMode = false;
  createEventVisible.value = true;
};

const startDelete = (id) => {
  eventStore.eventId = id;
  deleteEventVisible.value = true;
};

const eventEditPages = [BasicInfoForm, LocationForm, SlotForm];
const eventCreatePages = [BasicInfoForm, LocationForm, SlotForm];
</script>

<template>
  <DataTable
    :value="eventStore.events"
    stripedRows
    paginator
    sortField="id"
    :rows="5"
    :rowsPerPageOptions="[5, 10, 20, 50]"
    tableStyle="min-width: 50rem"
    :loading="eventStore.loading"
  >
    <template #header>
      <div class="flex flex-wrap items-center justify-between gap-2">
        <span class="text-xl font-bold">Events</span>
        <div class="flex gap-3">
          <Button icon="pi pi-refresh" rounded raised @click="refreshEvents" />
          <Button icon="pi pi-plus" rounded raised @click="startCreate" />
        </div>
      </div>
    </template>

    <Column field="id" header="Id" />
    <Column field="name" header="Name" />
    <Column field="category.name" header="Category" />
    <Column header="Image">
      <template #body="{ data }">
        <a :href="data.photoUrl" target="_blank" class="cursor-pointer"
          ><img :src="data.photoUrl" class="shadow-lg" width="64"
        /></a>
      </template>
    </Column>
    <Column
      headerStyle="width:5rem; text-align:center"
      bodyStyle="text-align:center; overflow:visible"
    >
      <template #body="{ data }">
        <div class="flex gap-3">
          <Button icon="pi pi-cog" rounded @click="() => startEdit(data)" />
          <Button
            icon="pi pi-trash"
            severity="danger"
            rounded
            @click="() => startDelete(data.id)"
          />
        </div>
      </template>
    </Column>
    <template #footer>
      In total there are {{ eventStore.events.length }} events.
    </template>
  </DataTable>

  <!-- Create Dialog -->
  <AdminDialog
    v-model:visible="createEventVisible"
    :pages="eventCreatePages"
    v-model:isAllowedNext="eventStore.isAllowedNext"
    header="Create Event"
    :isLoading="eventStore.loading"
    :saveFunction="eventStore.createEvent"
  />

  <!-- Edit Dialog -->
  <AdminDialog
    v-model:visible="editEventVisible"
    :pages="eventEditPages"
    v-model:isAllowedNext="eventStore.isAllowedNext"
    header="Edit Event"
    :isLoading="eventStore.loading"
    :saveFunction="() => eventStore.updateCompositeEvent(eventStore.eventId)"
  />

  <!-- Delete Confirm -->
  <ConfirmOperationDialog
    v-model:visible="deleteEventVisible"
    :header="'Deleting Event #' + eventStore.eventId"
    :saveFunction="() => eventStore.deleteEvent(eventStore.eventId)"
  />
</template>
