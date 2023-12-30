package com.blogger.controller;


import com.blogger.entity.Main;
import com.blogger.entity.Post;
import com.blogger.entity.User;
import com.blogger.entity.comment;
import com.blogger.repository.UserRepository;
import com.blogger.service.impl.Postserviceimpl;
import com.blogger.service.impl.commentserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("blog/main")
public class Maincontroller {

  //https
    @Autowired
    public Postserviceimpl postserviceimpl;



    @Autowired
    public UserRepository userRepository;



    @Autowired
    public commentserviceimpl commentserviceimpl;
    @GetMapping
    public ResponseEntity<?> readalltable() {

        List<Post> posts = postserviceimpl.readAll();
        List<comment> getallcomments = commentserviceimpl.getallcomments();
        List<User> users = userRepository.findAll();
        Main main = new Main();
        main.setPosts(posts);
        main.setComments(getallcomments);
        main.setUser(users);

        return new ResponseEntity<>(main, HttpStatus.OK);
    }
}
