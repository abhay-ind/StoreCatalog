package com.example.guptastores;

import java.util.Comparator;

public class ItemComparator implements Comparator<productNew> {

    @Override
    public int compare(productNew t1, productNew t2) {
        String a1=t1.getproductName().toUpperCase();
        String a2=t2.getproductName().toUpperCase();
        return a1.compareTo(a2);
    }
}
