package org.example.infrastructure.adapter;

import org.example.domain.model.Person;
import org.example.domain.port.PersonObserver;
import org.reactivestreams.Subscription;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Adaptador de salida
public class EmailWriterObserver implements PersonObserver {
    private Subscription subscription;
    private final Path outputDir;
    private BufferedWriter writer;

    public EmailWriterObserver(Path outputBaseDir) {
        this.outputDir = outputBaseDir.resolve(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        );
        try {
            Files.createDirectories(outputDir);
            this.writer = Files.newBufferedWriter(outputDir.resolve("PrintPersonByAge.txt"),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Error creating output file", e);
        }
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Person person) {
        try {
            writer.write(person.getEmail());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing email: " + e.getMessage());
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error in stream: " + throwable.getMessage());
        closeWriter();
    }

    @Override
    public void onComplete() {
        closeWriter();
        System.out.println("Email writing completed.");
    }

    private void closeWriter() {
        try {
            if (writer != null) writer.close();
        } catch (IOException ignored) {}
    }
}
