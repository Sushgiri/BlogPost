package com.blogger.payload;

import lombok.Data;

 @Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}

