package org.example.domain.model;

public class Person {
    private final int id;
    private final String name;
    private final String email;
    private final int age;

    public Person(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return name + " (" + age + " años)";
    }
}
