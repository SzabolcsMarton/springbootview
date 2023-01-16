package com.springbootView.springbootview.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Role {

    @Id
    @SequenceGenerator(name = "seqGenRole", sequenceName = "roleIdSeq", initialValue = 60001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenRole")
    private Long roleId;
    private String role;

    public Role() {
    }

    public Role(Long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
