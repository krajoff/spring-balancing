package com.example.balancing.exception;

import com.example.balancing.exception.auth.AuthException;
import com.example.balancing.exception.auth.PermissionException;
import com.example.balancing.exception.auth.WrongRequestException;
import com.example.balancing.exception.token.RefreshTokenExpiredException;
import com.example.balancing.exception.user.UserAlreadyExistedException;
import com.example.balancing.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundElementException.class)
    public ResponseEntity<ErrorResponse> catchNotFoundException(NotFoundElementException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistedException.class)
    public ResponseEntity<ErrorResponse> catchUserAlreadyExistedException
            (UserAlreadyExistedException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> catchAuthException
            (AuthException e) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                        e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ErrorResponse> catchPermissionException
            (PermissionException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(WrongRequestException.class)
    public ResponseEntity<ErrorResponse> catchWrongRequestException(WrongRequestException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> catchRefreshTokenExpiredException
            (RefreshTokenExpiredException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                e.getMessage()), HttpStatus.FORBIDDEN);
    }

}
