package br.com.challenge.alura.liter.models.transfers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexFormatResponse(
        @JsonProperty("image/jpeg") String imageUrl
) {
}
