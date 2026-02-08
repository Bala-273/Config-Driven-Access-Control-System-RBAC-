import axiosClient from "./axiosClient";

export const registerUser = async (payload) => {
  const res = await axiosClient.post("/api/auth/register", payload);
  return res.data;
};
