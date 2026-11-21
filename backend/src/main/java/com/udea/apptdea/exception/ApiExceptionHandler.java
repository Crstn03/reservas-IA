package com.udea.apptdea.exception;

import com.udea.apptdea.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<ApiError> conflict(ReservationConflictException ex, HttpServletRequest req) {
        return response(HttpStatus.CONFLICT, ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> integrityConflict(DataIntegrityViolationException ex, HttpServletRequest req) {
        return response(HttpStatus.CONFLICT, "Ya existe una reserva activa para esa fecha y hora.", req, Map.of());
    }
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ReservationNotFoundException ex, HttpServletRequest req) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage(), req, Map.of());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalid(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String,String> fields = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
            FieldError::getField, e -> e.getDefaultMessage() == null ? "Valor inválido" : e.getDefaultMessage(), (a,b) -> a));
        return response(HttpStatus.BAD_REQUEST, "Revisa los campos enviados.", req, fields);
    }
    private ResponseEntity<ApiError> response(HttpStatus status, String message, HttpServletRequest req, Map<String,String> fields) {
        return ResponseEntity.status(status).body(new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, req.getRequestURI(), fields));
    }
}
