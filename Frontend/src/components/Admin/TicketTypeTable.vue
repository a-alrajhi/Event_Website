<script setup>
import { ref, onMounted, computed } from "vue";
import AdminDialog from "./AdminDialog.vue";
import ConfirmOperationDialog from "./ConfirmOperationDialog.vue";
import TicketTypeForm from "./TicketTypeForm.vue";
import { useCreateTTStore } from "../../stores/createTTStore";

const ticketTypeStore = useCreateTTStore();

const createTicketTypeVisible = ref(false);
const editTicketTypeVisible = ref(false);
const deleteTicketTypeVisible = ref(false);
const selectedTicketTypeId = ref(null);
const currentPage = ref(1);
const itemsPerPage = ref(10);

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

const paginatedTicketTypes = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return ticketTypeStore.ticketTypes.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil((ticketTypeStore.ticketTypes?.length || 0) / itemsPerPage.value);
});

const ticketTypePages = [TicketTypeForm];
</script>

<template>
  <div class="admin-card">
    <!-- Header -->
    <div class="flex flex-wrap items-center justify-between gap-4 mb-6">
      <div class="flex items-center gap-3">
        <i class="pi pi-ticket text-xl" style="color: var(--color-primary);"></i>
        <h2 class="text-2xl font-bold" style="color: var(--color-text);">
          Ticket Types Management
        </h2>
      </div>
      <div class="flex gap-3">
        <button
          @click="refreshTicketTypes"
          class="admin-btn-icon"
          :disabled="ticketTypeStore.loading"
        >
          <i class="pi pi-refresh" :class="{ 'animate-spin': ticketTypeStore.loading }"></i>
        </button>
        <button
          @click="startCreate"
          class="admin-btn-primary"
        >
          <i class="pi pi-plus"></i>
          Add Ticket Type
        </button>
      </div>
    </div>

    <!-- Table Controls -->
    <div class="flex flex-wrap items-center justify-between gap-4 mb-4">
      <div class="flex items-center gap-2">
        <label class="text-sm font-medium" style="color: var(--color-text);">
          Show:
        </label>
        <select
          v-model="itemsPerPage"
          class="px-3 py-1 rounded border text-sm"
          style="background-color: var(--color-card); border-color: var(--color-gray); color: var(--color-text);"
        >
          <option value="5">5</option>
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="50">50</option>
        </select>
        <span class="text-sm" style="color: var(--color-gray);">entries</span>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="ticketTypeStore.loading" class="flex justify-center items-center py-12">
      <div class="admin-spinner w-8 h-8"></div>
    </div>

    <!-- Table -->
    <div v-else class="admin-table">
      <!-- Table Header -->
      <div class="grid grid-cols-4 gap-4 admin-table-header">
        <div>Name</div>
        <div>Price (SAR)</div>
        <div>Created</div>
        <div class="text-center">Actions</div>
      </div>

      <!-- Table Body -->
      <div v-if="paginatedTicketTypes.length === 0" class="text-center py-12">
        <i class="pi pi-inbox text-4xl mb-4" style="color: var(--color-gray);"></i>
        <p class="text-lg" style="color: var(--color-gray);">No ticket types found</p>
        <button
          @click="startCreate"
          class="admin-btn-primary mt-4"
        >
          <i class="pi pi-plus"></i>
          Create First Ticket Type
        </button>
      </div>

      <div
        v-for="(ticketType, index) in paginatedTicketTypes"
        :key="ticketType.id || index"
        class="grid grid-cols-4 gap-4 items-center admin-table-cell admin-table-row"
      >
        <div class="font-medium">{{ ticketType.name || 'N/A' }}</div>
        <div class="font-semibold">
          <span class="text-lg" style="color: var(--color-primary);">
            {{ ticketType.price ? ticketType.price.toFixed(2) : '0.00' }} SAR
          </span>
        </div>
        <div class="text-sm" style="color: var(--color-gray);">
          {{ ticketType.createdAt ? new Date(ticketType.createdAt).toLocaleDateString() : 'N/A' }}
        </div>
        <div class="flex justify-center gap-2">
          <button
            @click="() => startEdit(ticketType)"
            class="admin-btn-icon"
            title="Edit Ticket Type"
          >
            <i class="pi pi-pencil"></i>
          </button>
          <button
            @click="() => startDelete(ticketType.id)"
            class="admin-btn-icon-danger"
            title="Delete Ticket Type"
          >
            <i class="pi pi-trash"></i>
          </button>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <div v-if="totalPages > 1" class="admin-pagination">
      <button
        @click="currentPage = Math.max(1, currentPage - 1)"
        :disabled="currentPage === 1"
        class="admin-pagination-btn"
        :class="{ 'opacity-50 cursor-not-allowed': currentPage === 1 }"
      >
        <i class="pi pi-chevron-left"></i>
      </button>

      <button
        v-for="page in Math.min(totalPages, 5)"
        :key="page"
        @click="currentPage = page"
        class="admin-pagination-btn"
        :class="{ 'active': currentPage === page }"
      >
        {{ page }}
      </button>

      <button
        @click="currentPage = Math.min(totalPages, currentPage + 1)"
        :disabled="currentPage === totalPages"
        class="admin-pagination-btn"
        :class="{ 'opacity-50 cursor-not-allowed': currentPage === totalPages }"
      >
        <i class="pi pi-chevron-right"></i>
      </button>
    </div>

    <!-- Footer -->
    <div class="mt-6 text-center text-sm" style="color: var(--color-gray);">
      Showing {{ Math.min(itemsPerPage, (ticketTypeStore.ticketTypes?.length || 0) - (currentPage - 1) * itemsPerPage) }}
      of {{ ticketTypeStore.ticketTypes?.length || 0 }} ticket types
    </div>
  </div>

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

<style scoped>
@import "../../styles/admin.css";
</style>
