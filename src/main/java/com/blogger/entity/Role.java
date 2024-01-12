package com.blogger.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
    @Setter
    @Getter
    @Entity
    @Table(name = "roles")
    public class Role {
        @Id
        private long id;
        @Column(length = 40)
        private String name;
    }


