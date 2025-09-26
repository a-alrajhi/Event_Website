// src/apis/searchapi.js
import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

// Create axios instance with base configuration
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Add request interceptor for auth token if needed
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("authToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("API Error:", error.response?.data || error.message);
    return Promise.reject(error);
  }
);

/**
 * Search events with keyword and category filters
 * @param {string} keyword - Search keyword (searches in title and description)
 * @param {number[]} categoryIds - Array of category IDs
 * @param {string} sort - Sort order (relevance, title-asc, title-desc, date-asc, date-desc)
 * @param {number} page - Page number (default: 0)
 * @param {number} size - Page size (default: 50)
 * @returns {Promise} API response with search results
 */
export const searchEvents = async (
  keyword = "",
  categoryIds = [],
  sort = "relevance",
  page = 0,
  size = 50
) => {
  try {
    const params = new URLSearchParams();

    if (keyword && keyword.trim()) {
      params.append("keyword", keyword.trim());
    }

    if (categoryIds && categoryIds.length > 0) {
      categoryIds.forEach((id) => params.append("categoryIds", id));
    }

    // Add sorting parameter for backend
    params.append("sort", sort);
    params.append("page", page.toString());
    params.append("size", size.toString());

    const response = await api.get(`/api/events/search?${params.toString()}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to search events");
  }
};

/**
 * Get all events (fallback for when no search is active)
 * @param {string} sort - Sort order
 * @param {number} page - Page number
 * @param {number} size - Page size
 * @returns {Promise} API response with all events
 */
export const getAllEvents = async (sort = "date-desc", page = 0, size = 50) => {
  try {
    const params = new URLSearchParams();
    params.append("sort", sort);
    params.append("page", page.toString());
    params.append("size", size.toString());

    const response = await api.get(`/api/events?${params.toString()}`);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || "Failed to fetch events");
  }
};

/**
 * Get all categories for mapping category names to IDs
 * @returns {Promise} API response with categories
 */
export const getCategories = async () => {
  try {
    const response = await api.get("/api/categories");
    return response.data;
  } catch (error) {
    throw new Error(
      error.response?.data?.message || "Failed to fetch categories"
    );
  }
};

export default {
  searchEvents,
  getCategories,
};
