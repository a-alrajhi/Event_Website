import router from "../Router";
import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";

export const useCreateEventStore = defineStore("createEvent", () => {
  const isAllowedNext = ref(false);
  const name = ref("");
  const arName = ref("");
  const desc = ref("");
  const arDesc = ref("");
  const category = ref("");
  const categories = ref([{ name: "music", id: 1 }]);
  const location = ref(null);
  const locations = ref([{ name: "Boulevard", id: "1" }]);

  const eventImage = (ref < File) | (null > null);

  const setEventImage = (file) => {
    eventImage.value = file;
  };

  const reset = () => {
    isAllowedNext.value = false;
    name.value = "";
    arName.value = "";
    desc.value = "";
    arDesc.value = "";
    eventImage.value = null;
  };

  return {
    isAllowedNext,
    name,
    arName,
    desc,
    arDesc,
    eventImage,
    category,
    categories,
    location,
    locations,
    setEventImage,
    reset,
  };
});
