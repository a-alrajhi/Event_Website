<script setup>
import { ref, onMounted, computed } from "vue";
import { useCreateEventStore } from "../../stores/createEventStore";
import AdminDialog from "./AdminDialog.vue";
import ConfirmOperationDialog from "./ConfirmOperationDialog.vue";
import BasicInfoForm from "./BasicInfoForm.vue";
import LocationForm from "./LocationForm.vue";
import SlotForm from "./slotForm.vue";

const eventStore = useCreateEventStore();

const editEventVisible = ref(false);
const createEventVisible = ref(false);
const deleteEventVisible = ref(false);
const currentPage = ref(1);
const itemsPerPage = ref(5);
const sortField = ref('id');
const sortDirection = ref('asc');

onMounted(() => {
  eventStore.fetchEvents();
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

const sortedEvents = computed(() => {
  if (!eventStore.events || eventStore.events.length === 0) return [];

  return [...eventStore.events].sort((a, b) => {
    let aVal = a.event?.[sortField.value] || a[sortField.value];
    let bVal = b.event?.[sortField.value] || b[sortField.value];

    if (sortDirection.value === 'asc') {
      return aVal > bVal ? 1 : -1;
    } else {
      return aVal < bVal ? 1 : -1;
    }
  });
});

const paginatedEvents = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return sortedEvents.value.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil((eventStore.events?.length || 0) / itemsPerPage.value);
});

const sort = (field) => {
  if (sortField.value === field) {
    sortDirection.value = sortDirection.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortField.value = field;
    sortDirection.value = 'asc';
  }
};

const eventEditPages = [BasicInfoForm, LocationForm, SlotForm];
const eventCreatePages = [BasicInfoForm, LocationForm, SlotForm];
</script>

<template>
  <div class="admin-card">
    <!-- Header -->
    <div class="flex flex-wrap items-center justify-between gap-4 mb-6">
      <div class="flex items-center gap-3">
        <i class="pi pi-calendar text-xl" style="color: var(--color-primary);"></i>
        <h2 class="text-2xl font-bold" style="color: var(--color-text);">
          Events Management
        </h2>
      </div>
      <div class="flex gap-3 ">
        <button
          @click="refreshEvents"
          class="admin-btn-icon mt-1"
          :disabled="eventStore.loading"
        >
          <i class="pi pi-refresh"  :class="{ 'animate-spin': eventStore.loading }"></i>
        </button>
        <button
          @click="startCreate"
          class="admin-btn-primary"
        >
          <i class="pi pi-plus"></i>
          Add Event
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
    <div v-if="eventStore.loading" class="flex justify-center items-center py-12">
      <div class="admin-spinner w-8 h-8"></div>
    </div>

    <!-- Table -->
    <div v-else class="admin-table">
      <!-- Table Header -->
      <div class="grid grid-cols-8 gap-4 admin-table-header">
        <div @click="sort('id')" class="cursor-pointer flex items-center gap-2">
          ID
          <i
            class="pi text-xs"
            :class="sortField === 'id'
              ? (sortDirection === 'asc' ? 'pi-sort-up' : 'pi-sort-down')
              : 'pi-sort'"
          ></i>
        </div>
        <div @click="sort('name')" class="cursor-pointer flex items-center gap-2">
          Name
          <i
            class="pi text-xs"
            :class="sortField === 'name'
              ? (sortDirection === 'asc' ? 'pi-sort-up' : 'pi-sort-down')
              : 'pi-sort'"
          ></i>
        </div>
        <div class="col-span-2">Description</div>
        <div>Category</div>
        <div>Image</div>
        <div>Price</div>
        <div class="text-center">Actions</div>
      </div>

      <!-- Table Body -->
      <div v-if="paginatedEvents.length === 0" class="text-center py-12">
        <i class="pi pi-inbox text-4xl mb-4" style="color: var(--color-gray);"></i>
        <p class="text-lg" style="color: var(--color-gray);">No events found</p>
      </div>

      <div
        v-for="(data, index) in paginatedEvents"
        :key="data.event?.id || index"
        class="grid grid-cols-8 gap-4 items-center admin-table-cell admin-table-row"
      >
        <div class="font-mono text-sm">{{ data.event?.id || 'N/A' }}</div>
        <div class="font-medium truncate">{{ data.event?.name || 'N/A' }}</div>
        <div class="col-span-2 text-sm truncate" style="color: var(--color-gray);">
          {{ data.event?.description || 'N/A' }}
        </div>
        <div class="text-sm">
          <span
            class="px-2 py-1 rounded-full text-xs font-medium"
            style="background-color: var(--color-primary); color: var(--color-text);"
          >
            {{ data.category || 'N/A' }}
          </span>
        </div>
        <div>
          <a
            v-if="data.event?.photoUrl"
            :href="data.event.photoUrl"
            target="_blank"
            class="block w-16 h-16 rounded-lg overflow-hidden shadow-md hover:shadow-lg transition-shadow"
          >
            <img
              :src="data.event.photoUrl"
              :alt="data.event.name"
              class="w-full h-full object-cover"
            />
          </a>
          <div
            v-else
            class="w-16 h-16 rounded-lg flex items-center justify-center"
            style="background-color: var(--color-bg);"
          >
            <i class="pi pi-image text-xl" style="color: var(--color-gray);"></i>
          </div>
        </div>
        <div class="font-medium">
          {{ data.price ? data.price + ' SAR' : 'Free' }}
        </div>
        <div class="flex justify-center gap-2">
          <button
            @click="() => startEdit(data.event)"
            class="admin-btn-icon"
            title="Edit Event"
          >
            <i class="pi pi-pencil"></i>
          </button>
          <button
            @click="() => startDelete(data.id)"
            class="admin-btn-icon-danger"
            title="Delete Event"
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
      Showing {{ Math.min(itemsPerPage, (eventStore.events?.length || 0) - (currentPage - 1) * itemsPerPage) }}
      of {{ eventStore.events?.length || 0 }} events
    </div>
  </div>

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

<style scoped>
@import "../../styles/admin.css";
</style>
