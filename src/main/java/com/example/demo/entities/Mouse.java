package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mouses")
public class Mouse {
    @Id
    private int id;

    @Column(nullable = false)
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
