package com.blogger.payload;

import lombok.Data;

@Data
public class medicineview {
    private  String id;

    private String medicineId;

    private String name;

    private String manufacturer;

    private int stock;

    private Double price;

    private boolean prescribed;
    private String order ="ORDER      :"+"http://lcoalhost:8082/user/medicine/";
}
