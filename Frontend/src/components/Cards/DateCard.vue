<!-- components/DateCard.vue -->
<template>
  <button
    @click="$emit('select', date)"
    :class="[
      'flex flex-col items-center justify-center p-4 rounded-2xl border shadow-md w-20 h-20 text-center',
      isSelected
        ? 'bg-[var(--color-primary)] text-white border-[var(--color-primary)]'
        : 'bg-white dark:bg-gray-800 border-gray-200 dark:border-gray-700 hover:bg-[var(--color-primary)]/10 text-gray-900 dark:text-white'
    ]"
  >
    <span class="text-sm font-medium">{{ day }}</span>
    <span class="text-lg font-bold">{{ dayNumber }}</span>
    <span class="text-xs text-gray-500 dark:text-gray-400">{{ month }}</span>
  </button>
</template>

<script setup>
import { computed } from "vue";
import { ref, watch } from "vue";

const props = defineProps({
  date: { type: Date, required: true },
  modelValue: { type: Date, default: null }
});

const emit = defineEmits(["select"]);

const isSelected = computed(() => {
  if (!props.modelValue) return false;
  return props.date.toDateString() === props.modelValue.toDateString();
});

const day = computed(() => props.date.toLocaleDateString("en-US", { weekday: "short" }));
const dayNumber = computed(() => props.date.getDate());
const month = computed(() => props.date.toLocaleDateString("en-US", { month: "short" }));
</script>
