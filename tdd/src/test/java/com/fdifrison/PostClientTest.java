package com.fdifrison;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;

class PostClientTest {

  @RegisterExtension
  static WireMockExtension mockServer =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private final PostClient cut =
      new PostClient(RestClient.builder().baseUrl(mockServer.baseUrl()).build());

  @Test
  void shouldReturnAllPosts() {

    long limit = -1;
    long skip = 0;

    mockServer.stubFor(
        WireMock.get(String.format("/posts?limit=%d&skip=%d", limit, skip))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withBodyFile("dummyjson/get-all-posts-page.json")
                    .withHeader("Content-Type", "application/json")));

    var allPosts = cut.getAllPosts();
    assertThat(allPosts).hasSize(250);
  }
}
