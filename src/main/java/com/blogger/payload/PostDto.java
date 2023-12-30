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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private   long id;

    @NotEmpty
    @Size(min = 2, message = "Title should be atleast two character")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
     @NotEmpty
     @Size(min = 15 ,max = 100, message = "Description should contain atleast 15 charcters")
     private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   @NotEmpty
   @Size(min = 20, max = 400 ,message = "Content should be atleast 20 characters")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
