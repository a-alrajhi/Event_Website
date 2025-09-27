<!--
Event Main Content Component
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.1 
-->
<template>
  <div class="max-w-4xl mx-auto space-y-10">
    <!-- Event Info -->
    <section class="space-y-4">
      <h1 class="text-4xl font-bold text-gray-800 dark:text-gray-200">
        {{ title }}
      </h1>
      <h3 class="text-xl font-semibold text-gray-800 dark:text-gray-200">
        {{ subtitle }}
      </h3>
      <!-- once backend is ready -->
      <!-- <p class="text-lg text-gray-700 leading-relaxed"> -->

      <h3 class="text-lg text-gray-700 dark:text-gray-300 leading-relaxed">
        {{ longDescription }}
      </h3>
    </section>
  </div>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  subtitle: {
    type: String,
    required: true,
  },
  longDescription: {
    type: String,
    required: true,
  },
});
</script>
