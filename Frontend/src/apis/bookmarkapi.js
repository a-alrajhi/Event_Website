import axiosClient from "./axiosClient";

export const bookmark = async (eventId) => {
  return axiosClient.post("/bookmarks/create", { eventId });
};

export const unbookmark = async (eventId) => {
  return axiosClient.delete("/bookmarks/delete", {
    data: { eventId },
  });
};

export const getUserBookmarks = async () => {
  return axiosClient.get("/bookmarks/user");
};
