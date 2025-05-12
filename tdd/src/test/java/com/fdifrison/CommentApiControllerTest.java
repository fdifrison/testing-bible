package com.fdifrison;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// TODO see: https://rieckpil.de/course/tsbdr-tdd-example-3-rest-api/
@Import(SecurityConfiguration.class)
@WebMvcTest(CommentApiController.class)
class CommentApiControllerTest {

  @Autowired private MockMvc mockMvc; // comes for free with @WebMvcTest

  @MockitoBean private CommentService commentService;

  //  @InjectMocks private CommentApiController commentApiController;

  @Test
  void shouldAllowAnonymousUserToGetAllComments() throws Exception {

    when(commentService.getAllComments())
        .thenReturn(
            List.of(
                Instancio.of(Comment.class).create(),
                Instancio.of(Comment.class).create(),
                Instancio.of(Comment.class).create()));

    var result =
        mockMvc
            .perform(get("/api/comments").header("Content-Type", "application/json"))
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", Matchers.is(3)))
            .andExpect(jsonPath("$[0].content", Matchers.notNullValue()))
            .andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
            .andExpect(jsonPath("$[0].creationDate", Matchers.notNullValue()))
            .andExpect(jsonPath("$[0].userId", Matchers.notNullValue()));
  }

  @Test
  void shouldRejectAnonymousUserToCreateComment() throws Exception {
    mockMvc
        .perform(
            post("/api/comments")
                .header("Content-Type", "application/json")
                .content(
                    """
          {
              "content": "This is a comment"
          }
          """))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(
      username = "user",
      roles = {"GUEST"})
  void shouldNotAllowAuthenticatedButNotAdminUserToCreateComment() throws Exception {
    mockMvc
        .perform(
            post("/api/comments")
                .header("Content-Type", "application/json")
                .content(
                    """
          {
              "content": "This is a comment"
          }
          """))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(
      username = "user",
      roles = {"ADMIN"})
  void shouldAllowAdminUserToCreateComment() throws Exception {
    var uuid = UUID.randomUUID();
    mockMvc
        .perform(
            post("/api/comments")
                .header("Content-Type", "application/json")
                .content(
                    """
          {
              "content": "This is a comment"
          }
          """))
        .andExpect(status().is(200));
  }

  @Test
  @WithMockUser(
      username = "user",
      roles = {"ADMIN"})
  void shouldNotAllowToCreateCommentWithBlankContent() throws Exception {
    mockMvc
        .perform(
            post("/api/comments")
                .header("Content-Type", "application/json")
                .content(
                    """
          {
              "content": ""
          }
          """))
        .andExpect(status().is(400));
  }
}
