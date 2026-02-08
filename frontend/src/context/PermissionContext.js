import { createContext, useEffect, useState } from "react";
import { getMyPermissions } from "../api/meApi";

export const PermissionContext = createContext();

export const PermissionProvider = ({ children }) => {
  const [permissions, setPermissions] = useState(null);

  const loadPermissions = async () => {
    const data = await getMyPermissions();
    setPermissions(data.permissions);
  };

  const clearPermissions = () => {
    setPermissions(null);
  };

  useEffect(() => {
    const username = localStorage.getItem("username");

    if (username) {
      loadPermissions();
    } else {
      clearPermissions();
    }
  }, []);

  return (
    <PermissionContext.Provider
      value={{ permissions, loadPermissions, clearPermissions }}
    >
      {children}
    </PermissionContext.Provider>
  );
};
