import axiosClient from "./axiosClient";

export const bookmark = async (eventId) => {
  const res = axiosClient.post("/bookmarks/create", { eventId });
};

export const unbookmark = async (eventId) => {
  const res = axiosClient.delete("/bookmarks/delete", { eventId });
};
