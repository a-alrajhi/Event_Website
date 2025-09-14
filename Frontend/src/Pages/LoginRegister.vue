<script setup>
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import '../styles/login_Register.css';


const name = ref('')
const email = ref('')
const phoneNumber = ref('')
const password = ref('')
const confirmPassword = ref('')
const savePassword = ref(false)

const isLogin = ref(true)

const handleSubmit = () => {
  if (isLogin.value) {
    console.log('Logging in with:', email.value, password.value, savePassword.value)
  } else {
    if (password.value !== confirmPassword.value) {
      console.log('Passwords do not match!')
      return
    }
    console.log('Registering with:', name.value, email.value, phoneNumber.value, password.value)
  }
}

const route = useRoute()
watch(
    () => route.name,
    (newName) => {
      isLogin.value = newName === 'Login'
    },
    { immediate: true }
)
</script>

<template>
  <div class="container">
    <div class="forms">
      <div class="form-content">
        <div class="form-box">
          <div class="title">{{ isLogin ? 'Login' : 'Register' }}</div>

          <form @submit.prevent="handleSubmit">
            <div class="input-boxes">
              <!-- Name -->
              <div v-if="!isLogin" class="input-box">
                <i class="fas fa-user"></i>
                <input v-model="name" type="text" placeholder="Enter your name" required />
              </div>

              <!-- Email -->
              <div class="input-box">
                <i class="fas fa-envelope"></i>
                <input v-model="email" type="email" placeholder="Enter your email" required />
              </div>

              <!-- Phone -->
              <div v-if="!isLogin" class="input-box">
                <i class="fas fa-phone"></i>
                <input v-model="phoneNumber" type="tel" placeholder="Enter your phone" />
              </div>

              <!-- Password -->
              <div class="input-box">
                <i class="fas fa-lock"></i>
                <input v-model="password" type="password" placeholder="Enter your password" required />
              </div>

              <!-- Confirm Password -->
              <div v-if="!isLogin" class="input-box">
                <i class="fas fa-lock"></i>
                <input v-model="confirmPassword" type="password" placeholder="Confirm your password" required />
              </div>

              <!-- Submit -->
              <div class="button input-box">
                <input type="submit" :value="isLogin ? 'Login' : 'Register'" />
              </div>

              <!-- Switch -->
              <div class="text">
                {{ isLogin ? "Don't have an account?" : "Already have an account?" }}
                <router-link :to="isLogin ? '/register' : '/login'">
                  {{ isLoging ? 'Signup now' : 'Login now' }}
                </router-link>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
