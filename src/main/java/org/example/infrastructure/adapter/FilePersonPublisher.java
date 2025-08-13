package org.example.infrastructure.adapter;

import org.example.domain.model.Person;
import org.example.domain.port.PersonPublisher;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

//Adaptador de entrada
public class FilePersonPublisher implements PersonPublisher {
    private final Path filePath;

    public FilePersonPublisher(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Flux<Person> getPersons() {
        return Flux.using(
                () -> Files.lines(filePath),
                Flux::fromStream,
                Stream::close
        ).map(line -> line.split("\\|"))
                .map(fields -> new Person(
                Integer.parseInt(fields[0]),
                fields[1],
                fields[2],
                Integer.parseInt(fields[3])
        ));
    }
}
