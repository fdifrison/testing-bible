package com.fdifrison;

import java.util.Set;

public record Post(
    long id, String title, String body, long userId, Set<String> tags, Reactions reactions, long views) {
  public record Reactions(long likes, long dislikes) {}
}
