import axiosClient from "./axiosClient";

// const API_BASE_URL = "http://localhost:6060";

let cachedEvents = null;

export const getEvents = async () => {
  console.log('=== API DEBUG ===');
  console.log('getEvents called, cached:', !!cachedEvents);

  if (cachedEvents) {
    console.log('Returning cached events:', cachedEvents.length);
    return cachedEvents;
  }

  try {
    console.log('Making API call to /Event/details...');
    const response = await axiosClient.get(`/Event/details?page=0&size=10`);
    console.log('API Response status:', response.status);
    console.log('API Response data:', response.data);
    console.log('Response content:', response.data?.content);

    if (response.data?.content) {
      console.log('First raw item from API:', response.data.content[0]);
      console.log('Available keys in first item:', Object.keys(response.data.content[0]));
    }

    cachedEvents =
      response.data?.content?.map((item) => {
        console.log('Processing item with keys:', Object.keys(item));

        // Try different possible field names for ID
        const eventId = item.eventId || item.id || item.event_id || item.Id || item.EVENT_ID;
        const eventName = item.eventName || item.name || item.title || item.event_name || item.Name || item.TITLE;
        const eventDescription = item.eventDescription || item.description || item.desc || item.event_description || item.Description;

        console.log('Found eventId:', eventId, 'eventName:', eventName);

        return {
          id: eventId,
          title: eventName,
          description: eventDescription,
          price: item.ticketPrices?.[0] || item.price || 0,
          image:
            item.photoUrl || item.imageUrl || item.image ||
            "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg",
          category: item.categoryName || item.category,
          venue: item.venue || item.location || "Saudi Arabia",
          date: item.eventDate || item.date || "2025-10-15",
          time: item.eventTime || item.time || "TBD",
          attendees: item.attendees || Math.floor(Math.random() * 500) + 50,
          rating: item.rating || (Math.random() * 2 + 3).toFixed(1),
          spotsLeft: item.spotsLeft || Math.floor(Math.random() * 100) + 10,
          soldOut: item.soldOut || false,
        };
      }) || [];

    console.log('Processed events:', cachedEvents.length);
    console.log('First processed event:', cachedEvents[0]);
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
