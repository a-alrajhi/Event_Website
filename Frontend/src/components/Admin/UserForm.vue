<script setup>
import { FloatLabel } from "primevue";
import InputText from "primevue/inputtext";
import { ref, watch } from "vue";
import { useUserStore } from "../../stores/userStore";
import * as yup from "yup";

const userStore = useUserStore();

const email = ref("");
const password = ref("");
const name = ref("");
const phoneNumber = ref("");
const role = ref("")
const errors = ref({});
const originalValues = ref({});

const roles = [
  { label: "ADMIN", value: "ADMIN" },
  { label: "USER", value: "USER" },
];

const loadUser = (user) => {
  email.value = user.email ?? "";
  password.value = user.password ?? "";
  name.value = user.name ?? "";
  phoneNumber.value = user.phoneNumber ?? "";
  role.value = user.role ?? 'USER'
  originalValues.value = {
    email: email.value,
    password: password.value,
    name: name.value,
    phoneNumber: phoneNumber.value,
    role: role.value
  };
};

watch(
  () => userStore.selectedUser,
  (user) => {
    if (user) loadUser(user);
  },
  { immediate: true }
);

const schema = yup.object().shape({
  email: yup.string().required("Email is required").email("Invalid email"),
  name: yup
    .string()
    .required("Name is required")
    .min(2, "Name must be at least 2 characters"),
  phoneNumber: yup
    .string()
    .required("Phone number is required")
    .min(13, "Must be in +9665XXXXXXXX format"),
  password: yup
    .string()
    .required("Password is required")
    .min(6, "At least 6 characters long"),
});

const validate = async () => {
  errors.value = {};
  const isEditing = !!userStore.selectedUser?.id;

  const currentValues = {
    email: email.value,
    password: password.value,
    name: name.value,
    phoneNumber: phoneNumber.value,
  };

  let valuesToValidate = {};
  let shape = {};

  if (isEditing) {
    // Only include fields that have changed
    for (const key in currentValues) {
      const newValue = currentValues[key];
      const oldValue = originalValues.value[key];

      if (newValue !== oldValue && newValue?.toString().trim() !== "") {
        valuesToValidate[key] = newValue;
        shape[key] = schema.fields[key];
      }
    }

    // If no fields changed, skip validation and allow next
    if (Object.keys(valuesToValidate).length === 0) {
      userStore.isAllowedNext = true;
      return true;
    }
  } else {
    // Creating new user — validate everything
    valuesToValidate = currentValues;
    shape = schema.fields;
  }

  const dynamicSchema = yup.object().shape(shape);

  try {
    await dynamicSchema.validate(valuesToValidate, { abortEarly: false });
    userStore.isAllowedNext = true;
    return true;
  } catch (e) {
    userStore.isAllowedNext = false;
    e.inner.forEach((err) => {
      errors.value[err.path] = err.message;
    });
    return false;
  }
};

// Validate whenever any field changes
watch([email, password, name, phoneNumber, role], () => {
  userStore.selectedUser = {
    ...userStore.selectedUser,
    email: email.value,
    password: password.value,
    name: name.value,
    phoneNumber: phoneNumber.value,
    role: role.value
  };
  validate();
});
</script>

<template>
  <div class="flex flex-col gap-3">
    <span class="text-primary/90 block mb-6">User Info</span>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-x-4 gap-y-8 mb-6">
      <div>
        <FloatLabel>
          <InputText
            id="name"
            v-model="name"
            class="w-full"
            autocomplete="off"
          />
          <label for="name">Full Name</label>
        </FloatLabel>
        <small v-if="errors.name" class="text-red-500">{{ errors.name }}</small>
      </div>
      <div>
        <FloatLabel>
          <InputText
            id="email"
            v-model="email"
            class="w-full"
            autocomplete="off"
          />
          <label for="email">Email</label>
        </FloatLabel>
        <small v-if="errors.email" class="text-red-500">{{
          errors.email
        }}</small>
      </div>
      <div>
        <FloatLabel>
          <InputText
            id="password"
            v-model="password"
            type="password"
            class="w-full"
            autocomplete="off"
          />
          <label for="password">Password</label>
        </FloatLabel>
        <small v-if="errors.password" class="text-red-500">{{
          errors.password
        }}</small>
      </div>
      <div>
        <FloatLabel>
          <InputText
            id="phoneNumber"
            v-model="phoneNumber"
            class="w-full"
            autocomplete="off"
          />
          <label for="phoneNumber">Phone Number</label>
        </FloatLabel>
        <small v-if="errors.phoneNumber" class="text-red-500">{{
          errors.phoneNumber
        }}</small>
      </div>
      <div class="">
        <select
          v-model="role"
          class="w-full p-3 rounded-lg border-2 border-gray-400 text-gray-900 bg-white dark:bg-gray-800 dark:text-white dark:border-gray-600 focus:outline-none focus:border-primary focus:ring-2 focus:ring-purple-200 dark:focus:ring-purple-900 cursor-pointer"
        >
          <option
            :value="'ADMIN'"
          >
            {{ 'ADMIN' }}
          </option>
          <option
            :value="'USER'"
          >
            {{ 'USER' }}
          </option>
        </select>
      </div>
    </div>
  </div>
</template>
