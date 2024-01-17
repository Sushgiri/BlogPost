package com.blogger.controller;

import com.blogger.config.RestemplateConfig;
import com.blogger.entity.User;
import com.blogger.payload.PostDto;
import com.blogger.repository.UserRepository;
import com.blogger.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/blog")
public class Postcontroller {
    //http://localhost:8082/user/blog/
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestemplateConfig restTemplate;

    @PostMapping("/{username}")
    public ResponseEntity<?> writeblog(@PathVariable String username, @RequestBody PostDto postDto){
        User byUsername = userRepository.findByUsername(username);
try{
    restTemplate.getRestTemplate().postForEntity("http://localhost:8083/api/posts/"+username, postDto, String.class);

}
catch (Exception e){
    throw new RuntimeException("Something wnet wrong post is not saved");
}



       return new ResponseEntity<>("Post is saved", HttpStatus.OK);
    }


}
