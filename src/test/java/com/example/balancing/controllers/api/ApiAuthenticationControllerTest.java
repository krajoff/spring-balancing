package com.example.balancing.controllers.api;

import com.example.balancing.payloads.requests.SignUpRequest;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiAuthenticationController.class)
class ApiAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private SignUpRequest validSignUpRequest;
    private SignUpRequest invalidSignUpRequest;


    @MockBean
    private AccessTokenService accessTokenService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        validSignUpRequest = new SignUpRequest();
        validSignUpRequest.setUsername("validUser");
        validSignUpRequest.setEmail("validemail@example.com");
        validSignUpRequest.setPassword("validPassword");

        invalidSignUpRequest = new SignUpRequest();
        invalidSignUpRequest.setUsername("a");  // Invalid username (too short)
        invalidSignUpRequest.setEmail("invalidEmail");  // Invalid email
        invalidSignUpRequest.setPassword("123");  // Invalid password (too short)

        // You can mock the behavior of the authenticationService here if needed
        Mockito.when(authenticationService.signUp(Mockito.any(SignUpRequest.class)))
                .thenReturn(new AuthenticationResponse("token", "refreshToken"));
    }

    @Test
    void testValidSignUpRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{'accessToken':'token', 'refreshToken':'refreshToken'}"));
    }

    @Test
    void testInvalidSignUpRequest() throws Exception {
        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSignUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'username':'[Username] Минимальная длина — 5 символов, максимальная — 30',"
                        + "'email':'[Email] Формат должен соответствовать паттерну user@example.com'}"));
    }
}
