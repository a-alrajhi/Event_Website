<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>Welcome Back</h2>
        <p>Please log in to continue</p>
      </div>
      <form class="login-form" @submit.prevent="handleLogin">
        <input
          type="text"
          placeholder="Username"
          v-model="username"
          class="input-field"
        />
        <input
          type="password"
          placeholder="Password"
          v-model="password"
          class="input-field"
        />
        <button type="submit" class="login-btn" :disabled="auth.loading">
          {{ auth.loading ? "Logging in..." : "Login" }}
        </button>
        <p class="error-text" v-if="auth.error">{{ auth.error }}</p>
        <p class="signup-text">
          Don’t have an account?
          <router-link to="/register">Sign up</router-link>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useAuthStore } from "../stores/authStore";

const auth = useAuthStore();
const username = ref("");
const password = ref("");

const handleLogin = () => {
  auth.loginUser(username.value, password.value);
};
</script>
