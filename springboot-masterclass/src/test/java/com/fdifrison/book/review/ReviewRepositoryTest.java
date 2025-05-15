package com.fdifrison.book.review;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import javax.sql.DataSource;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

// TODO @DataJpaTest by default tries to autoconfigure an in-memory db; we could use h2 but it
//  wouldn't be a realistic representation of our prod environment.
//  Also it contains the @Trasanctional annotation to roll back the result of each test
@DataJpaTest(
    properties = {
      "spring.liquibase.enabled=false", // we don't need liquibase for the test
      "spring.jpa.hibernate.ddl-auto=create-drop", // better  ith in-memory db like h2 for testing
      // P6Spy db log interceptor
      "spring.datasource.driver-class-name=com.p6spy.engine.spy.P6SpyDriver",
      "spring.datasource.url=jdbc:p6spy:h2:mem:testing;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false", // P6Spy
      // Show SQL queries
      "spring.jpa.show-sql=true",
      // Format SQL for better readability
      "spring.jpa.properties.hibernate.format_sql=true",
      // Log transaction details
      "logging.level.org.hibernate.engine.transaction=DEBUG",
      // Log parameter binding
      "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE"
    })
// TODO triggers autoconfiguration for the db overriding the application properties ones and setting
//  up the h2 specifics
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

  // TODO @DataJpaTest also provide a testEntityManager that basically wraps a set of operations
  //  against the persistence context in a convenient way making sure that the synchronization
  //  between the first lvl cache (persistence context) and the underlying db is performed
  @Autowired private EntityManager entityManager;

  @Autowired private ReviewRepository cut;

  @Autowired private DataSource dataSource;

  @Autowired private TestEntityManager testEntityManager;

  @BeforeEach
  void setUp() {
    // check that the db is empty at each test execution since @Trasactional test annotation clean
    // the db after each test
    assertThat(cut.count()).isZero();
  }

  @Test
  void verifySetup() {
    assertThat(entityManager).isNotNull();
    assertThat(cut).isNotNull();
    assertThat(dataSource).isNotNull();
    assertThat(testEntityManager).isNotNull();

    var review = Instancio.create(Review.class).setId(null).setBook(null).setUser(null);
    var result = cut.save(review);

    assertThat(result.getId()).isNotNull();
  }
}
