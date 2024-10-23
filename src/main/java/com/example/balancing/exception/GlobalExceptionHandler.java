package com.example.balancing.exception;

import com.example.balancing.exception.auth.AuthException;
import com.example.balancing.exception.auth.PermissionException;
import com.example.balancing.exception.auth.WrongRequestException;
import com.example.balancing.exception.token.RefreshTokenExpiredException;
import com.example.balancing.exception.token.RefreshTokenNotFoundException;
import com.example.balancing.exception.user.UserAlreadyExistedException;
import com.example.balancing.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.balancing.payloads.responses.ErrorResponse;

/**
 * GlobalExceptionHandler — это глобальный обработчик исключений, который
 * перехватывает различные исключения в приложении и возвращает соответствующие
 * HTTP-ответы с информацией об ошибке и статусами.
 *
 * <p>Этот класс использует аннотацию {@link RestControllerAdvice} для глобального
 * перехвата исключений, выбрасываемых в контроллерах. Каждое исключение
 * обрабатывается соответствующим методом, который возвращает объект
 * {@link ResponseEntity}, содержащий информацию об ошибке в виде
 * {@link ErrorResponse} и HTTP-статус.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> catchUserNotFoundException
            (UserNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistedException.class)
    public ResponseEntity<ErrorResponse> catchUserAlreadyExistedException
            (UserAlreadyExistedException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage()), HttpStatus.BAD_REQUEST);
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
