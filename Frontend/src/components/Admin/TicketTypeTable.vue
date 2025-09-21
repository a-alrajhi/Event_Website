<script setup>
import { ref, onMounted } from "vue";
import { useCreateEventStore } from "../../stores/createEventStore";

import DataTable from "primevue/datatable";
import Column from "primevue/column";
import { Button } from "primevue";
import AdminDialog from "./AdminDialog.vue";
import ConfirmOperationDialog from "./ConfirmOperationDialog.vue";
import TicketTypeForm from "./TicketTypeForm.vue";
import { useCreateTTStore } from "../../stores/createTTStore";

const ticketTypeStore = useCreateTTStore();

const createTicketTypeVisible = ref(false);
const editTicketTypeVisible = ref(false);
const deleteTicketTypeVisible = ref(false);

const selectedTicketTypeId = ref(null);

onMounted(() => {
  ticketTypeStore.loadTicketTypes();
});

const refreshTicketTypes = () => {
  ticketTypeStore.loadTicketTypes();
};

const startCreate = () => {
  ticketTypeStore.reset();
  createTicketTypeVisible.value = true;
};

const startEdit = (tt) => {
  selectedTicketTypeId.value = tt.id;
  ticketTypeStore.reset();
  ticketTypeStore.loadTicketTypeData(tt.id);
  ticketTypeStore.isEditMode = true;
  editTicketTypeVisible.value = true;
};

const startDelete = (id) => {
  selectedTicketTypeId.value = id;
  deleteTicketTypeVisible.value = true;
};

const ticketTypePages = [TicketTypeForm];
</script>

<template>
  <DataTable
    :value="ticketTypeStore.ticketTypes"
    stripedRows
    paginator
    :rows="10"
    :rowsPerPageOptions="[5, 10, 20, 50]"
    tableStyle="min-width: 40rem"
    :loading="ticketTypeStore.loading"
  >
    <template #header>
      <div class="flex flex-wrap items-center justify-between gap-2">
        <span class="text-xl font-bold">Ticket Types</span>
        <div class="flex gap-3">
          <Button
            icon="pi pi-refresh"
            rounded
            raised
            @click="refreshTicketTypes"
          />
          <Button icon="pi pi-plus" rounded raised @click="startCreate" />
        </div>
      </div>
    </template>

    <Column field="name" header="Name" />
    <Column field="price" header="Price (SAR)">
      <template #body="{ data }"> {{ data.price.toFixed(2) }} SAR </template>
    </Column>
    <Column
      headerStyle="width:5rem; text-align:center"
      bodyStyle="text-align:center; overflow:visible"
    >
      <template #body="{ data }">
        <div class="flex gap-3 justify-center">
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
      Total: {{ ticketTypeStore.ticketTypes.length }} ticket types.
    </template>
  </DataTable>

  <!-- Create Dialog -->
  <AdminDialog
    v-model:visible="createTicketTypeVisible"
    :pages="ticketTypePages"
    v-model:isAllowedNext="ticketTypeStore.isAllowedNext"
    header="Create Ticket Type"
    :isLoading="ticketTypeStore.loading"
    :saveFunction="ticketTypeStore.createTT"
  />

  <!-- Edit Dialog -->
  <AdminDialog
    v-model:visible="editTicketTypeVisible"
    :pages="ticketTypePages"
    v-model:isAllowedNext="ticketTypeStore.isAllowedNext"
    header="Edit Ticket Type"
    :isLoading="ticketTypeStore.loading"
    :saveFunction="() => ticketTypeStore.updateTT(selectedTicketTypeId)"
  />

  <!-- Delete Confirm -->
  <ConfirmOperationDialog
    v-model:visible="deleteTicketTypeVisible"
    :header="'Delete Ticket Type #' + selectedTicketTypeId"
    :saveFunction="() => ticketTypeStore.deleteTT(selectedTicketTypeId)"
  />
</template>
