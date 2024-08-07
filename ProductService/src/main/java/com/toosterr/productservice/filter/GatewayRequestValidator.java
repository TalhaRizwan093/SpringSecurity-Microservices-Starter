package com.toosterr.productservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toosterr.productservice.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class GatewayRequestValidator extends OncePerRequestFilter {

    private final JwtUtil jwtTokenService;
    private final MessageSource messageSource;

    public GatewayRequestValidator(JwtUtil jwtTokenProvider, MessageSource messageSource) {
        this.jwtTokenService = jwtTokenProvider;
        this.messageSource = messageSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenService.resolveToken(request);
            jwtTokenService.validateToken(token);

        } catch (Exception ex) {
            String message = messageSource.getMessage("intruder.exception", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
            var responseData = new ResponseEntity<>(ErrorResponse.create(ex, HttpStatus.UNAUTHORIZED, message), HttpStatus.UNAUTHORIZED);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(responseData.getBody());
            response.getWriter().write(jsonResponse);
            return;
        }
        filterChain.doFilter(request, response);
    }

}
