package br.com.challenge.alura.liter.handlers;

import br.com.challenge.alura.liter.exceptions.NotFoundException;
import br.com.challenge.alura.liter.models.transfers.ExceptionHandlerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;
import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> notFoundException(NotFoundException exception,
                                                                      HttpServletRequest request) {
        final var status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(buildResponse(exception, request, status));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ExceptionHandlerResponse> illegalArgumentException(IllegalArgumentException exception,
                                                                             HttpServletRequest request) {
        final var status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity
                .status(status)
                .body(buildResponse(exception, request, status));
    }

    private ExceptionHandlerResponse buildResponse(RuntimeException exception,
                                        HttpServletRequest request,
                                        HttpStatus status) {
        return new ExceptionHandlerResponse(
                Instant.now(Clock.systemUTC()),
                status.value(),
                status.name(),
                exception.getMessage(),
                request.getRequestURI()
        );
    }
}
