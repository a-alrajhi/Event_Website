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

</script>

<template>
  <div class="auth-page">
    <div class="container">
      <div class="forms">
        <div class="form-content">
          <div class="form-box">
            <!-- Title -->

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
