import axiosClient from "./axiosClient";

export const createRole = async (roleName) => {
  const res = await axiosClient.post(`/api/admin/roles?roleName=${roleName}`);
  return res.data;
};

export const getRoles = async () => {
  const res = await axiosClient.get("/api/admin/roles");
  return res.data;
};

export const createPermission = async (payload) => {
  const res = await axiosClient.post("/api/admin/permissions", payload);
  return res.data;
};

export const getPermissions = async () => {
  const res = await axiosClient.get("/api/admin/permissions");
  return res.data;
};

export const assignRole = async (payload) => {
  const res = await axiosClient.post("/api/admin/assign-role", payload);
  return res.data;
};
