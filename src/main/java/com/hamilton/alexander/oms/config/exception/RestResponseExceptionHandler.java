package com.hamilton.alexander.oms.config.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

    // 400s

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorMap = new LinkedHashMap<String, Object>();

        errorMap.put("timestamp", Instant.now());
        errorMap.put("status", status.value());

        var fieldErrorMap = new HashMap<String, String>();
        ex.getFieldErrors().forEach(e -> fieldErrorMap.put(e.getField(), e.getDefaultMessage()));
        errorMap.put("errors", fieldErrorMap);

        errorMap.put("message", status.getReasonPhrase());
        errorMap.put("path", request.getRequestURI());

        return new ResponseEntity<Map<String, Object>>(errorMap, status);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorMessage> handlePropertyReferenceException(PropertyReferenceException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    // 403

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        LOG.warn("User '%s' attempted to access the protected URI: %s".formatted(jwt.getClaimAsString("preferred_username"),
                request.getRequestURI()));

        return buildResponse(HttpStatus.FORBIDDEN, "Insufficient privileges", request);
    }
    
    // helper method
    
    private ResponseEntity<ErrorMessage> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        var errMsg = new ErrorMessage(Instant.now().toString(), status.value(), status.getReasonPhrase(), message, request.getRequestURI());

        return new ResponseEntity<ErrorMessage>(errMsg, status);
    }

}
