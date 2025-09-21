import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export const getEvents = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/Event/details?page=0&size=10`);
    
    // Optional chaining in case backend changes
    return response.data?.content?.map((item) => ({
      id: item.eventId,
      title: item.eventName,
      description: item.eventDescription,
      price: item.ticketPrices?.[0] || 0,
      image: item.photoUrl || "https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg",
      category: item.categoryName,
      venue: item.venue || "Saudi Arabia",
      date: item.eventDate || "2025-10-15",
      time: item.eventTime || "TBD",
      attendees: item.attendees || Math.floor(Math.random() * 500) + 50, 
      rating: item.rating || (Math.random() * 2 + 3).toFixed(1), 
      spotsLeft: item.spotsLeft || Math.floor(Math.random() * 100) + 10,
      soldOut: item.soldOut || false,
    })) || [];
  } catch (error) {
    console.error("Error fetching events:", error);
    return [];
  }
};

// New function to get categories dynamically
export const getCategories = async () => {
  try {
    const events = await getEvents();
    const uniqueCategories = [...new Set(events.map(event => event.category))].filter(Boolean);
    return uniqueCategories;
  } catch (error) {
    console.error("Error fetching categories:", error);
    return [];
  }
};

// New function to get price range dynamically
export const getPriceRange = async () => {
  try {
    const events = await getEvents();
    const prices = events.map(event => event.price).filter(price => price > 0);
    
    if (prices.length === 0) {
      return { min: 0, max: 200 };
    }
    
    const min = Math.min(...prices);
    const max = Math.max(...prices);
    
 
    return { 
      min: Math.max(0, min - 10), 
      max: Math.ceil(max * 1.2) 
    };
  } catch (error) {
    console.error("Error fetching price range:", error);
    return { min: 0, max: 200 };
  }
};