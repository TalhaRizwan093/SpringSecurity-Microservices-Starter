package com.toosterr.apigateway.exception;

import com.toosterr.apigateway.exception.user.UnauthorizedUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseStatusExceptionHandler {

    final MessageSource messageSource;

    public CustomExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUnauthorizedUser(UnauthorizedUser ex) {
        String message = messageSource.getMessage("authentication.exception", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return Mono.just(new ResponseEntity<>(ErrorResponse
                .create(ex, HttpStatus.UNAUTHORIZED, message), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(SignatureException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleSignatureException(SignatureException ex) {
        String message = messageSource.getMessage("jwt.signature.exception", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return Mono.just(new ResponseEntity<>(ErrorResponse
                .create(ex, HttpStatus.UNAUTHORIZED, message), HttpStatus.UNAUTHORIZED));
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleExpiredJwtException(ExpiredJwtException ex) {
        String message = messageSource.getMessage("jwt.expired.exception", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return Mono.just(new ResponseEntity<>(ErrorResponse
                .create(ex, HttpStatus.UNAUTHORIZED, message), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleMalformedJwtException(MalformedJwtException ex) {
        String message = messageSource.getMessage("jwt.malformed.exception", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return Mono.just(new ResponseEntity<>(ErrorResponse
                .builder(ex, HttpStatus.BAD_REQUEST, message)
                .title("JWT Exception")
                .build()
                , HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        String message = messageSource.getMessage("jwt.unsupported.exception", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return Mono.just(new ResponseEntity<>(ErrorResponse
                .create(ex, HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST));
    }


}
