package com.fdifrison.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.MediaType;

public class OpenLibraryStubs {

  private final WireMockServer wireMockServer;

  public OpenLibraryStubs(WireMockServer wireMockServer) {
    this.wireMockServer = wireMockServer;
  }

  public void stubForSuccessfulBookResponse(String isbn, String response) {
    this.wireMockServer.stubFor(
        WireMock.get("/openLibrary/api/books?jscmd=data&format=json&bibkeys=" + isbn)
            .willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(response)));
  }
}
