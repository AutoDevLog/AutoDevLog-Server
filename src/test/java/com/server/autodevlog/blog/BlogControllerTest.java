package com.server.autodevlog.blog;

import com.google.gson.Gson;
import com.server.autodevlog.blog.controller.BlogController;
import com.server.autodevlog.blog.dto.VelogPostRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Gson gson;

    @Test
    public void 벨로그_포스트_API_호출() throws Exception {
        // Given
        VelogPostRequestDto velogPostRequestDto = new VelogPostRequestDto("title", "content", "token");

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/velog-post")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(velogPostRequestDto))
        );

        // Then
        resultActions.andExpect(status().isCreated());
    }

}
