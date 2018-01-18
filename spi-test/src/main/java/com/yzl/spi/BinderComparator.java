package com.yzl.spi;

import java.util.Comparator;

public class BinderComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        SpiBinder p1 = o1.getClass().getAnnotation(SpiBinder.class);
        SpiBinder p2 = o2.getClass().getAnnotation(SpiBinder.class);
        if (p1 == null) {
            return 1;
        } else if (p2 == null) {
            return -1;
        } else {
            return p1.order() - p2.order();
        }
    }


}
