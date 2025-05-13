package com.fdifrison.book.review;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class ReviewVerifier {

  private final List<String> swearWords = List.of("shit", "fuck", "bitch", "asshole");

  public boolean doesMeetQualityStandards(String review) {

    var words = review.split(" ");

    if (containsLoremIpsum(words)) return false;
    else if (hasRepetitions(words, "I", 5)) return false;
    else if (hasRepetitions(words, "good", 3)) return false;
    else if (containSwearWords(words)) return false;
    else return words.length > 10;
  }

  private boolean hasRepetitions(String[] words, String word, int count) {
    return Arrays.stream(words).filter(s -> s.equalsIgnoreCase(word)).count() >= count;
  }

  private boolean containsLoremIpsum(String[] words) {
    return Stream.of("lorem", "ipsum")
        .anyMatch(w -> Arrays.stream(words).anyMatch(w::equalsIgnoreCase));
  }

  private boolean containSwearWords(String[] words) {
    return swearWords.stream()
        .anyMatch(swear -> Arrays.stream(words).anyMatch(swear::equalsIgnoreCase));
  }
}
