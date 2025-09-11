<script setup>
import { ref, watch, computed } from "vue";
import * as yup from "yup";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../stores/authStore";

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
  form.value[field] = "";
});

// Dynamic schema
const schema = computed(() => {
  const shape = {};
  if (props.fields.includes("email")) {
    shape.email = yup.string().required("Email is required").email();
  }
  if (props.fields.includes("password")) {
    shape.password = yup
      .string()
      .required("Password is required")
      .min(6, "Password must be at least 6 characters");
  }
  if (props.fields.includes("name")) {
    shape.name = yup
      .string()
      .required("Name is required")
      .min("Name must be at least 2 characters");
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
  const uri = props.mode == "login" ? "/auth/login" : "/auth/register";
  try {
    const { status, message } = await auth.authUser({ ...form.value }, uri);
    if (status === 200) {
      auth.isLoggedIn2 = true;
      router.push("/");
    } else if (status === 401) {
      errors.value.failure = message;
    }
  } catch (error) {
    errors.value.failure = "Operation Failed";
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
  <form @submit.prevent="authUser">
    <div v-if="fields.includes('name')">
      <input v-model="form.name" placeholder="Name" />
      <p class="error">{{ errors.name }}</p>
    </div>

    <div v-if="fields.includes('email')">
      <input v-model="form.email" placeholder="Email" />
      <p class="error">{{ errors.email }}</p>
    </div>

    <div v-if="fields.includes('username')">
      <input v-model="form.username" placeholder="Username" />
      <p class="error">{{ errors.username }}</p>
    </div>

    <div v-if="fields.includes('password')">
      <input type="password" v-model="form.password" placeholder="Password" />
      <p class="error">{{ errors.password }}</p>
    </div>

    <p class="error">{{ errors.failure }}</p>

    <button type="submit">
      {{ mode === "login" ? "Login" : "Register" }}
    </button>
  </form>
</template>
