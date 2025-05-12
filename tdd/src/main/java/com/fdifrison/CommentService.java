package com.fdifrison;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
  public List<Comment> getAllComments() {
    return List.of();
  }

  public void saveComment(@Valid CommentCreationRequest comment, Authentication authentication) {}
}
