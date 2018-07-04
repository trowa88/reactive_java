package com.reactive.rx_java.rx_java;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

import java.util.concurrent.TimeUnit;

public class RxJavaSample4 {
    public static void main(String[] args) {
        Observable.just("Hello, world!")
                .map(RxJavaSample4::potentialException)
                .map(RxJavaSample4::anotherPotentialException)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Ouch!");
                    }
                });
        System.out.println();

        Observable
                .create(subscriber -> {
                    log("subscribe");
                    subscriber.onNext("emit 1");
                    subscriber.onNext("emit 2");
                    subscriber.onError(new Throwable());
                })
                .onErrorReturn(throwable -> "return")
                .subscribe(
                        s -> log("on next: " + s),
                        throwable -> log("error: " + throwable),
                        () -> log("completed")
                );
        System.out.println();

        Observable
                .create(subscriber -> {
                    log("subscribe");
                    subscriber.onNext("emit 1");
                    subscriber.onNext("emit 2");
                    subscriber.onError(new Throwable());
                })
                .onErrorResumeNext(throwable -> Observable.from(new String[]{"resume 1", "resume 2"}))
                .subscribe(
                        s -> log("on next: " + s),
                        throwable -> log("error: " + throwable),
                        () -> log("completed")
                );
        System.out.println();

        Observable
                .create(subscriber -> {
                    log("subscribe");
                    subscriber.onNext("emit 1");
                    subscriber.onNext("emit 2");
                    subscriber.onError(new Throwable());
                })
                // .retry() // 무한으로 재시도
                .subscribe(
                        s -> log("on next: " + s),
                        throwable -> log("error: " + throwable),
                        () -> log("completed")
                );
        System.out.println();

        Observable
                .create(subscriber -> {
                    log("subscribe");
                    subscriber.onNext("emit 1");
                    subscriber.onNext("emit 2");
                    subscriber.onError(new Throwable());
                })
                // retry 조건 다양하게 설정
                .retry((count, throwable) -> {
                    // 두 번까지는 무조건 retry
                    if (count < 3) {
                        return true;
                    }
                    // 그 이후는 IllegalStateException 의 경우에만 retry
                    return throwable instanceof IllegalStateException;
                })
                .subscribe(
                        s -> log("on next: " + s),
                        throwable -> log("error: " + throwable),
                        () -> log("completed")
                );
        System.out.println();

        Observable
                .create(subscriber -> {
                    log("subscribe");
                    subscriber.onNext("emit 1");
                    subscriber.onNext("emit 2");
                    subscriber.onError(new Throwable());
                })
                // retry 시간 설정
                .retryWhen(observable -> observable.flatMap(
                        (Func1<Throwable, Observable<?>>) throwable -> Observable.timer(3, TimeUnit.SECONDS))
                )
                .subscribe(
                        s -> log("on next: " + s),
                        throwable -> log("error: " + throwable),
                        () -> log("completed")
                );
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static String anotherPotentialException(String s) {
        return "anotherPotentialException - " + s;
    }

    private static String potentialException(String s) {
        return "potentialException - " + s;
    }
}
