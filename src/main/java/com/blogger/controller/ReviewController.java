package com.blogger.controller;

import com.blogger.entity.Review;
import com.blogger.payload.Doctordto;
import com.blogger.payload.Reviewdto;
import com.blogger.service.impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//http://localhost:8082/review/api/
@GetMapping("/{DoctorId}")
    public ResponseEntity<?> readreviews(@PathVariable String DoctorId){
    List<Review> readallreviewsbydoctorid = reviewService.readallreviewsbydoctorid(DoctorId);
    if(readallreviewsbydoctorid == null){
        return new ResponseEntity<>("No Reviews on this profile yet",HttpStatus.OK);
    }
    return new ResponseEntity<>(readallreviewsbydoctorid,HttpStatus.OK);
}
}
