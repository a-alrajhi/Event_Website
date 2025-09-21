<script setup>
import { onMounted } from "vue";
import { useUserStore } from "../stores/userStore";

import DataTable from "primevue/datatable";
import Column from "primevue/column";
import { Button } from "primevue";
import AdminDialog from "../components/Admin/AdminDialog.vue";
import { ref } from "vue";
import UserForm from "../components/Admin/UserForm.vue";
import ConfirmOperationDialog from "../components/Admin/ConfirmOperationDialog.vue";

const userStore = useUserStore();

onMounted(() => {
  userStore.fetchUsers();
});

const refreshUsers = () => {
  userStore.fetchUsers();
};

const editUserVisible = ref(false);
const createUserVisible = ref(false);
const deleteUserVisible = ref(false);

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

const userEditPage = [UserForm];
const userCreatePage = [UserForm];
</script>

<template>
  <DataTable
    :value="userStore.users"
    stripedRows
    paginator
    :rows="10"
    :rowsPerPageOptions="[5, 10, 20, 50]"
    tableStyle="min-width: 50rem"
    :loading="userStore.loading"
  >
    <!-- ✅ Header -->
    <template #header>
      <div class="flex flex-wrap items-center justify-between gap-2">
        <span class="text-xl font-bold">Users</span>
        <div class="flex gap-3">
          <Button icon="pi pi-refresh" rounded raised @click="refreshUsers" />
          <Button icon="pi pi-plus" rounded raised @click="startCreate" />
        </div>
      </div>
    </template>

    <Column field="id" header="Id" />
    <Column field="name" header="Name" />
    <Column field="email" header="Email" />
    <Column field="phoneNumber" header="Phone number" />

    <Column
      headerStyle="width: 5rem; text-align: center"
      bodyStyle="text-align: center; overflow: visible"
    >
      <template #body="{ data }">
        <div class="flex gap-3">
          <Button
            type="button"
            icon="pi pi-cog"
            rounded
            @click="() => startEdit(data)"
          />
          <Button
            type="button"
            icon="pi pi-user-minus"
            severity="danger"
            rounded
            @click="() => startDelete(data.id)"
          />
        </div>
      </template>
    </Column>

    <template #footer>
      In total there are {{ userStore.users.length }} users.
    </template>
  </DataTable>
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
  <AdminDialog
    v-model:visible="createUserVisible"
    :pages="userCreatePage"
    v-model:isAllowedNext="userStore.isAllowedNext"
    header="Create User"
    :isLoading="userStore.loading"
    :saveFunction="() => userStore.createUser(userStore.selectedUser)"
  />
  <ConfirmOperationDialog
    v-model:visible="deleteUserVisible"
    :header="'Deleting User #' + userStore.selectedUser?.id"
    :saveFunction="() => userStore.deleteUser(userStore.selectedUser.id)"
  />
</template>
