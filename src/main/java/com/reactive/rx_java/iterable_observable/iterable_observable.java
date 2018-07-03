package com.reactive.rx_java.iterable_observable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class iterable_observable {
    public static void main(String[] args) {
        List<Integer> dataArr = Arrays.asList(1, 2, 3, 4);
        for (int data : dataArr) {
            System.out.println(data);
        }

        Iterable<Integer> iterableList = Arrays.asList(1, 2, 3, 4);
        for (Iterator it = iterableList.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }
}
