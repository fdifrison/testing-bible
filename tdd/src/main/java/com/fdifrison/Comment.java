package com.fdifrison;

import java.time.LocalDate;
import java.util.UUID;

public record Comment(UUID id, String userId, String content, LocalDate creationDate) {}
