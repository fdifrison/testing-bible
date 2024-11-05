package com.fdifrison.app;

import java.time.LocalDateTime;

public class User {

  private Long id;
  private String username;
  private String email;
  private LocalDateTime createdAt;

  public Long getId() {
    return id;
  }

  public User setId(Long id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public User setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", email='" + email + '\'' +
      ", createdAt=" + createdAt +
      '}';
  }
}
