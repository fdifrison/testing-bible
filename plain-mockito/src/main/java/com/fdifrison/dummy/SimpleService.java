package com.fdifrison.dummy;

public class SimpleService {

    private final SimpleRepository repository;

    public SimpleService(SimpleRepository repository) {
        this.repository = repository;
    }

    public void sayHello() {
        System.out.printf(
                "Hello World from a Service! It's using the [%s] repository!%n",
                repository.getClass().getSimpleName());
    }
}
