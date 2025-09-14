<script setup>
import { ref, watch, computed } from "vue";
import * as yup from "yup";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../stores/authStore";
import AuthInput from "./AuthInput.vue";
import AuthButton from "./AuthButton.vue";

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

  if (props.fields.includes("name")) {
    shape.name = yup
        .string()
        .required("Name is required")
        .min(2, "Name must be at least 2 characters");
  }
  if (props.fields.includes("email")) {
    shape.email = yup
        .string()
        .required("Email is required")
        .email("Invalid email format");
  }
  if (props.fields.includes("phoneNumber")) {
    shape.phoneNumber = yup.string().required("Phone number is required");
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
        .oneOf([yup.ref("password")], "Passwords do not match")
        .required("Please confirm your password");
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

    // ✅ التصحيح هنا
    auth.isLoggedIn.value = true;

    router.push("/");
  } catch (error) {
    if (error.response?.status === 401) {
      errors.value.failure = error.response.data?.message || "Unauthorized";
    } else {
      errors.value.failure = "Operation Failed";
    }
  }
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
  <div class="bg-primary/20 rounded-2xl shadow-md flex flex-col gap-2 p-4">
    <form @submit.prevent="authUser" class="space-y-5 w-full max-w-md">
      <AuthInput
          v-for="field in fields"
          :key="field"
          :id="field"
          :label="field"
          :type="field !== 'password' && field !== 'confirmPassword' ? 'text' : 'password'"
          v-model="form[field]"
          :error="errors[field]"
      />
      <AuthButton
          :error="errors.failure"
          :label="mode === 'login' ? 'Login' : 'Register'"
      />
    </form>
  </div>
</template>
