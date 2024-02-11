package com.springboot.lesson3.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class LibUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String pwd;

    public LibUser(String name, String pwd, List<LibRole> libRoleList) {
        this.name = name;
        this.pwd = pwd;
        this.libRoleList = libRoleList;
    }

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "userRoles_id")
    )
    private List<LibRole> libRoleList;

    public LibUser() {}
}
