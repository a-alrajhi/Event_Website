import router from "../Router";
import { defineStore } from "pinia";
import { ref, computed } from "vue";
import axiosClient from "../apis/axiosClient";

const ACCESS_TOKEN_KEY = "accessToken";
const REFRESH_TOKEN_KEY = "refreshToken";

export const useAuthStore = defineStore("auth", () => {
  const accessToken = ref(localStorage.getItem(ACCESS_TOKEN_KEY) || null);
  const refreshToken = ref(localStorage.getItem(REFRESH_TOKEN_KEY) || null);
  const error = ref(null);
  const loading = ref(false);
  const userData = ref(null); // Store user data here

  // Define isTokenValid function first
  const isTokenValid = () => {
    if (!accessToken.value) return false;
    try {
      const [, payloadBase64] = accessToken.value.split(".");
      const payload = JSON.parse(atob(payloadBase64));
      const now = Math.floor(Date.now() / 1000);

      return payload.exp && payload.exp > now;
    } catch (e) {
      console.error("Invalid token:", e);
      return false;
    }
  };

  // Make isLoggedIn a computed property that reactively tracks token changes
  const isLoggedIn = computed(() => {
    return isTokenValid();
  });

  const setTokens = (newAccess, newRefresh) => {
    accessToken.value = newAccess;
    refreshToken.value = newRefresh;

    localStorage.setItem(ACCESS_TOKEN_KEY, newAccess);
    if (newRefresh) {
      localStorage.setItem(REFRESH_TOKEN_KEY, newRefresh);
    }

    axiosClient.defaults.headers.common["Authorization"] =
      `Bearer ${newAccess}`;
  };

  const clearTokens = () => {
    accessToken.value = null;
    refreshToken.value = null;
    localStorage.removeItem(ACCESS_TOKEN_KEY);
    localStorage.removeItem(REFRESH_TOKEN_KEY);
    delete axiosClient.defaults.headers.common["Authorization"];
  };

  const logout = () => {
    clearTokens();
    userData.value = null; // Clear user data on logout
    router.push("/login");
  };

  const authUser = async (form, uri) => {
    loading.value = true;
    error.value = null;

    try {
      const res = await axiosClient.post(uri, form);
      const { accessToken: newAccess, refreshToken: newRefresh } = res.data;
      setTokens(newAccess, newRefresh || null);
      redirectToPast();
    } catch (err) {
      error.value = err.response?.data?.message || "Authentication failed";
      clearTokens();
      throw err;
    } finally {
      loading.value = false;
    }
  };

  // Fetch user data from API
  const getUser = async () => {
    loading.value = true;
    error.value = null;

    try {
      if (!isLoggedIn.value) return null;

      const res = await axiosClient.get("/profile/");
      userData.value = res.data; // Store user data in userData
      return res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "Failed to fetch user";
      throw err;
    } finally {
      loading.value = false;
    }
  };

  // Update user data (with current password verification)
  const updateUser = async (form) => {
    loading.value = true;
    error.value = null;

    try {
      if (!isLoggedIn.value) return "Update failed, User must be logged in";

      // Add current password to form to verify it in the backend
      const res = await axiosClient.put("/profile/", form);
      userData.value = res.data; // Update local user data
      return res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "Update failed";
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const redirectToPast = () => {
    const route = router.currentRoute.value;
    const redirectQuery = route.query.redirect || "/";
    router.push(redirectQuery);
  };

  return {
    isLoggedIn,
    error,
    loading,
    authUser,
    logout,
    getUser,
    updateUser,
    isTokenValid,
    userData,
  };
});
