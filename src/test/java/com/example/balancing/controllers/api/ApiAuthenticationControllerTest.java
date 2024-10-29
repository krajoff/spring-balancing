package com.example.balancing.controllers.api;

import com.example.balancing.config.TestSecurityConfig;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.tokens.access.AccessTokenService;
import com.example.balancing.services.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestSecurityConfig.class)
@WebMvcTest(ApiAuthenticationController.class)
class ApiAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private AccessTokenService accessTokenService;

    @MockBean
    private UserService userService;

    private SignUpRequest validSignUpRequest;
    private SignUpRequest invalidSignUpRequest;

    private SignInRequest validSignInRequest;
    private SignInRequest invalidSignInRequest;

    @BeforeEach
    void setup() {

        validSignUpRequest = new SignUpRequest();
        validSignUpRequest.setUsername("username");
        validSignUpRequest.setEmail("username@mail.com");
        validSignUpRequest.setPassword("password");

        invalidSignUpRequest = new SignUpRequest();
        invalidSignUpRequest.setUsername("user");
        invalidSignUpRequest.setEmail("wrongEmailPattern");
        invalidSignUpRequest.setPassword("sm");

        validSignInRequest = new SignInRequest();
        validSignInRequest.setUsername("username");
        validSignInRequest.setPassword("password");

        Mockito.when(authenticationService.signIn(Mockito.any(SignInRequest.class)))
                .thenReturn(new AuthenticationResponse(
                        "accessToken", "refreshToken"));

        Mockito.when(authenticationService.signUp(Mockito.any(SignUpRequest.class)))
                .thenReturn(new AuthenticationResponse(
                        "accessToken", "refreshToken"));

    }

    @Test
    void testValidSignUpRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'accessToken':'accessToken', " +
                                "'refreshToken':'refreshToken'}"));
    }

    @Test
    void testInvalidSignUpRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "{'username':'[Username] Минимальная длина — 5 символов, максимальная — 30',"
                                + "'email':'[Email] Формат должен соответствовать паттерну user@example.com'," +
                                "'password': '[Password] Минимальная длина пароля — 5 символов, максимальная — 255'}"));
    }

    @Test
    void testValidSignInRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignInRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{'accessToken':'accessToken', " +
                                "'refreshToken':'refreshToken'}"));
    }

    @Test
    void testInvalidLoginRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignInRequest)))
                .andExpect(status().isBadRequest());
    }

}
