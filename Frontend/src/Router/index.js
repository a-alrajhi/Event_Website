import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import Auth from "../Pages/Auth.vue";
import AdminForms from "../Pages/AdminForms.vue"; 
import Event from "../Pages/Event.vue"
const routes = [
  { path: "/login", name: "Login", component: Auth },
  { path: "/register", name: "Register", component: Auth },
  { path: "/admin-form", name: "Admin forms", component: AdminForms },
    { path: "/event", name: "Event", component: Event },

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
