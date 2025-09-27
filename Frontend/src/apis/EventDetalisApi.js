import axiosClient from "./axiosClient";

let cachedEventSlots = {};

export const getEvents = async (page = 0, size = 10) => {
  try {
    const response = await axiosClient.get(`/Event/details?page=${page}&size=${size}`);

    const events = response.data?.content?.map((item) => {
        let eventPrice = 0;
        if (item.prices && Array.isArray(item.prices) && item.prices.length > 0) {
          eventPrice = Math.min(...item.prices.map(p => parseFloat(p) || 0));
        }

        return {
          id: item.id,
          title: item.name,
          description: item.description,
          price: eventPrice,
          priceRange: item.prices || [],
          image: item.photoUrl || "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg",
          category: item.categoryName,
          venue: item.locationName,
          date: item.date,
          time: item.time,
          dates: item.dates || [],
          attendees: item.attendees || 0,
          spotsLeft: item.remaining || 0,
          soldOut: (item.remaining === 0),
          rating: "4.5",
        };
      }) || [];

    return {
      content: events,
      totalElements: response.data?.totalElements || 0,
      totalPages: response.data?.totalPages || 0,
      size: response.data?.size || size,
      number: response.data?.number || page,
      first: response.data?.first || true,
      last: response.data?.last || true,
      numberOfElements: response.data?.numberOfElements || events.length
    };
  } catch (error) {
    return {
      content: [],
      totalElements: 0,
      totalPages: 0,
      size: size,
      number: page,
      first: true,
      last: true,
      numberOfElements: 0
    };
  }
};

export const getCategories = async () => {
  const eventsResponse = await getEvents(0, 1000);
  const events = eventsResponse.content;
  return [...new Set(events.map((event) => event.category))].filter(Boolean);
};

export const getPriceRange = async () => {
  const eventsResponse = await getEvents(0, 1000);
  const events = eventsResponse.content;
  const prices = events.map((e) => e.price).filter((p) => p > 0);
  if (!prices.length) return { min: 0, max: 200 };
  const min = Math.min(...prices);
  const max = Math.max(...prices);
  return { min: Math.max(0, min - 10), max: Math.ceil(max * 1.2) };
};

export const getEventById = async (eventId) => {
  try {
    const response = await axiosClient.get(`/Event/${eventId}/details`);
    return response.data;
  } catch (error) {
    return null;
  }
};

export const getEventSlots = async (eventId) => {
  if (cachedEventSlots[eventId]) {
    return cachedEventSlots[eventId];
  }

  try {
    const response = await axiosClient.get(`/slots/event/${eventId}`);
    cachedEventSlots[eventId] = response.data;
    return response.data;
  } catch (error) {
    return [];
  }
};

export const clearEventsCache = () => {
  cachedEventSlots = {};
};

export const refreshEvents = async () => {
  clearEventsCache();
  return await getEvents();
};

export const getEventWithSlots = async (eventId) => {
  try {
    const [eventDetails, slots] = await Promise.all([
      getEventById(eventId),
      getEventSlots(eventId)
    ]);

    if (!eventDetails) return null;

    return {
      ...eventDetails,
      slots: slots || [],
    };
  } catch (error) {
    return null;
  }
};

export const getEventsByLocation = async (locationId, page = 0, size = 10) => {
  try {
    const response = await axiosClient.get(`/Event/location/${locationId}?page=${page}&size=${size}`);
    return response.data;
  } catch (error) {
    return { content: [], totalElements: 0 };
  }
};
