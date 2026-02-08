import { useState } from "react";
import { registerUser } from "../api/authApi";
import { Link, useNavigate } from "react-router-dom";

export default function RegisterPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleRegister = async () => {
    if (!username.trim()) return alert("Enter username");
    if (!password.trim()) return alert("Enter password");

    try {
      await registerUser({ username, password });

      alert("Registered successfully! Now login.");
      navigate("/login");
    } catch (err) {
      alert(err?.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div style={{ padding: 30 }}>
      <h2>Register</h2>

      <input
        placeholder="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <br />
      <br />

      <input
        placeholder="password"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <br />
      <br />

      <button onClick={handleRegister}>Register</button>

      <p style={{ marginTop: 10 }}>
        Already have account? <Link to="/login">Login</Link>
      </p>
    </div>
  );
}
