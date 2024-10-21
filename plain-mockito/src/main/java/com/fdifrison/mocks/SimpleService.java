package com.fdifrison.mocks;

public class SimpleService {

    private final SimpleRepository repo;

    public SimpleService(SimpleRepository repo) {
        this.repo = repo;
    }

    public void sayHello() {
        System.out.printf("Hello World from a Service! It's using the [%s] repository!%n", repo.getClass().getSimpleName());
    }


}
