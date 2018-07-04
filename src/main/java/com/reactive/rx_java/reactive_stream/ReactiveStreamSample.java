package com.reactive.rx_java.reactive_stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Stack;

public class ReactiveStreamSample {
    public static void main(String[] args) {
        Publisher<Integer> publisher = new Publisher<>() {
            Stack<Integer> stack;

            @Override
            public void subscribe(Subscriber s) {
                stack = new Stack<>();

                for (int i = 0; i < 10; i++) {
                    stack.push(i);
                }
                s.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        System.out.println("request " + n);

                        if (n < 0) {
                            s.onError(new Exception("   0 이상의 숫자를 넣어야 합니다"));
                        }

                        for (int i = 1; i <= n; i++) {
                            if (stack.empty()) {
                                s.onComplete();
                                return;
                            }
                            s.onNext(stack.pop());
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> subscriber = new Subscriber<>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                this.subscription = s;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer o) {
                System.out.println("    onNext - " + o);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("    onError -" + t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("    onComplete");
            }
        };
        publisher.subscribe(subscriber);
    }
}
