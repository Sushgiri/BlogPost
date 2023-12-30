package com.blogger.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;




@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Comments")
public class comment implements Comparable<comment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  long id;

        private String name;
        private String body;


        private double rating;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    private String email;

        @ManyToOne
      @JoinColumn(name="posted_id")
       private Post post;

    @Override
    public int compareTo(comment o) {
        return (int) (this.rating-o.rating);
    }
}
