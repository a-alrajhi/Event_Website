import { createRouter, createWebHistory } from "vue-router";
// import Dashboard from "../Pages/Dashboard.vue";
import { useAuthStore } from "../stores/authStore";
import Auth from "../Pages/Auth.vue";
import LoginForm from "../Pages/LoginForm.vue";
import Event from "../Pages/Event.vue"; // <-- Import your Event page

const routes = [
  { path: "/login", name: "Login", component: LoginForm },
  { path: "/register", name: "Register", component: Auth },
  { path: "/event", name: "Event", component: Event }, 
  // { path: "/", name: "Dashboard", component: Dashboard, meta: { requiresAuth: true } },
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
