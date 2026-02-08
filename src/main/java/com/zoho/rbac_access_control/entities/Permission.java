package com.zoho.rbac_access_control.entities;

import com.zoho.rbac_access_control.enums.ActionType;
import com.zoho.rbac_access_control.enums.ScopeType;
import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    private String resource;

    @Enumerated(EnumType.STRING)
    private ActionType action;

    @Enumerated(EnumType.STRING)
    private ScopeType scope;

    @Column(name = "field_name")
    private String fieldName;

    public Permission(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public ScopeType getScope() {
        return scope;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
