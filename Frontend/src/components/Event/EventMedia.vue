<!-- Reusable component to display event media such as photos or videos. -->
<template>
  <div class="w-full flex justify-center">
    <h3 class="text-xl font-semibold mb-2 text-center">Event Media</h3>
  </div>
  <div class="w-full flex justify-center">
    <img
      :src="photo"
      alt="Event Banner"
      class="h-[500px] w-full object-cover rounded-[40px]"
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
