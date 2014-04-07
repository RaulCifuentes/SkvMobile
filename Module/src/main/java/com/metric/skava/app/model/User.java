package com.metric.skava.app.model;

import com.metric.skava.app.data.SkavaEntity;

import java.util.ArrayList;
import java.util.List;

public class User extends SkavaEntity {

    private String email;
    private List<Role> roles;

    public User(String id, String name, String email, Role role) {
        super(id, name);
        this.email = email;
        this.roles = new ArrayList<Role>();
        if (role != null){
            roles.add(role);
        }
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void grantRole(Role role) {
        this.roles.add(role);
    }

    public void grantRoles(List<Role> roles) {
        this.roles.addAll(roles);
    }

    public void revokeRole(Role role) {
        this.roles.remove(role);
    }

    public boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
