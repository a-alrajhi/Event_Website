<!--
Event Location Map
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->
<template>
  <div>
    <!-- using iframe to embed google maps -->
    <iframe :src="mapSrc" width="100%" height="450" style="border: 0" allowfullscreen="" loading="lazy"
      referrerpolicy="no-referrer-when-downgrade" title="Event Location Map">
    </iframe>
  </div>

  <!-- location info  -->
  <div class="mt-2 text-sm text-gray-600 dark:text-gray-400">
    📍 <strong>{{ location.name }}</strong> — {{ location.address }}
    <div v-if="location.additionalInfo" class="text-xs mt-1 italic">
      <!-- if there's any extra info show them -->
      {{ location.additionalInfo }}
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  location: { type: Object, required: true },
});

const mapSrc = `https://www.google.com/maps?q=${props.location.latitude},${props.location.longitude}&output=embed`;
</script>
