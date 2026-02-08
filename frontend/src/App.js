import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Navbar from "./components/Navbar";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import DashboardPage from "./pages/DashboardPage";
import AdminPage from "./pages/AdminPage";

function PrivateRoute({ children }) {
  const username = localStorage.getItem("username");
  return username ? children : <Navigate to="/login" />;
}

function AdminRoute({ children }) {
  const username = localStorage.getItem("username");
  const isAdmin = localStorage.getItem("isAdmin") === "true";

  if (!username) return <Navigate to="/login" />;
  if (!isAdmin) return <Navigate to="/dashboard" />;

  return children;
}

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        {/* Default route */}
        <Route path="/" element={<Navigate to="/login" />} />

        {/* Public */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        {/* Protected */}
        <Route
          path="/dashboard"
          element={
            <PrivateRoute>
              <DashboardPage />
            </PrivateRoute>
          }
        />

        {/* Admin protected */}
        <Route
          path="/admin"
          element={
            <AdminRoute>
              <AdminPage />
            </AdminRoute>
          }
        />

        {/* Fallback */}
        <Route path="*" element={<h2>404 Page Not Found</h2>} />
      </Routes>
    </BrowserRouter>
  );
}
