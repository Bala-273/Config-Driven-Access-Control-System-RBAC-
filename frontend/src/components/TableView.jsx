import { useContext, useState } from "react";
import { PermissionContext } from "../context/PermissionContext";
import EditModal from "./EditModal";

import {
  updateEmployee,
  deleteEmployee,
  updateProject,
  deleteProject,
  updateOrder,
  deleteOrder,
} from "../api/dataApi";

export default function TableView({ title, resource, data, refreshData }) {
  const { permissions } = useContext(PermissionContext);

  // ✅ Hooks must be on top always
  const [isEditOpen, setIsEditOpen] = useState(false);
  const [selectedRow, setSelectedRow] = useState(null);

  const resourcePerm = permissions?.[resource];

  // If permissions not loaded yet
  if (!resourcePerm) return null;

  const canEditTable = resourcePerm.table?.edit === true;

  // columns from data
  const columns = data.length > 0 ? Object.keys(data[0]) : [];

  // show only allowed view fields
  const visibleColumns = columns.filter((col) => {
    if (col === "id") return true;
    return resourcePerm.fields?.[col]?.view === true;
  });

  const updateMap = {
    employees: updateEmployee,
    projects: updateProject,
    orders: updateOrder,
  };

  const deleteMap = {
    employees: deleteEmployee,
    projects: deleteProject,
    orders: deleteOrder,
  };

  const openEdit = (row) => {
    setSelectedRow(row);
    setIsEditOpen(true);
  };

  const closeEdit = () => {
    setSelectedRow(null);
    setIsEditOpen(false);
  };

  const handleSave = async (payload) => {
    try {
      const updateFn = updateMap[resource];
      await updateFn(selectedRow.id, payload);

      alert("Updated successfully");
      closeEdit();
      refreshData();
    } catch (err) {
      alert(err?.response?.data?.message || "Update failed");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete?")) return;

    try {
      const deleteFn = deleteMap[resource];
      await deleteFn(id);

      alert("Deleted successfully");
      refreshData();
    } catch (err) {
      alert(err?.response?.data?.message || "Delete failed");
    }
  };

  return (
    <div style={{ marginTop: 30 }}>
      <h3>{title}</h3>

      {data.length === 0 ? (
        <p>No records found.</p>
      ) : (
        <table border="1" cellPadding="10">
          <thead>
            <tr>
              {visibleColumns.map((col) => (
                <th key={col}>{col}</th>
              ))}
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {data.map((row) => (
              <tr key={row.id}>
                {visibleColumns.map((col) => (
                  <td key={col}>{row[col]}</td>
                ))}

                <td>
                  <button disabled={!canEditTable} onClick={() => openEdit(row)}>
                    Edit
                  </button>

                  <button
                    style={{ marginLeft: 10 }}
                    disabled={!canEditTable}
                    onClick={() => handleDelete(row.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <EditModal
        isOpen={isEditOpen}
        onClose={closeEdit}
        rowData={selectedRow}
        resourcePerm={resourcePerm}
        onSave={handleSave}
      />
    </div>
  );
}
