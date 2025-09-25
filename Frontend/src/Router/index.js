import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import AuthView from "../Pages/Auth.vue";
import EventDetailsPage from "../Pages/EventDetailsPage.vue";
import EventTicketTypes from "../components/Event/EventTicketTypes.vue";
import UserProfile from "../Pages/UserProfile.vue";
import Home from "../Pages/Home.vue";
import EventBrowse from "../Pages/EventBrowse.vue";
// import AdminForms from "../Pages/AdminForms.vue";
import UserManagement from "../Pages/UserManagement.vue";
import EventManagement from "../Pages/EventManagement.vue";
import AdminStats from "../Pages/AdminStats.vue";
import FAQ from "../Pages/FAQ.vue";

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/login", name: "Login", component: AuthView },
  { path: "/register", name: "Register", component: AuthView },
  {
    path: "/user-profile",
    name: "UserProfile",
    component: UserProfile,
    meta: { requiresAuth: true },
  },
  // { path: "/", redirect: "/login" }
  // {path:"/event/:id", name:"EventDetails", component: EventDetailsPage},
  // {path:"/event/ticket-types/:eventId", name:"EventTicketTypes", component: EventTicketTypes},
  /*
  {path: "/event/slots/:eventId", name: "EventSlots", component: EventSlots},
*/

  // Events
  { path: "/events", name: "Events", component: EventBrowse },
  { path: "/event/:id", name: "EventDetails", component: EventDetailsPage },
  {
    path: "/event/ticket-types/:eventId",
    name: "EventTicketTypes",
    component: EventTicketTypes,
  },

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
  // FAQ
  { path: "/faq", name: "FAQ", component: FAQ },

  // // Dashboard
  // {
  //   path: "/Home",
  //   name: "Home",
  //   component: Home,
  //   meta: { requiresAuth: true },
  // }, // ✅ new

  // { path: "/admin-form", name: "Admin Forms", component: AdminForms },
  { path: "/admin/users", name: "User Management", component: UserManagement },
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
