package org.example.infrastructure.adapter;

import org.example.domain.model.Person;
import org.example.domain.port.PersonObserver;
import org.reactivestreams.Subscription;
//Adaptador de salida
public class CountPersonObserver implements PersonObserver {
    private Subscription subscription;
    private long count = 0;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Person person) {
        count++;
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error in count: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Total persons > 30 years: " + count);
    }
}
