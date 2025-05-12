package com.fdifrison;

import jakarta.validation.constraints.NotBlank;

public record CommentCreationRequest(@NotBlank String content) {}
