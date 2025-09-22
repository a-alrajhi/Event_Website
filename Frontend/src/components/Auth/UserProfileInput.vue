<template>
  <div class="form-group">
    <label :for="id" class="label">{{ label }}</label>
    <input
        v-bind="$attrs"
    :value="modelValue"
    @input="updateValue"
    :type="type || 'text'"
    :placeholder="placeholder"
    :id="id"
    class="input"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  id: String,
  label: String,
  placeholder: String,
  type: {
    type: String,
    default: "text",
  },
  modelValue: String,  // Prop for modelValue to enable v-model functionality
});

const emit = defineEmits(["update:modelValue"]);  // Emit the update:modelValue event

// Method to emit the updated value to the parent component
const updateValue = (event) => {
  emit("update:modelValue", event.target.value);  // Emit the updated value from the input
};
</script>

<style scoped>
.input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
}

.label {
  font-weight: 600;
  margin-bottom: 5px;
}

.form-group {
  margin-bottom: 20px;
}
</style>
