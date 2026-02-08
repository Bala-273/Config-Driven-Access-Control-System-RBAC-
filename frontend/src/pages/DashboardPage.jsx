import { useContext, useEffect, useState } from "react";
import { PermissionContext } from "../context/PermissionContext";
import { getEmployees, getProjects, getOrders } from "../api/dataApi";
import TableView from "../components/TableView";

export default function DashboardPage() {
  const { permissions, loadPermissions } = useContext(PermissionContext);

  const [employees, setEmployees] = useState([]);
  const [projects, setProjects] = useState([]);
  const [orders, setOrders] = useState([]);

  const loadEmployees = async () => {
    const data = await getEmployees();
    setEmployees(data);
  };

  const loadProjects = async () => {
    const data = await getProjects();
    setProjects(data);
  };

  const loadOrders = async () => {
    const data = await getOrders();
    setOrders(data);
  };

  useEffect(() => {
    loadPermissions();
  }, []);

  useEffect(() => {
    if (!permissions) return;

    if (permissions.employees?.table?.view) loadEmployees();
    if (permissions.projects?.table?.view) loadProjects();
    if (permissions.orders?.table?.view) loadOrders();
  }, [permissions]);

  if (!permissions) return <h3>Loading permissions...</h3>;

  return (
    <div style={{ padding: 20 }}>
      <h2>User Dashboard</h2>

      {permissions.employees?.table?.view && (
        <TableView
          title="Employees"
          resource="employees"
          data={employees}
          refreshData={loadEmployees}
        />
      )}

      {permissions.projects?.table?.view && (
        <TableView
          title="Projects"
          resource="projects"
          data={projects}
          refreshData={loadProjects}
        />
      )}

      {permissions.orders?.table?.view && (
        <TableView
          title="Orders"
          resource="orders"
          data={orders}
          refreshData={loadOrders}
        />
      )}
    </div>
  );
}
