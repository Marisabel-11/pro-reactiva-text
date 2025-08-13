package org.example.app;
import org.example.domain.model.Person;
import org.example.domain.port.PersonObserver;
import org.example.domain.port.PersonPublisher;
import org.example.infrastructure.adapter.CountPersonObserver;
import org.example.infrastructure.adapter.EmailWriterObserver;
import org.example.infrastructure.adapter.FilePersonPublisher;
import reactor.core.publisher.Flux;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path dataFile = Path.of("data/data.txt");
        Path outputDir = Path.of("data/output");

        PersonPublisher publisher = new FilePersonPublisher(dataFile);
        PersonObserver emailObserver = new EmailWriterObserver(outputDir);
        PersonObserver countObserver = new CountPersonObserver();

        Flux<Person> filteredForFile = publisher.getPersons()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45);

        Flux<Person> filteredForCount = publisher.getPersons()
                .filter(p -> p.getAge() > 30);

        filteredForFile.subscribe(emailObserver);
        filteredForCount.subscribe(countObserver);
    }
}