import router from "../Router";
import { defineStore } from "pinia";
import { ref } from "vue";
import axiosClient from "../apis/axiosClient";
import supabaseClient from "../apis/supabaseClient";
import { getFullEventDetails } from "../apis/eventApi";

export const useCreateEventStore = defineStore("createEvent", () => {
  const isAllowedNext = ref(false);
  const isEditMode = ref(false);
  const eventId = ref(null);
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
  const loading = ref(false);
  const events = ref([]);

  const setEventImage = (file) => {
    eventImage.value = file;
  };

  const fetchEvents = async () => {
    const res = await axiosClient.get("/Event");
    events.value = res.data.content;
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
    isEditMode.value = false;
    eventId.value = null;
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

  const createEvent = async () => {
    if (!isValid) return;
    loading.value = true;
    const request = await buildEventObject();
    const res = axiosClient.post("/Event/create-composite", request);
    console.log(res.data);
    loading.value = false;
    reset();
  };

  const buildEventObject = async () => {
    const finalCategory =
      category.value.id == null
        ? { name: category.value }
        : { id: category.value.id };
    const finalLocation =
      location.value.id == null ? location.value : { id: location.value.id };
    const finalSlots = slots.value.map((slot) => ({
      ...slot,
      ticketTypes: slot.ticketTypes
        .filter((tt) => tt.id != null)
        .map((tt) => ({ ttId: tt.id, capacity: tt.capacity })),
    }));
    const photoUrl = await uploadImage();

    if (!photoUrl) {
      return null;
    }

    const request = {
      event: {
        name: name.value,
        arName: arName.value,
        description: desc.value,
        arDescription: arDesc.value,
        photoUrl,
      },
      location: finalLocation,
      category: finalCategory,
      slots: finalSlots,
    };

    return request;
  };

  const uploadImage = async () => {
    const file = eventImage.value;
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

  const deleteEvent = async (id) => {
    try {
      await axiosClient.delete(`/Event/${id}`);
    } catch (err) {
      console.error("Delete failed:", err);
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

  const loadEventDetails = async (id) => {
    try {
      const res = await axiosClient.get(`/Event/get-composite/${id}`);
      const data = res.data;
      eventId.value = id;

      name.value = data.event.name;
      arName.value = data.event.arName;
      desc.value = data.event.description;
      arDesc.value = data.event.arDescription;
      eventImage.value = data.event.photoUrl;

      location.value = data.location;
      category.value = data.category;
      slots.value = data.slots;
    } catch (err) {
      console.error("Error loading event:", err);
    }
  };

  const updateCompositeEvent = async (id) => {
    if (!isValid()) return;

    loading.value = true;
    try {
      // Decide whether image is a new file or already a URL
      let photoUrl;
      if (eventImage.value && eventImage.value instanceof File) {
        // New file — upload
        photoUrl = await uploadImage();
        if (!photoUrl) {
          throw new Error("Image upload failed");
        }
      } else {
        // Already a URL (i.e. user didn’t change the image)
        photoUrl = eventImage.value;
      }

      // Build slots payload: include slot.id when editing
      const finalSlots = slots.value.map((slot) => ({
        // include id if exists (edit mode); omit or null otherwise
        ...(slot.id != null && { id: slot.id }),
        date: slot.date,
        startTime: slot.startTime,
        endTime: slot.endTime,
        ticketTypes: (slot.ticketTypes || []).map((tt) => ({
          ttId: tt.id,
          capacity: tt.capacity,
        })),
      }));

      const finalCategory =
        category.value && category.value.id != null
          ? { id: category.value.id }
          : { name: category.value };

      const finalLocation =
        location.value && location.value.id != null
          ? { id: location.value.id }
          : location.value;

      const request = {
        event: {
          name: name.value,
          arName: arName.value,
          description: desc.value,
          arDescription: arDesc.value,
          photoUrl,
        },
        location: finalLocation,
        category: finalCategory,
        slots: finalSlots,
      };

      const res = await axiosClient.put(
        `/Event/update-composite/${id}`,
        request
      );
      return res.data;
    } catch (err) {
      console.error("Composite update failed:", err);
      throw err;
    } finally {
      loading.value = false;
    }
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
    loading,
    eventId,
    isEditMode,
    events,
    setEventImage,
    fetchEvents,
    loadEventDetails,
    reset,
    loadCategories,
    loadLocations,
    loadTicketTypes,
    createEvent,
    deleteEvent,
    updateCompositeEvent,
  };
});
