package com.luv2code.pbl4.jobseekerapplication.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@IdClass(RolesId.class)
public class Roles {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @Column(name = "role")
    private String role;




    public Roles() {
    }

    public Roles(User user, String role) {
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
    public String toString() {
        return "Roles{" +
                "user=" + user +
                ", role='" + role + '\'' +
                '}';
    }
}
