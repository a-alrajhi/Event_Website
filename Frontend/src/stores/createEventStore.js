import router from "../Router";
import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";
import supabaseClient from "../apis/supabaseClient";

export const useCreateEventStore = defineStore("createEvent", () => {
  const isAllowedNext = ref(false);
  const name = ref("");
  const arName = ref("");
  const desc = ref("");
  const arDesc = ref("");
  const category = ref("");
  const slots = ref([]);
  const categories = ref([]);
  const location = ref(null);
  const locations = ref([]);
  const ticketTypes = ref([]);
  const eventImage = ref(null);

  const setEventImage = (file) => {
    eventImage.value = file;
  };

  const reset = () => {
    isAllowedNext.value = false;
    name.value = "";
    arName.value = "";
    desc.value = "";
    arDesc.value = "";
    location.value = null;
    category.value = null;
    slots.value = [];
    eventImage.value = null;
  };

  const loadCategories = async () => {
    const res = await axiosClient.get("/category/categories");
    categories.value = res.data;
  };

  const loadLocations = async () => {
    const res = await axiosClient.get("/locations/All");
    locations.value = res.data;
  };

  const loadTicketTypes = async () => {
    const res = await axiosClient.get("/ticket-type/all");
    ticketTypes.value = res.data;
  };

  const createEvent = () => {
    if (!isValid) return;
    const request = buildEventObject();

    console.log(request);
  };

  const buildEventObject = () => {
    const finalCategory =
      category.value.id == null ? category.value : category.value.id;
    const finalLocation =
      location.value.id == null ? location.value : location.value.id;
    const finalSlots = slots.value.map((slot) => ({
      ...slot,
      ticketTypes: slot.ticketTypes
        .filter((tt) => tt.id != null)
        .map((tt) => tt.id),
    }));
    const request = {
      event: {
        name: name.value,
        arName: arName.value,
        description: desc.value,
        arDescription: arDesc.value,
        eventImage: eventImage.value,
        location: finalLocation,
        category: finalCategory,
      },
      slots: finalSlots,
    };

    return request;
  };

  const uploadImage = async () => {
    const file = createEventStore.eventImage;
    if (!file) return null;

    const fileName = `${Date.now()}_${file.name}`;
    const filePath = `events/${fileName}`; // put images in 'events/' folder

    try {
      const { data, error } = await supabaseClient.storage
        .from("class-images") // bucket name
        .upload(filePath, file, {
          contentType: file.type,
          upsert: true,
        });

      if (error) {
        throw error;
      }

      // Return the public URL
      const { data: publicUrlData } = supabaseClient.storage
        .from("class-images")
        .getPublicUrl(filePath);

      return publicUrlData.publicUrl;
    } catch (err) {
      console.error("Upload failed:", err.message);
      return null;
    }
  };

  const isValid = () => {
    return (
      name.value &&
      name.value.length > 0 &&
      arName.value &&
      arName.value.length > 0 &&
      desc.value &&
      desc.value.length > 0 &&
      arDesc.value &&
      arDesc.value.length > 0 &&
      eventImage.value &&
      category.value &&
      location.value &&
      slots.value.length > 0
    );
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
    slots,
    ticketTypes,
    setEventImage,
    reset,
    loadCategories,
    loadLocations,
    loadTicketTypes,
    createEvent,
  };
});
