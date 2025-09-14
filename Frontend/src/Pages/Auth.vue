<script setup>
import { ref, watchEffect } from "vue";
import { useRoute } from "vue-router";
import AuthForm from "../components/Auth/AuthForm.vue";

const login = ref(true);
const route = useRoute();

watchEffect(() => {
  login.value = route.path === "/login";
});
</script>

<template>
  <div class="w-screen p-2 lg:p-6 flex flex-col justify-center items-center">
    <AuthForm :fields="['email', 'password']" v-if="login" />
    <AuthForm :fields="['name', 'email', 'phone', 'password']" v-if="!login" />
  </div>
</template>
