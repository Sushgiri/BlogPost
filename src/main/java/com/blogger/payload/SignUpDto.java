package com.blogger.payload;

import lombok.Data;

import javax.validation.constraints.Size;


@Data

public class SignUpDto {
    private String name;
    private String username;
    private String email;
    @Size(min = 8 ,message = "Password should be atleast of 8 characters")
    private String password;
    private  String location;
}

