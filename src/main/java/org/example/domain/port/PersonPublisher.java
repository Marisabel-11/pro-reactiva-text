package org.example.domain.port;

import org.example.domain.model.Person;
import reactor.core.publisher.Flux;

//Puerto de entrada
public interface PersonPublisher {
    Flux<Person> getPersons();
}
