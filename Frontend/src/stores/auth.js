import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";
import router from "../Router";

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
    if (newRefresh) localStorage.setItem(REFRESH_TOKEN_KEY, newRefresh);

    axiosClient.defaults.headers.common["Authorization"] = `Bearer ${newAccess}`;
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

  const loginUser = async (username, password) => {
    loading.value = true;
    error.value = null;
    try {
      const res = await axiosClient.post("/auth/login", { username, password });
      const { accessToken, refreshToken } = res.data;
      if (!accessToken || !refreshToken) throw new Error("Missing tokens");

      setTokens(accessToken, refreshToken);
      isLoggedIn.value = true;
      router.push("/");
    } catch (err) {
      error.value = err.response?.data?.message || "LoginRegister failed";
      clearTokens();
    } finally {
      loading.value = false;
    }
  };

  const registerUser = async (registerBody) => {
    loading.value = true;
    error.value = null;
    try {
      const res = await axiosClient.post("/auth/register", registerBody);
      const { accessToken } = res.data;
      if (!accessToken) throw new Error("Missing token");

      setTokens(accessToken, null);
      isLoggedIn.value = true;
      router.push("/");
    } catch (err) {
      error.value = err.response?.data?.message || "Registration failed";
      clearTokens();
    } finally {
      loading.value = false;
    }
  };

  const isTokenValid = () => {
    if (!accessToken.value) return false;
    try {
      const [, payloadBase64] = accessToken.value.split(".");
      const payload = JSON.parse(atob(payloadBase64));
      const now = Math.floor(Date.now() / 1000);
      return payload.exp && payload.exp > now;
    } catch {
      return false;
    }
  };

  return { isLoggedIn, error, loading, loginUser, registerUser, logout, isTokenValid };
});
