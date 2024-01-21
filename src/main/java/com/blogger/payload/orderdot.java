package com.blogger.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class orderdot {



    @JsonProperty
    private int quantity;
    @JsonProperty
    private boolean paid;
}
