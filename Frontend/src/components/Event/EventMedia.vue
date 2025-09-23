<!--
Event Media Component
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->

<!-- Reusable component to display event media such as photos or videos. -->
<template>
  <div class="w-full flex justify-center">
    <img
      :src="photo"
      alt="Event Banner"
      class="h-[500px] w-full object-cover rounded-[40px] hover:scale-[1.007] transition-all duration-500"
    />
  </div>
</template>
<script setup>
const props = defineProps({
  photo: {
    type: String,
    required: true,
  },
});
</script>
