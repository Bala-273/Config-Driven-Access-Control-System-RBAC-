import axios from 'axios';

const axiosClient = axios.create({
    baseURL: "http://localhost:8080",
});

axiosClient.interceptors.request.use((config) => {
    const username = localStorage.getItem("username");
    const isAdmin = localStorage.getItem("isAdmin");

    if(username) config.headers["X-USER"] = username;
    
    if(isAdmin == "true") config.headers["X-ADMIN"] = "true";

    return config;
});

export default axiosClient;