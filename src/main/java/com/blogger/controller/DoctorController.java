package com.blogger.controller;

import com.blogger.payload.Doctorview;
import com.blogger.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/doctor")
public class DoctorController {
    @Autowired
    private UserService userService;
    @GetMapping("/search/doctor/{userId}")
    public ResponseEntity<?> searchdoctos(@PathVariable String userId){
        Doctorview searchdoctors = userService.searchdoctors(userId);
        return new ResponseEntity<>(searchdoctors, HttpStatus.OK);

    }
}
