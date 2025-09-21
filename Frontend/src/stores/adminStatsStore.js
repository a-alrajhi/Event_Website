// stores/adminStatsStore.ts
import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import axiosClient from "../apis/axiosClient";

export const useAdminStatsStore = defineStore("adminStats", () => {
  const loading = ref(false);
  const stats = ref(null);
  const monthlyStats = ref([]);
  const topEvents = ref([]);

  const fetchStats = async () => {
    loading.value = true;
    try {
      const res = await axiosClient.get("/admin/stats");
      stats.value = res.data;
    } finally {
      loading.value = false;
    }
  };

  const fetchMonthlyStats = async () => {
    const res = await axiosClient.get("/admin/stats/monthly-sales");
    monthlyStats.value = res.data;
  };

  const fetchTopEvents = async (count = 5) => {
    const res = await axiosClient.get(`/admin/stats/monthly-top/${count}`);
    topEvents.value = res.data;
  };

  return {
    loading,
    stats,
    monthlyStats,
    topEvents,
    fetchStats,
    fetchMonthlyStats,
    fetchTopEvents,
  };
});
