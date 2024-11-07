package com.fdifrison.app;

public interface UserRepository {
    User save(User user);

    User findByUsername(String username);
}
