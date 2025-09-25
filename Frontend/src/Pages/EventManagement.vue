<script setup>
import { ref } from "vue";
import EventsTable from "../components/Admin/EventsTable.vue";
import TicketTypeTable from "../components/Admin/TicketTypeTable.vue";

const activeTab = ref('events');

const setActiveTab = (tab) => {
  activeTab.value = tab;
};
</script>

<template>
  <div class="flex flex-col gap-8">
    <!-- Page Header -->
    <div class="flex items-center gap-3">
      <i class="pi pi-calendar text-2xl" style="color: var(--color-primary);"></i>
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white">
        Event Management
      </h1>
    </div>

    <!-- Navigation Tabs -->
    <div class="flex gap-4 mb-6">
      <div class="flex rounded-lg p-1 bg-gray-100 dark:bg-gray-800">
        <button
          @click="setActiveTab('events')"
          class="px-6 py-3 rounded-lg font-medium transition-all duration-200 flex items-center gap-2"
          :style="activeTab === 'events'
            ? 'background-color: var(--color-primary); color: white;'
            : 'color: gray; background-color: transparent;'"
        >
          <i class="pi pi-calendar"></i>
          Events
        </button>
        <button
          @click="setActiveTab('tickets')"
          class="px-6 py-3 rounded-lg font-medium transition-all duration-200 flex items-center gap-2"
          :style="activeTab === 'tickets'
            ? 'background-color: var(--color-primary); color: white;'
            : 'color: gray; background-color: transparent;'"
        >
          <i class="pi pi-ticket"></i>
          Ticket Types
        </button>
      </div>
    </div>

    <!-- Tab Content -->
    <div>
      <EventsTable v-if="activeTab === 'events'" />
      <TicketTypeTable v-if="activeTab === 'tickets'" />
    </div>
  </div>
</template>

<style scoped>
@import "../styles/admin.css";

/* .space-y-8 > * + * {
  margin-top: 2rem;
} */
</style>
