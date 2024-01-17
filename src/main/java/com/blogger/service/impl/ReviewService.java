package com.blogger.service.impl;

import com.blogger.config.RestemplateConfig;
import com.blogger.entity.Review;
import com.blogger.entity.User;
import com.blogger.payload.Doctordto;
import com.blogger.payload.Reviewdto;
import com.blogger.repository.ReviewRepository;
import com.blogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private RestemplateConfig restTemplate;

    public String  reviewdoctor(String userId, String Doctoremail, Reviewdto reviewdto) {

      Optional<User> user = userrepository.findById(userId);
        Review review = new Review();
        String reviewid = UUID.randomUUID().toString();
        LocalDateTime todaydatetime = LocalDateTime.now();
         review.setDatetime(todaydatetime.toString());
          review.setId(reviewid);
          review.setName(user.get().getName());
          review.setEmail(user.get().getEmail());
          review.setUser(user.get());
          review.setDoctorAccount(Doctoremail);
          review.setContent(reviewdto.getContent());
          review.setRating(reviewdto.getRating());
        Review save = reviewRepository.save(review);
        Review passingreview = new Review();
        passingreview.setName(save.getName());
        passingreview.setId(save.getId());
        passingreview.setContent(save.getContent());
        passingreview.setDoctorAccount(save.getDoctorAccount());
        passingreview.setRating(save.getRating());
//        passingreview.setUser(save.getUser());
        if(save != null){
            restTemplate.getRestTemplate().postForEntity( "http://localhost:8081/doctor/api/review",
                    passingreview,
                    String.class);
            return " Review saved successfully";
        }
        return "Something went wrong REview not saved";

    }




    }


