package com.ccand99.effective.chapter5;

import java.util.ArrayList;
import java.util.List;

public class covariant {

    public static void main(String[] args) {
        //Fails at runtime (covariant)
        Object[] objectArray = new Long[1];
        objectArray[0] = "I don't fit it"; // throws ArrayStoreException

        //Won't compile! (invariant)
        List<Object> o1 = new ArrayList<Long>(); //Incompatible types
    }
}
