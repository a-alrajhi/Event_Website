import axiosClient from "./axiosClient";

// const API_BASE_URL = "http://localhost:6060";

let cachedEvents = null;

export const getEvents = async () => {
  if (cachedEvents) return cachedEvents;

  try {
    const response = await axiosClient.get(`/Event/details?page=0&size=10`);
    cachedEvents =
      response.data?.content?.map((item) => ({
        id: item.id,
        title: item.name,
        description: item.description,
        price: item.prices && item.prices.length ? Math.min(...item.prices) : 0,
        image:
          item.photoUrl ||
          "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg",
        category: item.categoryName,
        venue: item.locationName || "Saudi Arabia",
        date: item.date || "2025-10-15",
        dates: item.dates || null,
        time: item.time || "TBD",
        attendees: item.attendees || Math.floor(Math.random() * 500) + 50,
        rating: item.rating || (Math.random() * 2 + 3).toFixed(1),
        spotsLeft: item.remaining || Math.floor(Math.random() * 100) + 10,
        soldOut: item.soldOut || false,
      })) || [];

    return cachedEvents;
  } catch (error) {
    console.error("Error fetching events:", error);
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
