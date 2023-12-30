package com.blogger.controller;

import com.blogger.entity.Post;
import com.blogger.payload.PostDto;
import com.blogger.service.impl.Postservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/blog/posts")
public class PostController {

      //http://localhost:8080/api/posts
    private Postservice postservice;

    public PostController (Postservice postservice){

        this.postservice= postservice;
    }


//    @PostMapping
//    public void createPost(@RequestBody PostDto postDto){
//       postservice.createPost(postDto);
//    }

//    @PostMapping
//    public ResponseEntity<String> createPost(@RequestBody PostDto postDto){
//        PostDto dto = postservice.createPost(postDto);
//        return new ResponseEntity<>("Post is Created",HttpStatus.CREATED);
//    }

// phase 1
//    @PostMapping
//    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
//
//
//        PostDto dto = postservice.createPost(postDto);
//
//        return new ResponseEntity<>(dto,HttpStatus.CREATED);
//    }

    // phase 2
//    @PostMapping
//    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
//        PostDto dto = postservice.createPost(postDto);
//        return new ResponseEntity<>(dto,HttpStatus.CREATED);
//    }

      @PostMapping
    public ResponseEntity<?> createpost(@Valid @RequestBody  PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
         PostDto postdto = postservice.createPost(postDto);
        return new ResponseEntity<>(postdto,HttpStatus.CREATED);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletepost(@PathVariable long id){
        postservice.deletepost(id);

        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);
    }
@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity <?>datepost(@PathVariable long id,@Valid @RequestBody PostDto  postDto ,BindingResult bindingResult){

      if(bindingResult.hasErrors()){
          return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
      }
        Optional<Post> fetched = postservice.find(id);
            Post posts = fetched.get();
        posts.setTitle(postDto.getTitle());
        posts.setDescription(postDto.getDescription());
        posts.setContent(postDto.getContent());
        postservice.updatesave(posts);

              return  new ResponseEntity<>("Post  is Updated with id"+id,HttpStatus.OK);

    }
@PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<Post>> readAll(){

        List<Post> posts = postservice.readAll();
        return   ResponseEntity.ok(posts);
    }

//     @GetMapping("api/posts/read")
//     public ResponseEntity<List<Post>> readAll(){
//         List<Post> dto =Postrepo.findAll();
//        return  new ResponseEntity<>(dto,HttpStatus.OK);
//     }


}