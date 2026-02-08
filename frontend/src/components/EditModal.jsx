import { useEffect, useState } from "react";

export default function EditModal({
  isOpen,
  onClose,
  rowData,
  resourcePerm,
  onSave,
}) {
  const [formData, setFormData] = useState({});

  useEffect(() => {
    if (rowData) setFormData(rowData);
  }, [rowData]);

  if (!isOpen) return null;

  // editable fields from permissions
  const editableFields = Object.keys(resourcePerm.fields || {}).filter(
    (f) => resourcePerm.fields[f].edit === true
  );

  const handleChange = (field, value) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  const handleSave = () => {
    // Send only editable fields
    const payload = {};
    editableFields.forEach((f) => {
      if (formData[f] !== undefined) {
        payload[f] = formData[f];
      }
    });

    onSave(payload);
  };

  return (
    <div
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        height: "100%",
        width: "100%",
        background: "rgba(0,0,0,0.4)",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <div style={{ background: "white", padding: 20, width: 400 }}>
        <h3>Edit Row</h3>

        {editableFields.length === 0 ? (
          <p>No editable fields available.</p>
        ) : (
          editableFields.map((field) => (
            <div key={field} style={{ marginBottom: 10 }}>
              <label style={{ display: "block" }}>{field}</label>
              <input
                value={formData[field] ?? ""}
                onChange={(e) => handleChange(field, e.target.value)}
              />
            </div>
          ))
        )}

        <div style={{ marginTop: 15 }}>
          <button onClick={handleSave} disabled={editableFields.length === 0}>
            Save
          </button>

          <button style={{ marginLeft: 10 }} onClick={onClose}>
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}
