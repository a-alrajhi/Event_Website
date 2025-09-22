import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import AuthView from "../Pages/Auth.vue";
import EventDetailsPage from "../Pages/EventDetailsPage.vue";
import EventTicketTypes from "../components/Event/EventTicketTypes.vue";

import EventPaymentPage from "../Pages/EventPaymentPage.vue";
import EventConfirmationPage from "../Pages/EventConfirmationPage.vue";

import UserProfile from "../Pages/UserProfile.vue";
import Home from "../Pages/Home.vue";
import EventBrowse from "../Pages/EventBrowse.vue";
import AdminForms from "../Pages/AdminForms.vue";
import UserManagement from "../Pages/UserManagement.vue";
import EventManagement from "../Pages/EventManagement.vue";
import AdminStats from "../Pages/AdminStats.vue";

const routes = [
  // main pages
  { path: "/", name: "Home", component: Home },
  { path: "/login", name: "Login", component: AuthView },
  { path: "/register", name: "Register", component: AuthView },

  // user
  {
    path: "/user-profile",
    name: "UserProfile",
    component: UserProfile,
    meta: { requiresAuth: true },
  },

  // events
  { path: "/events", name: "Events", component: EventBrowse },
  { path: "/event/:id", name: "EventDetails", component: EventDetailsPage },
  {
    path: "/event/ticket-types/:eventId",
    name: "EventTicketTypes",
    component: EventTicketTypes,
  },
  {
    path: "/event/:eventId/confirmation",
    name: "EventConfirmation",
    component: EventConfirmationPage,
  },
  {
    path: "/event/:eventId/payment",
    name: "EventPaymentPage",
    component: EventPaymentPage,
  },

  // admin
  { path: "/admin-form", name: "Admin Forms", component: AdminForms },
  { path: "/admin/users", name: "User Management", component: UserManagement },
  {
    path: "/admin/resources",
    name: "Resource Management",
    component: EventManagement,
  },
  {
    path: "/admin/statistics",
    name: "Admin Statistics",
    component: AdminStats,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  if (to.meta.requiresAuth && !authStore.isTokenValid()) {
    next({ path: "/login", query: { redirect: to.fullPath } });
  } else {
    next();
  }
});

export default router;
