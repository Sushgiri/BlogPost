package com.blogger.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class showpost {
    private   String id;

    private String author;

    private String title;


    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    private String content;
    private String datetime;

    private String comment;
}
