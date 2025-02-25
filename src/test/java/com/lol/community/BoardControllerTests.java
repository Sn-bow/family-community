package com.lol.community;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.security.test.context.support.WithMockUser;


@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testBoardCreation() throws Exception {
        // 파일이 있는 경우(이미지 업로드)
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "dummy image content".getBytes());

        mockMvc.perform(multipart("/api/board")
                        .file(file)
                        .param("title", "Test Board Title")
                        .param("content", "Test Board Content"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Board Title"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testBoardCreationAndUpdateWithImage() throws Exception {
        // 게시글 생성 시 이미지 포함 (가짜 파일 사용)
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "dummy image content".getBytes());
        MvcResult creationResult = mockMvc.perform(multipart("/api/board")
                        .file(file)
                        .param("title", "Original Title")
                        .param("content", "Original Content"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Original Title"))
                .andReturn();

        String creationResponse = creationResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(creationResponse);
        long boardId = jsonNode.get("id").asLong();

        // 새로운 이미지 파일로 업데이트
        MockMultipartFile newFile = new MockMultipartFile("file", "updated.jpg", "image/jpeg", "new dummy image content".getBytes());
        // PUT 요청은 multipart를 지원하기 위해 request builder를 재설정합니다.
        MvcResult updateResult = mockMvc.perform(multipart("/api/board/" + boardId)
                        .file(newFile)
                        .param("title", "Updated Title")
                        .param("content", "Updated Content")
                        .with(request -> {
                            request.setMethod("PUT");  // PUT 메서드로 변경
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testBoardDeletion() throws Exception {
        // 게시글 생성 (이미지 없이 텍스트만 전송)
        MvcResult result = mockMvc.perform(multipart("/api/board")
                        .param("title", "Board to Delete")
                        .param("content", "Content to be deleted"))
                .andExpect(status().isOk())
                .andReturn();

        // 생성된 게시글의 id 추출 (응답 JSON에 id 필드가 있다고 가정)
        String response = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        long boardId = jsonNode.get("id").asLong();

        // 삭제 엔드포인트: DELETE /api/board/{id}
        mockMvc.perform(delete("/api/board/" + boardId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testBoardDelet() throws Exception {
        // 삭제 엔드포인트: DELETE /api/board/{id}
        // 개별적으로 삭제 테스트 할 때 사용하는 코드!
        mockMvc.perform(delete("/api/board/" + "삭제할 board ID 찾아서 삭제 진행"))
                .andExpect(status().isOk());
    }
}
