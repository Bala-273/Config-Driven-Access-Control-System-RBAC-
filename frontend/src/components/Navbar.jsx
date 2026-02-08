import { Link, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { PermissionContext } from "../context/PermissionContext";

export default function Navbar() {
  const navigate = useNavigate();
  const { clearPermissions } = useContext(PermissionContext);

  const username = localStorage.getItem("username");
  const isAdmin = localStorage.getItem("isAdmin") === "true";

  const logout = () => {
    localStorage.clear();
    clearPermissions(); // ✅ important
    navigate("/login");
  };

  return (
    <div
      style={{
        padding: 15,
        borderBottom: "1px solid gray",
        display: "flex",
        gap: 15,
        alignItems: "center",
      }}
    >
      <Link to="/dashboard">Dashboard</Link>

      {isAdmin && <Link to="/admin">Admin</Link>}

      {!username ? (
        <Link to="/login">Login</Link>
      ) : (
        <>
          <span style={{ marginLeft: "auto" }}>
            Logged in as: <b>{username}</b>
          </span>
          <button onClick={logout}>Logout</button>
        </>
      )}
    </div>
  );
}
