<template>
  <div class="flex flex-col">
    <FloatLabel variant="on">
      <InputText
        :id="id"
        :type="type"
        :placeholder="placeholder || `Enter your ${label.toLowerCase()}`"
        class="w-full"
        v-model="internalValue"
        :autocomplete="autocomplete"
        :invalid="error != ''"
      />
      <label :for="id">{{ label }}</label>
    </FloatLabel>
    <p v-if="error" class="text-primary text-sm mt-1">
      {{ error }}
    </p>
  </div>
</template>

<script setup>
import { InputText, FloatLabel } from "primevue";
import { computed } from "vue";

const emit = defineEmits(["update:modelValue"]);

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
  modelValue: {
    type: String,
    required: true,
  },
  type: {
    type: String,
    default: "text",
  },
  placeholder: {
    type: String,
    default: "",
  },
  error: {
    type: String,
    default: "",
  },
  autocomplete: {
    type: String,
    default: "off",
  },
});

const internalValue = computed({
  get: () => props.modelValue,
  set: (val) => emit("update:modelValue", val),
});
</script>
