package com.example.balancing.controllers.web;

import com.example.balancing.controllers.api.ApiAuthenticationController;
import com.example.balancing.payloads.requests.SignInRequest;
import com.example.balancing.payloads.requests.SignUpRequest;
import com.example.balancing.payloads.responses.AuthenticationResponse;
import com.example.balancing.services.auth.AuthenticationService;
import com.example.balancing.services.cookie.CookieHttpOnlyService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiAuthenticationController.class)
@AutoConfigureMockMvc
public class WebAuthenticationController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private CookieHttpOnlyService cookieService;

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
    public void testLogin_Success() throws Exception {

        AuthenticationResponse authResponse =
                new AuthenticationResponse("accessToken", "refreshToken");

        // Мокируем поведение сервиса аутентификации
        when(authenticationService.signIn(any(SignInRequest.class))).thenReturn(authResponse);

        // Выполнение запроса
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signInRequest)))
                .andExpect(status().isOk())  // Проверка статуса ответа 200
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));

        // Проверка, что токены были добавлены в cookies
        verify(cookieService, times(1)).addAccessTokenCookie(any(HttpServletResponse.class), eq("accessToken"));
        verify(cookieService, times(1)).addRefreshTokenCookie(any(HttpServletResponse.class), eq("refreshToken"));

        // Проверка, что метод signIn был вызван с правильным запросом
        verify(authenticationService, times(1)).signIn(any(SignInRequest.class));
    }

    // Вспомогательный метод для преобразования объекта в JSON-строку
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
