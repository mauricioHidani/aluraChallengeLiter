package br.com.challenge.alura.liter.models.transfers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexAuthorResponse(
        @JsonProperty String name,
        @JsonProperty("birth_year") Integer birthYear,
        @JsonProperty("death_year") Integer deathYear
) {
}
