package com.reactive.rx_java.rx_java;

import rx.Observable;
import rx.functions.Func1;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RxJavaSample3 {
    public static Observable<List<String>> query(String text) {
        return Observable.just(Arrays.asList("www.naver.com", "www.google.com", "www.kakao.com"));
    }

    public static void main(String[] args) {
        query("Hello, world!")
                .subscribe(urls -> {
                    for (String url : urls) {
                        System.out.println(url);
                    }
                });
        System.out.println();

        query("Hello, world!")
                .subscribe(urls -> Observable.from(urls).subscribe(System.out::println));
        System.out.println();

        // flatMap
        query("Hello, world!")
                .flatMap(Observable::from)
                .map(s -> "[" + System.currentTimeMillis() + "]" + s)
                .subscribe(System.out::println);
        System.out.println();

        // filter
        query("Hello, world!")
                .flatMap(Observable::from)
                .filter(Objects::nonNull)
                .subscribe(System.out::println);
        System.out.println();

        // take
        // 기존 Reactive Stream 에 request 해당
        query("Hello, world!")
                .flatMap(Observable::from)
                .map(s -> "[" + System.currentTimeMillis() + "]" + s)
                .take(5)
                .subscribe(System.out::println);
        System.out.println();

        // doOnXX
        // onNext 실행될 때 호출
        query("Hello, world!")
                .flatMap(Observable::from)
                .flatMap(RxJavaSample3::getTitle)
                .filter(Objects::nonNull)
                .take(5)
                .doOnNext(RxJavaSample3::saveTitle)
                .subscribe(System.out::println);
    }

    private static void saveTitle(Object title) {
        System.out.println("saveTitle : " + title);
    }

    private static Observable<?> getTitle(String url) {
        return Observable.just("get " + url);
    }
}
