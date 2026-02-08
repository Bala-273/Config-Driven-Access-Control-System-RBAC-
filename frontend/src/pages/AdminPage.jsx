import { useEffect, useState } from "react";
import {
  createRole,
  getRoles,
  assignRole,
  createPermission,
  getPermissions,
} from "../api/adminApi";

export default function AdminPage() {
  const [roles, setRoles] = useState([]);
  const [permissions, setPermissions] = useState([]);

  // Create Role
  const [newRoleName, setNewRoleName] = useState("");

  // Assign Role
  const [assignUsername, setAssignUsername] = useState("");
  const [assignRoleId, setAssignRoleId] = useState("");

  // Create Permission
  const [permRoleId, setPermRoleId] = useState("");
  const [resource, setResource] = useState("employees");
  const [action, setAction] = useState("VIEW");
  const [scope, setScope] = useState("TABLE");
  const [fieldName, setFieldName] = useState("");

  const loadAll = async () => {
    const rolesData = await getRoles();
    setRoles(rolesData);

    const permData = await getPermissions();
    setPermissions(permData);
  };

  useEffect(() => {
    loadAll();
  }, []);

  // ---------------- CREATE ROLE ----------------
  const handleCreateRole = async () => {
    if (!newRoleName.trim()) return alert("Enter role name");

    try {
      await createRole(newRoleName);
      setNewRoleName("");
      await loadAll();
      alert("Role created");
    } catch (err) {
      alert(err?.response?.data?.message || "Error creating role");
    }
  };

  // ---------------- ASSIGN ROLE ----------------
  const handleAssignRole = async () => {
    if (!assignUsername.trim()) return alert("Enter username");
    if (!assignRoleId) return alert("Select role");

    try {
      await assignRole({
        username: assignUsername,
        roleId: Number(assignRoleId),
      });

      setAssignUsername("");
      setAssignRoleId("");
      alert("Role assigned successfully");
    } catch (err) {
      alert(err?.response?.data?.message || "Error assigning role");
    }
  };

  // ---------------- CREATE PERMISSION ----------------
  const handleCreatePermission = async () => {
    if (!permRoleId) return alert("Select role");

    if (scope === "FIELD" && !fieldName.trim()) {
      return alert("Field name required for FIELD scope");
    }

    try {
      await createPermission({
        roleId: Number(permRoleId),
        resource,
        action,
        scope,
        fieldName: scope === "FIELD" ? fieldName : null,
      });

      setFieldName("");
      await loadAll();
      alert("Permission created");
    } catch (err) {
      alert(err?.response?.data?.message || "Error creating permission");
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Admin Panel</h2>

      {/* ================= CREATE ROLE ================= */}
      <div style={{ border: "1px solid gray", padding: 15, marginTop: 20 }}>
        <h3>Create Role</h3>

        <input
          placeholder="role name (ex: viewer)"
          value={newRoleName}
          onChange={(e) => setNewRoleName(e.target.value)}
        />

        <button style={{ marginLeft: 10 }} onClick={handleCreateRole}>
          Create
        </button>
      </div>

      {/* ================= VIEW ROLES ================= */}
      <div style={{ border: "1px solid gray", padding: 15, marginTop: 20 }}>
        <h3>Roles</h3>

        <ul>
          {roles.map((r) => (
            <li key={r.id}>
              {r.id} - {r.roleName}
            </li>
          ))}
        </ul>
      </div>

      {/* ================= ASSIGN ROLE ================= */}
      <div style={{ border: "1px solid gray", padding: 15, marginTop: 20 }}>
        <h3>Assign Role to User</h3>

        <input
          placeholder="username"
          value={assignUsername}
          onChange={(e) => setAssignUsername(e.target.value)}
        />

        <select
          value={assignRoleId}
          onChange={(e) => setAssignRoleId(e.target.value)}
          style={{ marginLeft: 10 }}
        >
          <option value="">Select Role</option>
          {roles.map((r) => (
            <option key={r.id} value={r.id}>
              {r.roleName}
            </option>
          ))}
        </select>

        <button style={{ marginLeft: 10 }} onClick={handleAssignRole}>
          Assign
        </button>
      </div>

      {/* ================= CREATE PERMISSION ================= */}
      <div style={{ border: "1px solid gray", padding: 15, marginTop: 20 }}>
        <h3>Create Permission</h3>

        <div style={{ marginTop: 10 }}>
          <label>Role: </label>
          <select
            value={permRoleId}
            onChange={(e) => setPermRoleId(e.target.value)}
          >
            <option value="">Select Role</option>
            {roles.map((r) => (
              <option key={r.id} value={r.id}>
                {r.roleName}
              </option>
            ))}
          </select>
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Resource: </label>
          <select value={resource} onChange={(e) => setResource(e.target.value)}>
            <option value="employees">employees</option>
            <option value="projects">projects</option>
            <option value="orders">orders</option>
          </select>
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Action: </label>
          <select value={action} onChange={(e) => setAction(e.target.value)}>
            <option value="VIEW">VIEW</option>
            <option value="EDIT">EDIT</option>
          </select>
        </div>

        <div style={{ marginTop: 10 }}>
          <label>Scope: </label>
          <select value={scope} onChange={(e) => setScope(e.target.value)}>
            <option value="TABLE">TABLE</option>
            <option value="FIELD">FIELD</option>
          </select>
        </div>

        {scope === "FIELD" && (
          <div style={{ marginTop: 10 }}>
            <label>Field Name: </label>
            <input
              placeholder="ex: email"
              value={fieldName}
              onChange={(e) => setFieldName(e.target.value)}
            />
          </div>
        )}

        <button style={{ marginTop: 15 }} onClick={handleCreatePermission}>
          Create Permission
        </button>
      </div>

      {/* ================= VIEW PERMISSIONS ================= */}
      <div style={{ border: "1px solid gray", padding: 15, marginTop: 20 }}>
        <h3>Permissions</h3>

        <table border="1" cellPadding="8">
          <thead>
            <tr>
              <th>ID</th>
              <th>Role</th>
              <th>Resource</th>
              <th>Action</th>
              <th>Scope</th>
              <th>Field</th>
            </tr>
          </thead>

          <tbody>
            {permissions.map((p) => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>{p.role?.roleName}</td>
                <td>{p.resource}</td>
                <td>{p.action}</td>
                <td>{p.scope}</td>
                <td>{p.fieldName || "-"}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
