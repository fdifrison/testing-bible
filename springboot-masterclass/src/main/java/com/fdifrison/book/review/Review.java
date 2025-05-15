package com.fdifrison.book.review;

import com.fdifrison.book.management.Book;
import com.fdifrison.book.management.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Integer rating;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne private Book book;

  @ManyToOne private User user;

  public Long getId() {
    return id;
  }

  public Review setId(Long id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Book getBook() {
    return book;
  }

  public Review setBook(Book book) {
    this.book = book;
    return this;
  }

  public User getUser() {
    return user;
  }

  public Review setUser(User user) {
    this.user = user;
    return this;
  }

  @Override
  public String toString() {
    return "Review{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", content='"
        + content
        + '\''
        + ", rating="
        + rating
        + ", createdAt="
        + createdAt
        + ", book="
        + book
        + ", user="
        + user
        + '}';
  }
}
