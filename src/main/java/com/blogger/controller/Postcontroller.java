package com.blogger.controller;

import com.blogger.config.RestemplateConfig;
import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.payload.PostDto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import com.blogger.service.impl.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user/blog")
public class Postcontroller {
    //http://localhost:8082/user/blog/
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RestemplateConfig restTemplate;

    @PostMapping("/{username}")
    @CircuitBreaker(name = "postBreaker", fallbackMethod = "postFallback")
    public ResponseEntity<?> writeblog(@PathVariable String username, @RequestBody PostDto postDto) {
        User byUsername = userRepository.findByUsername(username);
        restTemplate.getRestTemplate().postForEntity("http://localhost:8083/api/posts/" + byUsername.getUsername(), postDto, String.class);
        assginrole(byUsername);
        return new ResponseEntity<>("post is saved", HttpStatus.OK);
    }
    public void assginrole(User user) {
        Role roles = roleRepository.findByName("ROLE_AUTHOR").get();
        user.getRoles().add(roles);
        userRepository.save(user);
    }
    //http://localhost:8082/user/blog/readallblogs
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/readallblogs/{username}")
    @CircuitBreaker(name = "postBreaker", fallbackMethod = "postFallback")
    public ResponseEntity<?> readallblogs(@PathVariable String username) {
        PostDto [] postDto = restTemplate.getRestTemplate().getForObject("http://localhost:8083/api/posts/all/posts", PostDto[].class);
       List<PostDto> returndto =  Arrays.stream(postDto).collect(Collectors.toList());
       for(PostDto addcomment:postDto){
           addcomment.setComment("http://localhost:8082/user/comment/"+addcomment.getId()+"/"+username);
           returndto.add(addcomment);
       }
       List<PostDto> showdto = new ArrayList<>(returndto);
        Set<PostDto> returdto = new HashSet<>(showdto);
        return new ResponseEntity<>(returdto, HttpStatus.OK);
    }
    public ResponseEntity<PostDto> postFallback(String username, Exception ex) {
        System.out.println("Fallback is executed because service is down : "+ ex.getMessage());

        ex.printStackTrace();

        PostDto dto = new PostDto();

        dto.setTitle("Post Down");
        dto.setContent("Post Down");
        dto.setDescription("Post Down");

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

}