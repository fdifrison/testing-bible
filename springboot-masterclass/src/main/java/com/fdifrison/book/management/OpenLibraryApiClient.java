package com.fdifrison.book.management;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenLibraryApiClient {

  private final RestClient openLibraryWebClient;

  public OpenLibraryApiClient(RestClient openLibraryWebClient) {
    this.openLibraryWebClient = openLibraryWebClient;
  }

  public Book fetchMetadataForBook(String isbn) {

    var result =
        openLibraryWebClient
            .get()
            .uri(
                "/api/books",
                uriBuilder ->
                    uriBuilder
                        .queryParam("jscmd", "data")
                        .queryParam("format", "json")
                        .queryParam("bibkeys", isbn)
                        .build())
            .retrieve()
            .body(ObjectNode.class);

    JsonNode content = Objects.requireNonNull(result).get(isbn);

    return convertToBook(isbn, content);
  }

  private Book convertToBook(String isbn, JsonNode content) {
    Book book = new Book();
    book.setIsbn(isbn);
    book.setThumbnailUrl(content.get("cover").get("small").asText());
    book.setTitle(content.get("title").asText());
    book.setAuthor(content.get("authors").get(0).get("name").asText());
    book.setPublisher(content.get("publishers").get(0).get("name").asText("n.A."));
    book.setPages(content.get("number_of_pages").asLong(0));
    book.setDescription(content.get("notes") == null ? "n.A" : content.get("notes").asText("n.A."));
    book.setGenre(
        content.get("subjects") == null
            ? "n.A"
            : content.get("subjects").get(0).get("name").asText("n.A."));
    return book;
  }
}
