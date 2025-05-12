package com.fdifrison;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
  public List<Comment> getAllComments() {
    return List.of();
  }

  public void saveComment(@Valid CommentCreationRequest comment, Authentication authentication) {

  }
}
