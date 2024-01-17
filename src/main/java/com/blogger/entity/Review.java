package com.blogger.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Reivews")
public class Review {

    @Id

    private String id;

    @Column(name = "UserName")
    private String name;
   @Column(name = "UserEmail")
    private String email;

   @Column(name = "Rating")
    private double rating ;

   @Column(name = "Content")
    private String content;

   @Column(name = "Doctor")
   private String DoctorAccount;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

    @Column(name = "DateTime")
    private String Datetime;
}
