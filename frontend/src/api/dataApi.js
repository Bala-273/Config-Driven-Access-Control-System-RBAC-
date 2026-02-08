import axiosClient from "./axiosClient";

/* ===================== EMPLOYEES ===================== */

export const getEmployees = async () => {
  const res = await axiosClient.get("/api/employees");
  return res.data;
};

export const getEmployeeById = async (id) => {
  const res = await axiosClient.get(`/api/employees/${id}`);
  return res.data;
};

export const createEmployee = async (payload) => {
  const res = await axiosClient.post("/api/employees", payload);
  return res.data;
};

export const updateEmployee = async (id, payload) => {
  const res = await axiosClient.put(`/api/employees/${id}`, payload);
  return res.data;
};

export const deleteEmployee = async (id) => {
  const res = await axiosClient.delete(`/api/employees/${id}`);
  return res.data;
};

/* ===================== PROJECTS ===================== */

export const getProjects = async () => {
  const res = await axiosClient.get("/api/projects");
  return res.data;
};

export const getProjectById = async (id) => {
  const res = await axiosClient.get(`/api/projects/${id}`);
  return res.data;
};

export const createProject = async (payload) => {
  const res = await axiosClient.post("/api/projects", payload);
  return res.data;
};

export const updateProject = async (id, payload) => {
  const res = await axiosClient.put(`/api/projects/${id}`, payload);
  return res.data;
};

export const deleteProject = async (id) => {
  const res = await axiosClient.delete(`/api/projects/${id}`);
  return res.data;
};

/* ===================== ORDERS ===================== */

export const getOrders = async () => {
  const res = await axiosClient.get("/api/orders");
  return res.data;
};

export const getOrderById = async (id) => {
  const res = await axiosClient.get(`/api/orders/${id}`);
  return res.data;
};

export const createOrder = async (payload) => {
  const res = await axiosClient.post("/api/orders", payload);
  return res.data;
};

export const updateOrder = async (id, payload) => {
  const res = await axiosClient.put(`/api/orders/${id}`, payload);
  return res.data;
};

export const deleteOrder = async (id) => {
  const res = await axiosClient.delete(`/api/orders/${id}`);
  return res.data;
};
