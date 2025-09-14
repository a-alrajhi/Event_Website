<template>
  <div class="flex flex-col space-y-2">
    <Button
      :label="label"
      :type="type"
      unstyled
      pt:root="w-full bg-primary text-white p-3 rounded-md shadow-lg transition"
    />
    <p v-if="error != ''" class="text-primary text-sm">
      {{ error }}
    </p>
  </div>
</template>

<script setup>
import Button from "primevue/button";
defineEmits(["submit"]);

const props = defineProps({
  label: {
    type: String,
    default: "Submit",
  },
  type: {
    type: String,
    default: "submit",
  },
  error: {
    type: String,
    default: "",
  },
});
</script>
