import axiosClient from "./axiosClient";

export const bookmark = async (eventId) => {
  const res = axiosClient.post("/bookmarks/create", { eventId });
};

export const unbookmark = async (eventId) => {
  return axiosClient.delete("/bookmarks/delete", {
    data: { eventId },
  });
};
