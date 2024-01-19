package com.blogger.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {


    private   String id;

    private String author;
    @NotEmpty
    @Size(min = 2, message = "Title should be atleast two character")
    private String title;

     @NotEmpty
     @Size(min = 15 ,max = 100, message = "Description should contain atleast 15 charcters")
     private String description;

    public void setDescription(String description) {
        this.description = description;
    }
   @NotEmpty
   @Size(min = 20, max = 400 ,message = "Content should be atleast 20 characters")
    private String content;
    private String datetime;


    private  double rating;
    private String comment;










}
