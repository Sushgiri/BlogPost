package com.blogger.controller;

import com.blogger.payload.Doctordto;
import com.blogger.payload.Reviewdto;
import com.blogger.service.impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review/api")
public class ReviewController {

     @Autowired
    private ReviewService reviewService;


@PostMapping("/{Doctoremail}/{userId}")
    public ResponseEntity<?> reivewdoctor( @PathVariable String  Doctoremail,@PathVariable String userId, @RequestBody Reviewdto reviewdto){
    String reviewdoctor = reviewService.reviewdoctor(userId, Doctoremail, reviewdto);
    return  new ResponseEntity<>(reviewdoctor, HttpStatus.OK);
}
}
