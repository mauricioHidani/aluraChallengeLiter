package br.com.challenge.alura.liter.models.transfers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse(
        @JsonProperty("results") List<GutendexResultResponse> results
) {
}
