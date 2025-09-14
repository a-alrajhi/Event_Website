import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
<<<<<<< HEAD
import LoginRegister from "../Pages/LoginRegister.vue";  // نفس المكون لكل من login و register
import Event from "../Pages/Event.vue";
import Dashboard from "../Pages/Dashboard.vue"

const routes = [
  { path: "/login", name: "Login", component: LoginRegister },
  { path: "/register", name: "Register", component: LoginRegister },
  { path: "/event", name: "Event", component: Event },  // ✅ add Event route
  { path: "/dash", name: "Dashboard", component: Dashboard, meta: { requiresAuth: true } },
=======
import Auth from "../Pages/Auth.vue";
import LoginForm from "../Pages/LoginForm.vue";
import AdminForms from "../Pages/AdminForms.vue";

const routes = [
  { path: "/login", name: "Login", component: Auth },
  { path: "/register", name: "Register", component: Auth },
  { path: "/admin-form", name: "Admin forms", component: AdminForms },
  //   { path: "/", name: "Dashboard", component: Dashboard, meta: { requiresAuth: true } },
>>>>>>> bf2b0f30f3fa109ae46c693940a4c82a92de7b0a
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
