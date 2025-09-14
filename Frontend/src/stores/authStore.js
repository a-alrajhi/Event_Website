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
    // 🚫 لا تستعمل router.push هنا
    // رجّع بس إشارة انك طلعت
    return true;
  };

  const authUser = async (authBody, uri) => {
    loading.value = true;
    error.value = null;
    try {
      const response = await axiosClient.post(uri, authBody);
      const { accessToken } = response.data;

      if (!accessToken || response.status !== 200) {
        clearTokens();
        return { status: response.status, message: response.data.message };
      }

      setTokens(accessToken, null);
      isLoggedIn.value = true;
      return { status: response.status, accessToken, message: "Successful!" };
    } catch (err) {
      clearTokens();
      error.value = err.response?.data?.message || "Authentication failed";
      return { status: 500, message: error.value };
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

  return { isLoggedIn, error, loading, authUser, logout, isTokenValid };
});
