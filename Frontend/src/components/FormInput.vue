<template>
  <label>
    <span>{{ label }}:</span>
    <input :type="type" :value="modelValue" @input="updateValue" :placeholder="placeholder" />
  </label>
</template>

<script setup>
defineProps({
  modelValue: {
    type: String,
    required: true
  },
  type: {
    type: String,
    default: 'text'
  },
  label: {
    type: String,
    required: true
  },
  placeholder: {
    type: String,
    required: true
  }
});

const emit = defineEmits(['update:modelValue']);

// دالة لتحديث القيمة المرسلة إلى الـ parent component
const updateValue = (event) => {
  emit('update:modelValue', event.target.value); // تمرير القيمة المدخلة
};
</script>

<style scoped>
label {
  display: block;
  margin-bottom: 1.5rem;
}

label span {
  display: block;
  color: var(--gray);
  font-size: 1.25rem;
  font-weight: 500;
  margin-bottom: 0.5rem;
}

input {
  display: block;
  width: 100%;
  border-radius: 0.5rem;
  padding: 1rem;
  font-size: 1rem;
  font-weight: 500;
  color: var(--dark);
  background-color: var(--light);
  border: 1px solid var(--gray);
  margin-bottom: 1rem;
}
</style>
