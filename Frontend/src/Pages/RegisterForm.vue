<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>Create Account</h2>
        <p>Please register to get started</p>
      </div>
      <form class="login-form" @submit.prevent="handleRegister">
        <input
          type="text"
          placeholder="Username"
          v-model="username"
          class="input-field"
        />
        <input
          type="email"
          placeholder="Email"
          v-model="email"
          class="input-field"
        />
        <input
          type="password"
          placeholder="Password"
          v-model="password"
          class="input-field"
        />
        <button type="submit" class="login-btn" :disabled="auth.loading">
          {{ auth.loading ? "Registering..." : "Register" }}
        </button>
        <p class="error-text" v-if="auth.error">{{ auth.error }}</p>
        <p class="signup-text">
          Already have an account? <router-link to="/login">Login</router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "../stores/auth";

const auth = useAuthStore();
const username = ref("");
const email = ref("");
const password = ref("");

const handleRegister = () => {
  auth.registerUser({
    username: username.value,
    email: email.value,
    password: password.value,
  });
};
</script>
