package br.com.challenge.alura.liter.models.transfers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResultResponse(
        @JsonProperty("title") String title,
        @JsonProperty("authors") List<GutendexAuthorResponse> authors,
        @JsonProperty("languages") List<String> languages,
        @JsonProperty("formats") GutendexFormatResponse formats,
        @JsonProperty("download_count") Long downloadCount
) {
}
