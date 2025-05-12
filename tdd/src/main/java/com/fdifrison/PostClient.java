package com.fdifrison;

import java.util.List;
import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Develop an HTTP client that fetch all posts from the post-service and return them as a list. The
 * post-service returns the all posts with pagination. The client should fetch all pages and return
 * the result as a list.
 */
@Component
class PostClient {

  private final RestClient client;

  PostClient(RestClient dummyJsonClient) {
    this.client = dummyJsonClient;
  }

  public List<Post> getAllPosts() {
    var postPage =
        client
            .get()
            .uri("/posts?limit={limit}&skip={skip}", -1, 0)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(PostPage.class);
    return Objects.requireNonNull(postPage).posts();
  }

  //  public Page<Post> getPostsPage(Pageable pageable) {
  //    PostPage postPage =
  //        client
  //            .get()
  //            .uri("/posts?limit={limit}&skip={skip}", pageable.getPageSize(),
  // pageable.getOffset())
  //            .accept(MediaType.APPLICATION_JSON)
  //            .retrieve()
  //            .body(PostPage.class);
  //
  //    List<Post> posts = Objects.requireNonNull(postPage).posts();
  //    return new PageImpl<>(posts, pageable, postPage.total());
  //  }
}
