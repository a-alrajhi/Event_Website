<!--
Event Main Content Component
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.1 - Fixed light/dark mode classes
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
      <!-- {{ description }}     </p>-->

      <h3 class="text-lg text-gray-700 dark:text-gray-300 leading-relaxed">
        German DJ and producer Boris Brejcha is the mastermind behind High-Tech
        Minimal — a unique blend of techno, progressive, and electro. Known for
        his signature Joker mask and boundary-pushing sound, he turns every set
        into an electrifying journey. Founder of Fckng Serious and a global
        festival favorite, Boris is here to take you deep into his world. Get
        ready to lose yourself in the world of High-Tech Minimal.
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
});
</script>
