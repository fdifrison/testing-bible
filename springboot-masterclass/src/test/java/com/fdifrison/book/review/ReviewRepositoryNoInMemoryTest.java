package com.fdifrison.book.review;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@DataJpaTest(
    properties = {
      "spring.liquibase.enabled=false",
      "spring.jpa.hibernate.ddl-auto=create",
      "spring.jpa.show-sql=true",
      "spring.jpa.properties.hibernate.format_sql=true",
      "logging.level.org.hibernate.engine.transaction=DEBUG",
    })
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

  // TODO testing a native query
  @Test
  // TODO to disable the @Transactional given by @DataJpaTest for a single testcase. Modification to
  //  the db will be committed
  // @Rollback(false)
  @Sql(scripts = "/scripts/INIT_REVIEW_EACH_BOOK.sql")
  void shouldGetTwoReviewStatisticsWhenDatabaseContainsTwoBooksWithReview() {
    assertThat(cut.count()).isEqualTo(3L);
    assertThat(cut.getReviewStatistics()).hasSize(2);
  }

  @Test
  void databaseShouldBeEmpty() {
    assertThat(cut.count()).isZero();
  }
}
