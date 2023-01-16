package com.springbootView.springbootview.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @Id
    @SequenceGenerator(name = "seqGenRole", sequenceName = "roleIdSeq", initialValue = 60001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenRole")
    @Column(name = "role_id")
    private Long roleId;
    private String role;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

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
