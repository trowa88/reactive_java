package com.reactive.rx_java.rx_java;


import rx.Observable;
import rx.Subscriber;

public class RxJavaSample1 {
    public static void main(String[] args) {
        Observable<String> myObservable = Observable.create(
                subscriber -> {
                    subscriber.onNext("Hello, world!");
                    subscriber.onCompleted();
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        myObservable.subscribe(mySubscriber);
    }
}
