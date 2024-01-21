package com.blogger.controller;

import com.blogger.config.RestemplateConfig;
import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.payload.commentdto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/comment")
public class commentController {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RestemplateConfig restTemplate;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{postId}/{username}")
    public ResponseEntity<?> comment (@PathVariable String username, @PathVariable String postId, @RequestBody commentdto commentdto){
        User byUsername = userRepository.findByUsername(username);
        restTemplate.getRestTemplate().postForEntity("http://localhost:8084/api/comments/"+username+"/"+postId,commentdto,String.class);
        Role roles = roleRepository.findByName("ROLE_CUSTOMER").get();
        byUsername.getRoles().add(roles);
        userRepository.save(byUsername);
        return new ResponseEntity<>("Comment saved", HttpStatus.OK);
    }

}
