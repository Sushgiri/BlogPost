package com.blogger.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;
    @Data
    @Entity
    @Table(name = "users", uniqueConstraints = {
            @UniqueConstraint(columnNames = {"username"}),
            @UniqueConstraint(columnNames = {"email"})
    })
    public class User {

        @Id
        private String id;
        private String name;
        private String Signupdatetime;
        @Size(min =8, max = 10,message = "username should be of 8 to 10 characters")
        private String username;
        private String email;

        @Size(min =8,max = 9,message = "password should of exactly 8 characters")
        private String password;
        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName
                        = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id",
                        referencedColumnName = "id"))




        private Set<Role> roles;
    }

