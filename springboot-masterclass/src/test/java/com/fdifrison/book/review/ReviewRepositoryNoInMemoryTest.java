package com.fdifrison.book.review;

import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@DataJpaTest(
    properties = {"spring.liquibase.enabled=false", "spring.jpa.hibernate.ddl-auto=create"})
// TODO manage the lifecycle of the container
// @Testcontainers(disabledWithoutDocker = true)
// TODO stop spring to autoconfigure an in-memory db
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryNoInMemoryTest {

  // TODO in conjunction with @Testcontainers manage the container lifecycle (don't work with reuse)
  // @Container
  // TODO autoconfigure the datasource connections properties
  @ServiceConnection
  static PostgreSQLContainer<?> container =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
          .withDatabaseName("test")
          .withUsername("duke")
          .withPassword("s3cret")
          // TODO reusable container across tests; we also need to add
          //  "testcontainers.reuse.enable=true" in the .testcontainers.properties file, usually
          //  under /home/username and start the container manually from a static block as explained
          //  in https://java.testcontainers.org/features/reuse/
          .withReuse(true);

  // TODO in some cases it can be useful to manage manually the container lifecycle:
  static {
    container.start();
  }

  // TODO set dynamically properties in the application context, substituted by @ServiceConnection
  //  @DynamicPropertySource
  //  static void properties(DynamicPropertyRegistry registry) {
  //    registry.add("spring.datasource.url", container::getJdbcUrl);
  //    registry.add("spring.datasource.password", container::getPassword);
  //    registry.add("spring.datasource.username", container::getUsername);
  //  }

  @Autowired private ReviewRepository cut;

  @Test
  void verifySetup() {
    assertThat(container).isNotNull();
    assertThat(cut).isNotNull();

    var review = Instancio.create(Review.class).setId(null).setBook(null).setUser(null);
    var result = cut.save(review);

    assertThat(result.getId()).isNotNull();
  }

  //  @Test
  //  @Sql(scripts = "/scripts/INIT_REVIEW_EACH_BOOK.sql")
  //  void shouldGetTwoReviewStatisticsWhenDatabaseContainsTwoBooksWithReview() {}
  //
  //  @Test
  //  void databaseShouldBeEmpty() {}
}
