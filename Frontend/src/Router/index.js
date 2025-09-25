import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";

// Pages
import Home from "../Pages/Home.vue";
import AuthView from "../Pages/Auth.vue";
import UserProfile from "../Pages/UserProfile.vue";
import EventBrowse from "../Pages/EventBrowse.vue";
import EventDetailsPage from "../Pages/EventDetailsPage.vue";
import EventPaymentPage from "../Pages/EventPaymentPage.vue";
import EventConfirmationPage from "../Pages/EventConfirmationPage.vue";
import UserManagement from "../Pages/UserManagement.vue";
import EventManagement from "../Pages/EventManagement.vue";
import AdminStats from "../Pages/AdminStats.vue";
import FAQ from "../Pages/FAQ.vue";

// Components
import EventTicketTypes from "../components/Event/EventTicketTypes.vue";

const routes = [
  // Main Routes
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Login",
    component: AuthView,
  },
  {
    path: "/register",
    name: "Register",
    component: AuthView,
  },
  {
    path: "/user-profile",
    name: "UserProfile",
    component: UserProfile,
    meta: { requiresAuth: true },
  },

  // Event Routes
  {
    path: "/events",
    name: "Events",
    component: EventBrowse,
  },
  {
    path: "/event/:id",
    name: "EventDetails",
    component: EventDetailsPage,
  },
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

  // Admin Routes
  {
    path: "/admin/users",
    name: "User Management",
    component: UserManagement,
    meta: { requiresAuth: true },
  },
  // FAQ
  { path: "/faq", name: "FAQ", component: FAQ },

  // // Dashboard
  // {
  //   path: "/Home",
  //   name: "Home",
  //   component: Home,
  //   meta: { requiresAuth: true },
  // }, // ✅ new
  {
    path: "/admin/users",
    name: "User Management",
    component: UserManagement,
    meta: { requiresAuth: true },
  },
  {
    path: "/admin/resources",
    name: "Resource Management",
    component: EventManagement,
    meta: { requiresAuth: true },
  },
  {
    path: "/admin/statistics",
    name: "Admin Statistics",
    component: AdminStats,
    meta: { requiresAuth: true },
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
