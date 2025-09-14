import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import LoginRegister from "../Pages/LoginRegister.vue";  // نفس المكون لكل من login و register

const routes = [
  { path: "/login", name: "Login", component: LoginRegister },
  { path: "/register", name: "Register", component: LoginRegister },
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
