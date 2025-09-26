<script setup>
import { onMounted, ref, computed } from "vue";
import { useUserStore } from "../stores/userStore";
import AdminDialog from "../components/Admin/AdminDialog.vue";
import UserForm from "../components/Admin/UserForm.vue";
import ConfirmOperationDialog from "../components/Admin/ConfirmOperationDialog.vue";
import InputText from "primevue/inputtext";
import IconField from "primevue/iconfield";
import InputIcon from "primevue/inputicon";

const userStore = useUserStore();

const editUserVisible = ref(false);
const createUserVisible = ref(false);
const deleteUserVisible = ref(false);
const currentPage = ref(1);
const itemsPerPage = ref(10);
const searchQuery = ref('');

onMounted(() => {
  userStore.fetchUsers();
});

const refreshUsers = () => {
  userStore.fetchUsers();
};

const startEdit = (data) => {
  userStore.resetSelectedUser();
  userStore.selectedUser = data;
  editUserVisible.value = true;
};

const startCreate = () => {
  userStore.resetSelectedUser();
  createUserVisible.value = true;
};

const startDelete = (id) => {
  userStore.selectedUser = { id };
  deleteUserVisible.value = true;
};

const filteredUsers = computed(() => {
  if (!searchQuery.value) return userStore.users;

  const query = searchQuery.value.toLowerCase();
  return userStore.users.filter(user =>
    user.name?.toLowerCase().includes(query) ||
    user.email?.toLowerCase().includes(query) ||
    user.phoneNumber?.includes(query)
  );
});

const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return filteredUsers.value.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil((filteredUsers.value?.length || 0) / itemsPerPage.value);
});

const userEditPage = [UserForm];
const userCreatePage = [UserForm];
</script>

<template>
  <div class="flex flex-col gap-8">
    <!-- Page Header -->
    <div class="flex items-center gap-3">
      <i class="pi pi-users text-2xl" style="color: var(--color-primary);"></i>
      <h1 class="text-3xl font-bold" style="color: var(--color-text);">
        User Management
      </h1>
    </div>

    <!-- User Stats Cards -->
    <div class="grid grid-cols-1 gap-6">
      <div class="admin-stat-card max-w-xs">
        <div class="flex items-center justify-between mb-3">
          <h3 class="text-sm font-medium uppercase tracking-wider opacity-90" style="color: var(--color-text);">
            Total Users
          </h3>
          <div class="w-10 h-10 rounded-lg flex items-center justify-center transition-all duration-200" style="background-color: rgba(255, 255, 255, 0.2);">
            <i class="pi pi-users text-lg" style="color: var(--color-text);"></i>
          </div>
        </div>
        <div class="text-3xl font-bold" style="color: var(--color-text);">
          {{ userStore.users?.length || 0 }}
        </div>
      </div>
    </div>

    <!-- Users Table -->
    <div class="admin-card">
      <!-- Header -->
      <div class="flex flex-wrap items-center justify-between gap-4 mb-6">
        <div class="flex items-center gap-3">
          <i class="pi pi-users text-xl" style="color: var(--color-primary);"></i>
          <h2 class="text-2xl font-bold" style="color: var(--color-text);">
            Users Directory
          </h2>
        </div>
        <div class="flex items-center gap-3">
          <button
            @click="refreshUsers"
            class="admin-btn-icon"
            :disabled="userStore.loading"
          >
            <i class="pi pi-refresh" :class="{ 'animate-spin': userStore.loading }"></i>
          </button>
          <button
            @click="startCreate"
            class="admin-btn-primary"
          >
            <i class="pi pi-user-plus"></i>
            Add User
          </button>
        </div>
      </div>

      <!-- Search and Controls -->
      <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-6">
        <!-- Search Section -->
        <div class="flex-1 max-w-md">
          <IconField>
            <InputIcon class="pi pi-search" />
            <InputText
              v-model="searchQuery"
              placeholder="Search users..."
              class="w-full"
              :pt="{
                root: {
                  style: `
                    padding: 0.75rem 1rem 0.75rem 2.5rem;
                    border-radius: 0.5rem;
                    border: 2px solid var(--color-gray);
                    background-color: var(--color-card);
                    color: var(--color-text);
                    transition: all 0.2s;
                    width: 100%;
                  `
                }
              }"
            />
          </IconField>
        </div>

        <!-- Items Per Page Control -->
        <div class="flex items-center gap-2 flex-shrink-0">
          <label class="text-sm font-medium" style="color: var(--color-text);">
            Show:
          </label>
          <select
            v-model="itemsPerPage"
            class="px-3 py-2 rounded-lg border text-sm min-w-[80px]"
            style="background-color: var(--color-card); border-color: var(--color-gray); color: var(--color-text);"
          >
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
          <span class="text-sm" style="color: var(--color-gray);">entries</span>
        </div>
      </div>

      <!-- Loading State -->
      <div v-if="userStore.loading" class="flex justify-center items-center py-12">
        <div class="admin-spinner w-8 h-8"></div>
      </div>

      <!-- Table -->
      <div v-else class="admin-table">
        <!-- Table Header -->
        <div class="grid grid-cols-5 gap-4 admin-table-header">
          <div>Name</div>
          <div>Email</div>
          <div>Phone</div>
          <div>Role</div>
          <div class="text-center">Actions</div>
        </div>

        <!-- Table Body -->
        <div v-if="paginatedUsers.length === 0" class="text-center py-12">
          <i class="pi pi-users text-4xl mb-4" style="color: var(--color-gray);"></i>
          <p class="text-lg" style="color: var(--color-gray);">
            {{ searchQuery ? 'No users found matching your search' : 'No users found' }}
          </p>
          <button
            v-if="!searchQuery"
            @click="startCreate"
            class="admin-btn-primary mt-4"
          >
            <i class="pi pi-user-plus"></i>
            Create First User
          </button>
        </div>

        <div
          v-for="(user, index) in paginatedUsers"
          :key="user.id || index"
          class="grid grid-cols-5 gap-4 items-center admin-table-cell admin-table-row"
        >
          <div>
            <div class="font-medium">{{ user.name || 'N/A' }}</div>
            <div class="text-xs" style="color: var(--color-gray);">
              ID: {{ user.id || 'N/A' }} • Joined {{ user.createdAt ? new Date(user.createdAt).toLocaleDateString() : 'N/A' }}
            </div>
          </div>
          <div class="text-sm" style="color: var(--color-gray);">{{ user.email || 'N/A' }}</div>
          <div class="text-sm">{{ user.phoneNumber || 'N/A' }}</div>
          <div class="text-sm">
          <span
            :class="`px-2 py-1 rounded-full text-md font-medium ${user.role == 'USER' ? 'bg-green-500' : 'bg-amber-500'}`"
          >
            {{ user.role }}
          </span>
          </div>
          <div class="flex justify-center gap-2">
            <button
              @click="() => startEdit(user)"
              class="admin-btn-icon"
              title="Edit User"
            >
              <i class="pi pi-pencil"></i>
            </button>
            <button
              @click="() => startDelete(user.id)"
              class="admin-btn-icon-danger"
              title="Delete User"
            >
              <i class="pi pi-user-minus"></i>
            </button>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div v-if="totalPages > 1" class="admin-pagination">
        <button
          @click="currentPage = Math.max(1, currentPage - 1)"
          :disabled="currentPage === 1"
          class="admin-pagination-btn"
          :class="{ 'opacity-50 cursor-not-allowed': currentPage === 1 }"
        >
          <i class="pi pi-chevron-left"></i>
        </button>

        <button
          v-for="page in Math.min(totalPages, 5)"
          :key="page"
          @click="currentPage = page"
          class="admin-pagination-btn"
          :class="{ 'active': currentPage === page }"
        >
          {{ page }}
        </button>

        <button
          @click="currentPage = Math.min(totalPages, currentPage + 1)"
          :disabled="currentPage === totalPages"
          class="admin-pagination-btn"
          :class="{ 'opacity-50 cursor-not-allowed': currentPage === totalPages }"
        >
          <i class="pi pi-chevron-right"></i>
        </button>
      </div>

      <!-- Footer -->
      <div class="mt-6 text-center text-sm" style="color: var(--color-gray);">
        Showing {{ Math.min(itemsPerPage, (filteredUsers?.length || 0) - (currentPage - 1) * itemsPerPage) }}
        of {{ filteredUsers?.length || 0 }} users
        {{ searchQuery ? `(filtered from ${userStore.users?.length || 0} total)` : '' }}
      </div>
    </div>
  </div>

  <!-- Edit Dialog -->
  <AdminDialog
    v-model:visible="editUserVisible"
    :pages="userEditPage"
    v-model:isAllowedNext="userStore.isAllowedNext"
    header="Edit User"
    :isLoading="userStore.loading"
    :saveFunction="
      () =>
        userStore.updateUser(userStore.selectedUser.id, userStore.selectedUser)
    "
  />

  <!-- Create Dialog -->
  <AdminDialog
    v-model:visible="createUserVisible"
    :pages="userCreatePage"
    v-model:isAllowedNext="userStore.isAllowedNext"
    header="Create User"
    :isLoading="userStore.loading"
    :saveFunction="() => userStore.createUser(userStore.selectedUser)"
  />

  <!-- Delete Confirm -->
  <ConfirmOperationDialog
    v-model:visible="deleteUserVisible"
    :header="'Delete User #' + userStore.selectedUser?.id"
    :saveFunction="() => userStore.deleteUser(userStore.selectedUser.id)"
  />
</template>

<style scoped>
@import "../styles/admin.css";
</style>
