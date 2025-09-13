<<<<<<< HEAD
import { createApp } from 'vue';
import App from './App.vue';
import { createRouter, createWebHistory } from 'vue-router';
import Login from './Pages/Login.vue';

const routes = [
    { path: '/', component: Login },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount('#app');
=======
import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import PrimeVue from "primevue/config";
import Aura from "@primeuix/themes/aura";
import router from "./Router";
import { createPinia } from "pinia";

const app = createApp(App);
app.use(PrimeVue);
app.use(router);
app.use(createPinia());
app.mount("#app");
>>>>>>> afc9417f073f64589e4d5648551e0e98a2778bc8
