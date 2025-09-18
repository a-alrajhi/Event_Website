import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";

export const useCreateTTStore = defineStore("createTT", () => {
  const isAllowedNext = ref(false);
  const name = ref("");
  const price = ref(0);
  const loading = ref(false);

  const reset = () => {
    isAllowedNext.value = false;
    name.value = "";
    price.value = 0;
  };

  const createTT = async () => {
    if (!isValid) return;
    loading.value = true;
    const request = await buildTTObject();
    const res = axiosClient.post("/ticket-type/create", request);
    console.log(res.data);
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
    createTT,
  };
});
