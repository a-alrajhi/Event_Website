import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import AuthView from "../Pages/Auth.vue";
import EventDetailsPage from "../Pages/EventDetailsPage.vue";
import EventTicketTypes from "../components/Event/EventTicketTypes.vue";
import EventSlots from "../components/Event/EventSlots.vue";

import UserProfile from "../Pages/UserProfile.vue";

const routes = [
  { path: "/login", name: "Login", component: AuthView },
  { path: "/register", name: "Register", component: AuthView },
  {
    path: "/user-profile",
    name: "UserProfile",
    component: UserProfile,
    meta: { requiresAuth: true }
  },
  // { path: "/", redirect: "/login" }
  // {path:"/event/:id", name:"EventDetails", component: EventDetailsPage},
  // {path:"/event/ticket-types/:eventId", name:"EventTicketTypes", component: EventTicketTypes},
  {path: "/event/slots/:eventId", name: "EventSlots", component: EventSlots},
  { path: "/", redirect: "/login" },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  if (to.meta.requiresAuth && !authStore.isLoggedIn) {
    next({ path: "/login", query: { redirect: to.fullPath } });
  } else {
    next();
  }
});

export default router;
