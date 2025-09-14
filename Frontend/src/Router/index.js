import { createRouter, createWebHistory } from "vue-router";
// import Dashboard from "../Pages/Dashboard.vue";
import { useAuthStore } from "../stores/authStore";
import Auth from "../Pages/Auth.vue";
import LoginForm from "../Pages/LoginForm.vue";
import AdminForms from "../Pages/AdminForms.vue";

const routes = [
  { path: "/login", name: "Login", component: Auth },
  { path: "/register", name: "Register", component: Auth },
  { path: "/admin-form", name: "Admin forms", component: AdminForms },
  //   { path: "/", name: "Dashboard", component: Dashboard, meta: { requiresAuth: true } },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const requiresAuth = to.meta.requiresAuth;
  const authStore = useAuthStore();
  if (requiresAuth && !authStore.isTokenValid()) {
    next({ path: "/login", query: { redirect: to.fullPath } });
  } else {
    next();
  }
});

export default router;
