package com.luv2code.pbl4.jobseekerapplication.entity;
import java.io.Serializable;
import java.util.Objects;

public class RolesId implements Serializable {

    private User user;
    private String role;

    public RolesId() {
    }

    public RolesId(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesId rolesId = (RolesId) o;
        return Objects.equals(user, rolesId.user) && Objects.equals(role, rolesId.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }
}
