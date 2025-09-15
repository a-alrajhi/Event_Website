<script setup>
import { ref, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import AuthForm from "../components/Auth/AuthForm.vue";
import "../styles/auth.css";

const login = ref(true);
const route = useRoute();
const router = useRouter();
const auth = useAuthStore();

watchEffect(() => {
  login.value = route.path === "/login";
});

const handleSubmit = async (formData) => {
  if (login.value) {
    const res = await auth.authUser(
      { email: formData.email, password: formData.password },
      "/auth/login"
    );
    if (res.status === 200) {
      router.push("/");
    }
  } else {
    if (formData.password !== formData.confirmPassword) {
      console.error("Passwords do not match!");
      return;
    }
    const res = await auth.authUser(formData, "/auth/register");
    if (res.status === 200) {
      router.push("/");
    }
  }
};
</script>

<template>
  <div class="auth-page">
    <div class="container">
      <div class="forms">
        <div class="form-content">
          <div class="form-box">
            <!-- Title -->
            <div class="title">{{ login ? "Login" : "Register" }}</div>

            <!-- Form -->
            <AuthForm
              :fields="
                login
                  ? ['email', 'password']
                  : [
                      'name',
                      'email',
                      'phoneNumber',
                      'password',
                      'confirmPassword',
                    ]
              "
              :onSubmit="handleSubmit"
              :mode="login ? 'login' : 'register'"
            />

            <!-- Switch -->
            <div class="text">
              {{
                login ? "Don't have an account?" : "Already have an account?"
              }}
              <router-link :to="login ? '/register' : '/login'">
                {{ login ? "Signup now" : "Login now" }}
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
