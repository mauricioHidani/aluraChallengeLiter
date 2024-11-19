package br.com.challenge.alura.liter.models.transfers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record ExceptionHandlerResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
