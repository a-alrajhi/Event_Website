import axiosClient from "./axiosClient";

// const API_BASE_URL = "http://localhost:6060";

let cachedEvents = null;

export const getEvents = async () => {
  if (cachedEvents) {
    return cachedEvents;
  }

  try {
    const response = await axiosClient.get(`/Event/details?page=0&size=10`);

    cachedEvents =
      response.data?.content?.map((item) => {
        // Try different possible field names for ID
        const eventId = item.eventId || item.id || item.event_id || item.Id || item.EVENT_ID;
        const eventName = item.eventName || item.name || item.title || item.event_name || item.Name || item.TITLE;
        const eventDescription = item.eventDescription || item.description || item.desc || item.event_description || item.Description;

        // Extract price from backend prices array
        let eventPrice = 0;
        if (item.prices && Array.isArray(item.prices) && item.prices.length > 0) {
          // Get the minimum price from the prices array
          eventPrice = Math.min(...item.prices.map(p => parseFloat(p) || 0));
        } else {
          // Fallback for other possible price fields
          eventPrice = item.ticketPrices?.[0] || item.price || 0;
        }

        return {
          id: eventId,
          title: eventName,
          description: eventDescription,
          price: eventPrice,
          priceRange: item.prices || [], // Keep full price range for future use
          image:
            item.photoUrl || item.imageUrl || item.image ||
            "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg",
          category: item.categoryName || item.category,
          venue: item.locationName || item.venue || item.location || "Saudi Arabia",
          date: item.eventDate || item.date || "2025-10-15",
          time: item.eventTime || item.time || "TBD",
          attendees: item.attendees || Math.floor(Math.random() * 500) + 50,
          rating: item.rating || (Math.random() * 2 + 3).toFixed(1),
          spotsLeft: item.spotsLeft || Math.floor(Math.random() * 100) + 10,
          soldOut: item.soldOut || false,
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
