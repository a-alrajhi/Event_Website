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
import LeafletMap from "../components/Map/LeafletMap.vue";

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
    path: "/events/:id",
    name: "EventDetails",
    component: EventDetailsPage,
  },
  {
    path: "/events/ticket-types/:eventId",
    name: "EventTicketTypes",
    component: EventTicketTypes,
  },
  {
    path: "/events/:eventId/confirmation",
    name: "EventConfirmation",
    component: EventConfirmationPage,
  },
  {
    path: "/events/:eventId/payment",
    name: "EventPaymentPage",
    component: EventPaymentPage,
  },

  // Admin Routes
  {
    path: "/admin/users",
    name: "User Management",
    component: UserManagement,
  },
  // FAQ
  { path: "/faq", name: "FAQ", component: FAQ },

  //LocationMap
  { path: "/location", name: "Location", component: LeafletMap},

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
