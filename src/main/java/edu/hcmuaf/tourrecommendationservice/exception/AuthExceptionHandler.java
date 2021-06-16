package edu.hcmuaf.tourrecommendationservice.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class AuthExceptionHandler {

    @ExceptionHandler({DisabledException.class, BadCredentialsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage authenticateException(Exception ex, WebRequest request) {
        log.info(ex.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage signupException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ErrorMessage(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({JwtException.class, TokenInvalidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage jwtException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
    }



}
