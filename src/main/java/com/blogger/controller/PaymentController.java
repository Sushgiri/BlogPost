package com.blogger.controller;

import com.blogger.config.RestemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/payment")

public class PaymentController {

    @Autowired
    private RestemplateConfig restemplateConfig;



}
