package org.example.domain.port;

import org.example.domain.model.Person;
import org.reactivestreams.Subscriber;

//Puerto de salida
public interface PersonObserver extends Subscriber<Person> {
}
