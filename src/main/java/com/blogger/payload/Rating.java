package com.blogger.payload;

import java.util.Comparator;

public class Rating implements Comparator<commentdto> {
    @Override
    public int compare(commentdto o1, commentdto o2) {
        return (int) (o1.getRating()-o2.getRating());
    }
}
