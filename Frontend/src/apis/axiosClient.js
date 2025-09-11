import axios from "axios";

// Create a custom axios instance
const axiosClient = axios.create({
  baseURL: "http://localhost:8080", 
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosClient;
