import { createApp } from 'vue';
import App from './App.vue';
import { createRouter, createWebHistory } from 'vue-router';
import LoginRegister from './Pages/LoginRegister.vue';
import PrimeVue from "primevue/config";
import Aura from "@primeuix/themes/aura";
import { createPinia } from "pinia";
import "./style.css";
import './styles/login_Register.css';

const routes = [
    { path: '/login', name: 'Login', component: LoginRegister },
    { path: '/register', name: 'Register', component: LoginRegister },
];


const router = createRouter({
    history: createWebHistory(),
    routes,
});

const app = createApp(App);
app.use(PrimeVue);
app.use(router);
app.use(createPinia());
app.mount('#app');
