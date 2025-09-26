<template>
  <div class="auth-button-wrapper">
    <button
      :type="type"
      :disabled="disabled || loading"
      class="auth-button"
      :class="{ 'is-loading': loading, 'is-disabled': disabled }"
      @click="handleClick"
    >
      <!-- Loading spinner -->
      <div v-if="loading" class="auth-button-spinner">
        <Loader2 class="w-5 h-5 animate-spin" />
      </div>

      <!-- Button content -->
      <div v-if="!loading" class="auth-button-content">
        <component v-if="icon" :is="icon" class="w-5 h-5" />
        <span>{{ label }}</span>
        <ArrowRight v-if="!icon" class="w-4 h-4 transition-transform group-hover:translate-x-1" />
      </div>
    </button>

    <!-- Error message (if needed) -->
    <div v-if="error" class="auth-button-error">
      <AlertCircle class="w-4 h-4" />
      <span>{{ error }}</span>
    </div>
  </div>
</template>

<script setup>
import { Loader2, ArrowRight, AlertCircle } from "lucide-vue-next";

const emit = defineEmits(["click"]);

const props = defineProps({
  label: {
    type: String,
    default: "Submit",
  },
  type: {
    type: String,
    default: "submit",
  },
  loading: {
    type: Boolean,
    default: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  icon: {
    type: [Object, String],
    default: null,
  },
  error: {
    type: String,
    default: "",
  },
});

const handleClick = (event) => {
  if (!props.loading && !props.disabled) {
    emit("click", event);
  }
};
</script>
