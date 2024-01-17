package com.blogger.payload;

import lombok.Data;

import javax.validation.constraints.Size;
@Data
public class Resetpassword {

    @Size(min = 8 ,message = "Password should be atleast of 8 characters")
    private String newpassword;
}
