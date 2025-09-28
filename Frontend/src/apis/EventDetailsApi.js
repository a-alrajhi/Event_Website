// src/apis/eventapi.js

import axiosClient from "./axiosClient";

export const getEvents = async () => {
  try {
    const response = await axiosClient.get(`/Event/details?page=0&size=10`);

    // Optional chaining in case backend changes
    return (
      response.data?.content?.map((item) => ({
        id: item.eventId,
        title: item.eventName,
        description: item.eventDescription,
        price: item.ticketPrices?.[0] || 0,
        image:
          item.photoUrl ||
          "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg",
        category: item.categoryName,
      })) || []
    );
  } catch (error) {
    console.error("Error fetching events:", error);
    return [];
  }
};
