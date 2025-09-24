<script setup>
import { watch, ref, onMounted } from "vue";
import { useCreateTTStore } from "../../stores/createTTStore";

const ttName = ref("");
const ttPrice = ref(0);
const createTTStore = useCreateTTStore();

onMounted(() => {
  if (createTTStore.isEditMode) {
    ttName.value = createTTStore.name;
    ttPrice.value = createTTStore.price;
  }
});

watch([ttName, ttPrice], ([name, price]) => {
  createTTStore.price = price;
  createTTStore.name = name;
  createTTStore.isAllowedNext = name.trim().length > 1 && price > 0;
});
</script>

<template>
  <div class="flex flex-col gap-6">
    <!-- Section Title -->
    <div class="flex items-center gap-3">
      <i class="pi pi-ticket text-xl" style="color: var(--color-primary);"></i>
      <h3 class="text-xl font-semibold" style="color: var(--color-text);">
        Ticket Type Information
      </h3>
    </div>

    <!-- Form Fields -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <div class="admin-form-group">
        <label class="admin-form-label">Ticket Type Name</label>
        <input
          id="tt-name"
          v-model="ttName"
          type="text"
          class="admin-input"
          placeholder="Enter ticket type name (e.g., VIP, Standard)"
          autocomplete="off"
        />
        <p class="text-xs mt-1" style="color: var(--color-gray);">
          Choose a clear, descriptive name for this ticket category
        </p>
      </div>

      <div class="admin-form-group">
        <label class="admin-form-label">Price (SAR)</label>
        <input
          v-model.number="ttPrice"
          type="number"
          min="0"
          step="0.01"
          class="admin-input"
          placeholder="0.00"
        />
        <p class="text-xs mt-1" style="color: var(--color-gray);">
          Enter the ticket price in Saudi Riyals
        </p>
      </div>
    </div>

    <!-- Preview Card -->
    <div class="admin-card">
      <div class="flex items-center gap-3 mb-4">
        <i class="pi pi-eye text-lg" style="color: var(--color-primary);"></i>
        <h4 class="text-lg font-medium" style="color: var(--color-text);">
          Preview
        </h4>
      </div>

      <div class="p-4 rounded-lg border" style="background-color: var(--color-bg); border-color: var(--color-gray);">
        <div class="flex items-center justify-between">
          <div>
            <h5 class="text-lg font-semibold" style="color: var(--color-text);">
              {{ ttName || 'Ticket Type Name' }}
            </h5>
            <p class="text-sm" style="color: var(--color-gray);">
              This is how your ticket type will appear to customers
            </p>
          </div>
          <div class="text-right">
            <div class="text-2xl font-bold" style="color: var(--color-primary);">
              {{ ttPrice ? `${ttPrice.toFixed(2)} SAR` : '0.00 SAR' }}
            </div>
            <p class="text-xs" style="color: var(--color-gray);">
              Per ticket
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../../styles/admin.css";
</style>
