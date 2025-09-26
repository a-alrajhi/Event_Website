import axiosClient from "./axiosClient";

let cachedEvents = null;
let cachedEventSlots = {}; // Cache for event slots

export const getEvents = async () => {
  if (cachedEvents) {
    return cachedEvents;
  }

  try {
    const response = await axiosClient.get(`/Event/details?page=0&size=50`);

    cachedEvents =
      response.data?.content?.map((item) => {
        // Use actual backend field names from EventDtoDetalis
        const eventId = item.id;
        const eventName = item.name;
        const eventDescription = item.description;

        // Extract price from backend prices array (BigDecimal values)
        let eventPrice = 0;
        if (item.prices && Array.isArray(item.prices) && item.prices.length > 0) {
          // Get the minimum price from the prices array
          eventPrice = Math.min(...item.prices.map(p => parseFloat(p) || 0));
        }

        return {
          id: eventId,
          title: eventName,
          description: eventDescription,
          price: eventPrice,
          priceRange: item.prices || [], // Real backend data
          image: item.photoUrl || "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg",
          category: item.categoryName,
          venue: item.locationName,
          // ONLY real backend data - removed all fake fields:
          // - attendees (not tracked)
          // - rating (no rating system)
          // - spotsLeft (not directly available)
          // - soldOut (not directly available)
          // - date/time (comes from slots, not events)
        };
      }) || [];

    return cachedEvents;
  } catch (error) {
    console.error("Error fetching events:", error);
    console.error("Error details:", error.response?.data || error.message);
    return [];
  }
};

export const getCategories = async () => {
  const events = await getEvents();
  // Extract only real categories (remove null/undefined values)
  return [...new Set(events.map((event) => event.category))].filter(Boolean);
};

export const getPriceRange = async () => {
  const events = await getEvents();
  const prices = events.map((e) => e.price).filter((p) => p > 0);
  if (!prices.length) return { min: 0, max: 200 };
  const min = Math.min(...prices);
  const max = Math.max(...prices);
  return { min: Math.max(0, min - 10), max: Math.ceil(max * 1.2) };
};

// Get individual event details with slots/dates
export const getEventById = async (eventId) => {
  try {
    const response = await axiosClient.get(`/Event/${eventId}/details`);
    return response.data;
  } catch (error) {
    console.error("Error fetching event details:", error);
    return null;
  }
};

// Get slots for a specific event (date/time information) - REAL BACKEND DATA
export const getEventSlots = async (eventId) => {
  if (cachedEventSlots[eventId]) {
    return cachedEventSlots[eventId];
  }

  try {
    // Real backend endpoint for event slots
    const response = await axiosClient.get(`/slots/event/${eventId}`);
    // Backend returns: {id, eventId, date (LocalDate), startTime (LocalTime), endTime (LocalTime)}
    cachedEventSlots[eventId] = response.data;
    return response.data;
  } catch (error) {
    console.error("Error fetching event slots:", error);
    return [];
  }
};

// Clear cache when events are updated (call this after creating/updating events)
export const clearEventsCache = () => {
  cachedEvents = null;
  cachedEventSlots = {};
};

// Refresh events (useful for real-time updates)
export const refreshEvents = async () => {
  clearEventsCache();
  return await getEvents();
};

// Get event with its slots (combines event details + date/time info)
export const getEventWithSlots = async (eventId) => {
  try {
    const [eventDetails, slots] = await Promise.all([
      getEventById(eventId),
      getEventSlots(eventId)
    ]);

    if (!eventDetails) return null;

    return {
      ...eventDetails,
      slots: slots || [], // Add real slot data (date, startTime, endTime)
    };
  } catch (error) {
    console.error("Error fetching event with slots:", error);
    return null;
  }
};
