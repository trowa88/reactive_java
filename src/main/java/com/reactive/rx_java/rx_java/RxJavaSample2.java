package com.reactive.rx_java.rx_java;

import rx.Observable;

public class RxJavaSample2 {
    public static void main(String[] args) {
        Observable.just("Hello World").subscribe(System.out::println);
        Observable.just("Hello World").subscribe(s -> System.out.println("[" + System.currentTimeMillis() + "]" + s));

        Observable.just("Hello, world!")
                .map(s -> "[" + System.currentTimeMillis() + "]" + s)
                .subscribe(System.out::println);

        Observable.just("Hello, world!")
                .map(s -> "[" + System.currentTimeMillis() + "]" + s)
                .map(String::length)
                .subscribe(System.out::println);
    }
}
