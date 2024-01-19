package com.blogger.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AvailableSlots {

    private String book;
    private String date;
    private boolean morningSlot;
    private boolean afternoonSlots;
    private boolean eveningSlots;


}
