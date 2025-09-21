import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";

export const useCreateTTStore = defineStore("createTT", () => {
  const isAllowedNext = ref(false);
  const isEditMode = ref(false);
  const name = ref("");
  const price = ref(0);
  const id = ref(null);
  const loading = ref(false);
  const ticketTypes = ref([]);

  const loadTicketTypes = async () => {
    const res = await axiosClient.get("/ticket-type/all");
    ticketTypes.value = res.data;
  };

  const loadTicketTypeData = (ttId) => {
    const tt = ticketTypes.value.find((ticketType) => ticketType.id == ttId);
    price.value = tt.price;
    name.value = tt.name;
    id.value = tt.id;
  };

  const reset = () => {
    isAllowedNext.value = false;
    isEditMode.value = false;
    name.value = "";
    price.value = 0;
    id.value = null;
  };

  const createTT = async () => {
    if (!isValid) return;
    loading.value = true;
    const request = await buildTTObject();
    const res = await axiosClient.post("/ticket-type/create", request);
    ticketTypes.value.push(res.data);
    console.log(res.data);
    loading.value = false;
    reset();
  };

  const updateTT = async (id) => {
    loading.value = true;
    const request = await buildTTObject();
    const res = axiosClient.put(`/ticket-type/update/${id}`, request);
    const updatedTicket = (await res).data;
    const index = ticketTypes.value.findIndex(
      (tt) => tt.id === updatedTicket.id
    );
    if (index !== -1) {
      ticketTypes.value[index] = updatedTicket;
    } else {
      ticketTypes.value.push(updatedTicket);
    }
    console.log(res.data);
    loading.value = false;
    reset();
  };

  const deleteTT = async (id) => {
    if (!isValid) return;
    loading.value = true;
    const res = axiosClient.delete(`/ticket-type/update/${id}`);
    ticketTypes.value = ticketTypes.value.filter((tt) => tt.id !== id);
    loading.value = false;
    reset();
  };

  const buildTTObject = async () => {
    const request = {
      name: name.value,
      price: price.value,
    };

    return request;
  };

  const isValid = () => {
    return name.value && name.value.length > 0 && price.value > 0;
  };

  return {
    name,
    price,
    isAllowedNext,
    loading,
    ticketTypes,
    isEditMode,
    createTT,
    updateTT,
    loadTicketTypes,
    deleteTT,
    loadTicketTypeData,
    reset,
  };
});
