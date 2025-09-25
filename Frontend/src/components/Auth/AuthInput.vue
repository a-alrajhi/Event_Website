<template>
  <div class="auth-input-wrapper">
    <div class="auth-input-container" :class="{ 'has-error': error, 'is-focused': isFocused }">
      <!-- Input icon -->
      <div class="auth-input-icon">
        <Mail v-if="type === 'email'" class="w-5 h-5" />
        <Lock v-else-if="type === 'password'" class="w-5 h-5" />
        <User v-else-if="id === 'name'" class="w-5 h-5" />
        <Phone v-else-if="type === 'tel'" class="w-5 h-5" />
        <User v-else class="w-5 h-5" />
      </div>

      <!-- Input field -->
      <div class="auth-input-field">
        <input
          :id="id"
          :type="actualType"
          :placeholder="placeholder"
          v-model="internalValue"
          :autocomplete="autocomplete"
          class="auth-input"
          @focus="handleFocus"
          @blur="handleBlur"
          :class="{ 'has-value': internalValue }"
        />
        <label :for="id" class="auth-input-label">{{ label }}</label>
      </div>

      <!-- Password visibility toggle -->
      <button
        v-if="type === 'password'"
        type="button"
        class="auth-input-toggle"
        @click="togglePasswordVisibility"
      >
        <Eye v-if="showPassword" class="w-5 h-5" />
        <EyeOff v-else class="w-5 h-5" />
      </button>
    </div>

    <!-- Error message -->
    <div v-if="error" class="auth-input-error">
      <AlertCircle class="w-4 h-4" />
      <span>{{ error }}</span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { Mail, Lock, User, Phone, Eye, EyeOff, AlertCircle } from "lucide-vue-next";

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

// State management
const isFocused = ref(false);
const showPassword = ref(false);
const actualType = ref(props.type);

// Computed properties
const internalValue = computed({
  get: () => props.modelValue,
  set: (val) => emit("update:modelValue", val),
});

// Methods
const handleFocus = () => {
  isFocused.value = true;
};

const handleBlur = () => {
  isFocused.value = false;
};

const togglePasswordVisibility = () => {
  showPassword.value = !showPassword.value;
  actualType.value = showPassword.value ? 'text' : 'password';
};

// Update type when showPassword changes
computed(() => {
  if (props.type === 'password') {
    return showPassword.value ? 'text' : 'password';
  }
  return props.type;
});
</script>
