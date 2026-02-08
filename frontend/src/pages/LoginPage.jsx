import { useContext, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";

export default function LoginPage() {
  const { login } = useContext(AuthContext);
  const [username, setUsername] = useState("");
  const [isAdmin, setIsAdmin] = useState(false);
  const navigate = useNavigate();

  const handleLogin = () => {
    if (!username.trim()) return alert("Enter username");
    login(username, isAdmin);
    navigate("/dashboard");
  };

  return (
    <div style={{ padding: 30 }}>
      <h2>Login</h2>

      <input
        placeholder="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <div style={{ marginTop: 10 }}>
        <label>
          <input
            type="checkbox"
            checked={isAdmin}
            onChange={(e) => setIsAdmin(e.target.checked)}
          />
          Login as Admin
        </label>
      </div>

      <button style={{ marginTop: 15 }} onClick={handleLogin}>
        Login
      </button>

      <p style={{ marginTop: 10 }}>
        New user? <Link to="/register">Register</Link>
      </p>
    </div>
  );
}
