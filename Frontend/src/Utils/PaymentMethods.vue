<!-- PaymentMethods -->
<template>
  <div
    class="border border-gray-200 dark:border-gray-700 rounded-xl overflow-hidden"
    :class="{ 'mt-4': index !== 0 }"
  >
    <!-- Header -->
    <div class="relative">
      <label
        class="flex items-center justify-between w-full p-5 cursor-pointer hover:bg-gray-50 dark:hover:bg-gray-800 transition-colors duration-200"
      >
        <!-- Left side: Radio + Label -->
        <div class="flex items-center gap-3">
          <input
            type="radio"
            :name="name"
            :value="value"
            :checked="modelValue === value"
            @change="$emit('update:modelValue', value)"
            class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
          />
          <span
            class="whitespace-nowrap text-lg font-semibold text-gray-900 dark:text-white"
          >
            {{ label }}
          </span>
        </div>

        <!-- Right side: Logo(s) -->
        <div class="flex items-center gap-2 h-8">
          <slot name="logo" />
        </div>
      </label>
    </div>

    <!-- Accordion Content -->
    <transition name="accordion">
      <div
        v-if="modelValue === value"
        class="border-t border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800/50"
      >
        <div class="p-6">
          <slot />
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
defineProps({
  name: { type: String, required: true }, // radio group name
  value: { type: String, required: true }, // unique value for this option
  label: { type: String, required: true }, // label text
  modelValue: { type: String, required: true }, // selected value (v-model)
  index: { type: Number, default: 0 }, // for spacing (mt-4 except first)
});

defineEmits(["update:modelValue"]);
</script>

<style scoped>
.accordion-enter-active,
.accordion-leave-active {
  transition: all 0.3s ease;
  max-height: 500px;
  overflow: hidden;
}
.accordion-enter-from,
.accordion-leave-to {
  max-height: 0;
  opacity: 0;
}
</style>
