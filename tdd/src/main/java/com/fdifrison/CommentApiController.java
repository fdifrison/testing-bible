package com.fdifrison;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Develop a REST API to retrieve and create comments. Everybody should be able to retrieve comments
 * but only logged-in users with the role ADMIN can create a comment.
 */
@RestController
@RequestMapping("/api/comments")
class CommentApiController {

  private final CommentService commentService;

  CommentApiController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public List<Comment> getComments() {
    return commentService.getAllComments();
  }

  @PostMapping
  public void addComment(
      @Valid @RequestBody CommentCreationRequest comment,
      Authentication authentication) {
      commentService.saveComment(comment, authentication);
  }
}
