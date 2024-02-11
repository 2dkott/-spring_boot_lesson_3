package com.springboot.lesson3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="userRoles")
public class LibRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    public LibRole(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "libRoleList")
    private List<LibUser> libUserList;

    public LibRole() {}
}
