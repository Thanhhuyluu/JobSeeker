package com.luv2code.pbl4.jobseekerapplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name="locations")
public class Location {

    @Id
    @Column(name="location_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="location_name")
    private String name;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
