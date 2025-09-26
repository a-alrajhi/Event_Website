<script setup>
import { ref, watch, computed } from "vue";
import * as yup from "yup";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../stores/authStore";
import { useI18n } from "vue-i18n";
import AuthInput from "./AuthInput.vue";
import AuthButton from "./AuthButton.vue";
import { User, LogIn, AlertCircle } from "lucide-vue-next";

const props = defineProps({
  fields: {
    type: Array,
    required: true,
  },
  mode: {
    type: String,
    default: "login", // or 'register'
  },
});

const router = useRouter();
const auth = useAuthStore();

const { t } = useI18n();

const form = ref({});
const errors = ref({});

// Initialize form fields
props.fields.forEach((field) => {
  if (field === "phone number") {
    form.value["phoneNumber"] = "";
  } else {
    form.value[field] = "";
  }
});

// Dynamic schema
const schema = computed(() => {
  const shape = {};

  if (props.fields.includes("email")) {
    shape.email = yup.string().required("Email is required").email("Please enter a valid email");
  }

  if (props.fields.includes("password")) {
    shape.password = yup
        .string()
        .required("Password is required")
        .min(6, "Password must be at least 6 characters");
  }

  if (props.fields.includes("confirmPassword")) {
    shape.confirmPassword = yup
        .string()
        .required("Please confirm your password")
        .oneOf([yup.ref('password')], "Passwords must match");
  }

  if (props.fields.includes("name")) {
    shape.name = yup
        .string()
        .required("Name is required")
        .min(2, "Name must be at least 2 characters");
  }

  if (props.fields.includes("phoneNumber")) {
    shape.phoneNumber = yup
        .string()
        .required("Phone number is required")
        .matches(/^[0-9+\-\s()]+$/, "Please enter a valid phone number");
  }

  return yup.object().shape(shape);
});

const validate = async () => {
  errors.value = {};
  try {
    await schema.value.validate(form.value, { abortEarly: false });
    return true;
  } catch (validationError) {
    validationError.inner.forEach((err) => {
      errors.value[err.path] = err.message;
    });
    return false;
  }
};

const authUser = async () => {
  const valid = await validate();
  if (!valid) return;

  const uri = props.mode === "login" ? "/auth/login" : "/auth/register";
  try {
    await auth.authUser({ ...form.value }, uri);
  } catch (error) {
    if (error.response?.status === 401) {
      errors.value.failure = error.response.data?.message || "Unauthorized";
    } else {
      errors.value.failure = "Operation Failed";
    }
  }
};

// Helper methods for field configuration
const getFieldLabel = (field) => {
  const labels = {
    email: 'Email Address',
    password: 'Password',
    confirmPassword: 'Confirm Password',
    name: 'Full Name',
    phoneNumber: 'Phone Number'
  };
  return labels[field] || field.charAt(0).toUpperCase() + field.slice(1);
};

const getFieldType = (field) => {
  if (field === 'password' || field === 'confirmPassword') return 'password';
  if (field === 'email') return 'email';
  if (field === 'phoneNumber') return 'tel';
  return 'text';
};

const getFieldPlaceholder = (field) => {
  const placeholders = {
    email: 'Enter your email address',
    password: 'Enter your password',
    confirmPassword: 'Confirm your password',
    name: 'Enter your full name',
    phoneNumber: 'Enter your phone number'
  };
  return placeholders[field] || `Enter your ${field}`;
};

const getAutocomplete = (field) => {
  const autocomplete = {
    email: 'email',
    password: 'current-password',
    confirmPassword: 'new-password',
    name: 'name',
    phoneNumber: 'tel'
  };
  return autocomplete[field] || 'off';
};

watch(
    () => ({ ...form.value }),
    () => {
      errors.value.failure = "";
      for (const key in form.value) {
        if (errors.value[key] && form.value[key]) {
          errors.value[key] = "";
        }
      }
    }
);
</script>
<template>
  <div class="auth-form-container">
    <!-- Header with icon and title -->
    <div class="auth-header">
      <div class="auth-icon">
        <User v-if="mode === 'register'" class="w-6 h-6 text-white" />
        <LogIn v-else class="w-6 h-6 text-white" />
      </div>
      <h2 class="auth-title">{{ mode === 'login' ? 'Welcome Back' : 'Create Account' }}</h2>
      <p class="auth-subtitle">{{ mode === 'login' ? 'Sign in to your account' : 'Join our event community' }}</p>
    </div>

    <!-- Form -->
    <form @submit.prevent="authUser" class="auth-form">
      <!-- Error message display -->
      <div v-if="errors.failure" class="auth-error-banner">
        <AlertCircle class="w-4 h-4" />
        <span>{{ errors.failure }}</span>
      </div>

      <!-- Form fields -->
      <div class="auth-fields">
        <AuthInput
          v-for="field in fields"
          :key="field"
          :id="field"
          :label="getFieldLabel(field)"
          :type="getFieldType(field)"
          :placeholder="getFieldPlaceholder(field)"
          v-model="form[field]"
          :error="errors[field]"
          :autocomplete="getAutocomplete(field)"
        />
      </div>

      <!-- Submit button -->
      <AuthButton
        :label="mode === 'login' ? 'Sign In' : 'Create Account'"
        :loading="auth.loading"
        :disabled="auth.loading"
      />

      <!-- Additional options -->
      <div class="auth-extras">
        <div v-if="mode === 'register'" class="terms-notice">
          <p class="terms-text">
            By creating an account, you agree to our
            <a href="#" class="terms-link">Terms of Service</a> and
            <a href="#" class="terms-link">Privacy Policy</a>
          </p>
        </div>
      </div>
    </form>
  </div>
</template>
