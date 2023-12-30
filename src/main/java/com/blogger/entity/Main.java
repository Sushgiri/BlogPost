package com.blogger.entity;

import java.util.ArrayList;
import java.util.List;


public class Main {


    private  long id;

    List<User> user = new ArrayList<>();

    List<comment> comments = new ArrayList<>();

    List<Post> posts = new ArrayList<>();

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<comment> getComments() {
        return comments;
    }

    public void setComments(List<comment> comments) {
        this.comments = comments;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}

