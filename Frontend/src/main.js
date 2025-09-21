import { createApp } from "vue";
import "./style.css";
import "leaflet/dist/leaflet.css";
import App from "./App.vue";
import PrimeVue from "primevue/config";
import Aura from "@primeuix/themes/aura";
import router from "./Router";
import { createI18n } from "vue-i18n";

import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

import { messages, defaultLocale } from "./locales";

const i18n = createI18n({
  locale: defaultLocale,
  fallbackLocale: "en",
  messages: messages,
});

const app = createApp(App);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

app.use(PrimeVue, {
  theme: {
    preset: Aura,
  },
});
app.use(router);
app.use(pinia);
app.use(i18n);
app.mount("#app");
