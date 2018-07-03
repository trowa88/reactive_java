package com.reactive.rx_java.iterable_observable;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ObservableMain {
    public static void main(String[] args) {
        Observable1 observable1 = new Observable1();

        Observer observer1 = (o, data) -> System.out.println(data);
        observable1.addObserver(observer1);
        observable1.say();
    }
}

class Observable1 extends Observable {
    public void say() {
        List<Integer> dataArr = Arrays.asList(1, 2, 3, 4, 5);

        for (Object i : dataArr) {
            setChanged();
            notifyObservers(i);
        }
    }
}
