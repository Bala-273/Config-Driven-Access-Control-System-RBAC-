import axiosClient from "./axiosClient";

export const getMyPermissions = async () => {
    const res = await axiosClient.get("/api/me/permissions");
    return res.data;
}