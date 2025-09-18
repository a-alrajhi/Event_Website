// router/index.js
import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import AuthView from "../Pages/Auth.vue";
import EventDetailsPage from "../Pages/EventDetailsPage.vue";
import AdminForms from "../Pages/AdminForms.vue";
import Sidebar from "../components/Admin/AdminSidebar.vue";
import UserManagement from "../Pages/UserManagement.vue";
import Dashboard from "../Pages/Dashboard.vue";
import EventManagement from "../Pages/EventManagement.vue";

const routes = [
  { path: "/", name: "Home", component: Dashboard },
  { path: "/login", name: "Login", component: AuthView },
  { path: "/register", name: "Register", component: AuthView },
  { path: "/event/:id", name: "EventDetails", component: EventDetailsPage },
  { path: "/admin-form", name: "Admin Forms", component: AdminForms },
  { path: "/admin/users", name: "User Management", component: UserManagement },
  {
    path: "/admin/resources",
    name: "Resource Management",
    component: EventManagement,
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
