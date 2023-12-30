package com.blogger.controller;

import com.blogger.entity.Post;
import com.blogger.payload.PostDto;
import com.blogger.payload.commentdto;
import com.blogger.service.impl.commentserviceimpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment/test")
public class commentController {

        //http://localhost:8080/comment/test?postId=


    private commentserviceimpl commentser;

    public commentController(commentserviceimpl commentser) {
        this.commentser = commentser;
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestParam("postId") long postId,
    @RequestBody commentdto commdto
    ){
           commentser.save(postId,commdto);

           return new ResponseEntity<>("Comment is saved", HttpStatus.OK);
    }
    @PreAuthorize("hasRole('COMMENTOR')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete (@PathVariable long commentId){
        commentser.deletecomment(commentId);

        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ANALYST')")
    @GetMapping("/{postId}")
    public ResponseEntity<?>  getallcomments(@PathVariable long postId){
        List<commentdto> getcommentbypostid = commentser.getcommentbypostid(postId);
        if (getcommentbypostid.isEmpty()){
            return new ResponseEntity<>("There are No comments for this post",HttpStatus.OK);
        }
        return new ResponseEntity<>(getcommentbypostid,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ANALYST')")
    @GetMapping("/mostrated")
    public ResponseEntity<?>  mostrated(){
        Post mostrated = commentser.mostrated();
        PostDto dto  = new PostDto();
        dto.setId(mostrated.getId());
        dto.setContent(mostrated.getContent());
        dto.setTitle(mostrated.getTitle());
        dto.setId(mostrated.getId());
        dto.setDescription(mostrated.getDescription());
        return new ResponseEntity<>("Here is the most rated post"+dto,HttpStatus.OK);

    }
    @PreAuthorize("hasRole('ANALYST')")
    @GetMapping("/leastrated")
    public ResponseEntity<?>  leastrrated(){
        Post leastrated = commentser.leastrated();
        PostDto dto  = new PostDto();
        dto.setId(leastrated.getId());
        dto.setContent(leastrated.getContent());
        dto.setTitle(leastrated.getTitle());
        dto.setId(leastrated.getId());
        dto.setDescription(leastrated.getDescription());
        return new ResponseEntity<>("Here is the least rated post"+dto,HttpStatus.OK);

    }
    @PutMapping
    public ResponseEntity<?> update(@RequestParam("commentId") long commentid, @RequestBody commentdto dto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        commentser.update(commentid,dto);

        return new ResponseEntity<>("Comment is updated",HttpStatus.OK);
    }






}
