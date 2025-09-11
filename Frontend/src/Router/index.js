import { createRouter, createWebHistory } from "vue-router";
import LoginForm from "../Pages/LoginForm.vue";
import RegisterForm from "../Pages/RegisterForm.vue";
// import Dashboard from "../Pages/Dashboard.vue";
import { useAuthStore } from "../stores/auth";

const routes = [
  { path: "/login", name: "Login", component: LoginForm },
  { path: "/register", name: "Register", component: RegisterForm },
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
