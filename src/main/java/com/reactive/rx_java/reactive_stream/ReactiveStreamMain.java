package com.reactive.rx_java.reactive_stream;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class ReactiveStreamMain {
    public static void main(String[] args) {
        Publisher pub = subscriber -> subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long n) {
                log.debug("request - {}", n);

                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onComplete();
            }

            @Override
            public void cancel() {

            }
        });

        Subscriber sub = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Object o) {
                log.debug("onNext - {}", o);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        };
        pub.subscribe(sub);
        log.debug("exit");

        Publisher pubOnPub = new Publisher() {
            @Override
            public void subscribe(Subscriber s) {
                Executors.newSingleThreadExecutor().execute(() -> pub.subscribe(s));
            }
        };
        pubOnPub.subscribe(sub);

        Subscriber subOnSub = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {
                Executors.newSingleThreadExecutor().execute(() -> sub.onSubscribe(s));
            }

            @Override
            public void onNext(Object o) {
                Executors.newSingleThreadExecutor().execute(() -> sub.onNext(o));
            }

            @Override
            public void onError(Throwable t) {
                Executors.newSingleThreadExecutor().execute(() -> sub.onError(t));
            }

            @Override
            public void onComplete() {
                Executors.newSingleThreadExecutor().execute(() -> sub.onComplete());
            }
        };
        pub.subscribe(subOnSub);
    }
}
