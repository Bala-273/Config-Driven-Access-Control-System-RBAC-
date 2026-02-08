import {  createContext, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children}) => {
    const[username, setUsername] = useState(localStorage.getItem("username"));
    const [isAdmin, setIsAdmin] = useState(localStorage.getItem("isAdmin") === "true");

    const login = (username, admin) => {
        localStorage.setItem("username", username);
        localStorage.setItem("isAdmin", admin ? "true" : "false");
        setUsername(username);
        setIsAdmin(admin);
    }

    const logout = () => {
        localStorage.removeItem("username");
        localStorage.removeItem("isAdmin");
        setUsername(null);
        setIsAdmin(false);
    }

    return(
        <AuthContext.Provider value={{username, isAdmin, login, logout}}>
            {children}
        </AuthContext.Provider>
    )
}