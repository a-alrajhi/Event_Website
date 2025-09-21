import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";

export const useUserStore = defineStore("user", () => {
  const users = ref([]);
  const selectedUser = ref(null);
  const loading = ref(false);
  const error = ref(null);
  const userTickets = ref([]);
  const isAllowedNext = ref(false);

  // Fetch all users (optionally with pagination)
  const fetchUsers = async (page = null, pageLimit = null) => {
    try {
      loading.value = true;
      let url = "/users/all";
      if (page && pageLimit) {
        url += `?page=${page}&pageLimit=${pageLimit}`;
      }
      const res = await axiosClient.get(url);
      users.value = res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "Failed to fetch users.";
    } finally {
      loading.value = false;
    }
  };

  // Get one user
  const fetchUser = async (id) => {
    try {
      loading.value = true;
      const res = await axiosClient.get(`/users/${id}`);
      selectedUser.value = res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "User not found.";
    } finally {
      loading.value = false;
    }
  };

  // Get user's tickets
  const fetchUserTickets = async (id) => {
    try {
      loading.value = true;
      const res = await axiosClient.get(`/users/${id}/tickets`);
      userTickets.value = res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "Failed to fetch tickets.";
    } finally {
      loading.value = false;
    }
  };

  const createUser = async (userData) => {
    try {
      loading.value = true;
      const res = await axiosClient.post("/users/", userData);
      const newUser = res.data;
      users.value.push(newUser);
      return res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "User creation failed.";
      throw err;
    } finally {
      loading.value = false;
    }
  };

  const updateUser = async (id, userData) => {
    try {
      loading.value = true;
      const body = {};
      for (const key in userData) {
        if (userData[key] != "") {
          body[key] = userData[key];
        }
      }
      const res = await axiosClient.put(`/users/${id}`, body);
      const updatedUser = res.data;
      const index = users.value.findIndex((user) => user.id === updatedUser.id);
      if (index !== -1) {
        users.value[index] = updatedUser;
      } else {
        users.value.push(updatedUser);
      }
      return res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "User update failed.";
      throw err;
    } finally {
      resetSelectedUser();
      loading.value = false;
    }
  };

  // Delete a user
  const deleteUser = async (id) => {
    try {
      loading.value = true;
      const res = await axiosClient.delete(`/users/${id}`);
      users.value = users.value.filter((user) => user.id !== id);
      return res.data;
    } catch (err) {
      error.value = err.response?.data?.message || "User deletion failed.";
      throw err;
    } finally {
      loading.value = false;
      resetSelectedUser();
    }
  };

  const resetSelectedUser = () => {
    selectedUser.value = null;
    userTickets.value = [];
    isAllowedNext.value = false;
    loading.value = false;
  };

  return {
    users,
    selectedUser,
    userTickets,
    loading,
    error,
    isAllowedNext,
    fetchUsers,
    fetchUser,
    fetchUserTickets,
    createUser,
    updateUser,
    deleteUser,
    resetSelectedUser,
  };
});
