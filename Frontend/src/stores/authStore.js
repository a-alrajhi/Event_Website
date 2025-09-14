import router from "../Router";
import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";
const ACCESS_TOKEN_KEY = "accessToken";
const REFRESH_TOKEN_KEY = "refreshToken";

export const useAuthStore = defineStore("auth", () => {
  const accessToken = ref(localStorage.getItem(ACCESS_TOKEN_KEY) || null);
  const refreshToken = ref(localStorage.getItem(REFRESH_TOKEN_KEY) || null);
  const isLoggedIn = ref(!!accessToken.value);
  const error = ref(null);
  const loading = ref(false);

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
    isLoggedIn.value = false;
    router.push("/login");
  };

  const authUser = async (form, uri) => {
    loading.value = true;
    error.value = null;

    try {
      const res = await axiosClient.post(uri, form);
      const { accessToken, refreshToken } = res.data;
      setTokens(accessToken, refreshToken || null);
      isLoggedIn.value = true;
    } catch (err) {
      error.value = err.response?.data?.message || "Authentication failed";
      clearTokens();
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const isTokenValid = () => {
    if (!accessToken) return false;
    try {
      const [, payloadBase64] = accessToken.split(".");
      const payload = JSON.parse(atob(payloadBase64));
      const now = Math.floor(Date.now() / 1000);

      return payload.exp && payload.exp > now;
    } catch (error) {
      console.error("Invalid token:", error);
      return false;
    }
  };

  return { isLoggedIn, error, loading, authUser, logout, isTokenValid };
});
