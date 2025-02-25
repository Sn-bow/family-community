package com.lol.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;



@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUserRegistration() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .param("email", "testuser@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                // 응답 토큰 또는 메시지가 포함되었는지 확인 (예: 토큰 문자열의 일부가 포함되었는지)
                .andExpect(content().string(containsString("eyJ")));
    }

    @Test
    public void testUserDelete() throws Exception {
        mockMvc.perform(delete("/api/auth/delete")
                        .param("email", "testuser@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserLogin() throws Exception {
        // 먼저 회원가입
        mockMvc.perform(post("/api/auth/register")
                        .param("email", "loginuser@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk());

        // 그 후 로그인 테스트
        mockMvc.perform(post("/api/auth/login")
                        .param("email", "loginuser@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("eyJ")));

        // 회원탈퇴 진행 (원할한 테스트를 위해서;;)
        mockMvc.perform(delete("/api/auth/delete")
                        .param("email", "loginuser@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserDeletion() throws Exception {
        // 회원가입 진행
        mockMvc.perform(post("/api/auth/register")
                        .param("email", "deleteuser@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk());

        // 회원탈퇴 진행
        mockMvc.perform(delete("/api/auth/delete")
                        .param("email", "deleteuser@example.com"))
                .andExpect(status().isOk());
    }
}
