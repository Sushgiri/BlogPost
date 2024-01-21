package com.blogger.payload;

import lombok.Data;

import javax.validation.constraints.Positive;
@Data
public class Medicinedto {


    private String medicineId;

    private String name;

    private String manufacturer;

    private int stock;

    private Double price;

    private boolean prescribed;
private String order;
}
