package br.com.challenge.alura.liter.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class GutendexService {

    @Value("${application.service.gutendex.url}")
    private String baseUrl;

    private final ObjectMapper mapper;

    public GutendexService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> Optional<T> findByTitle(String title, Class<T> clazz) {
        try {
            final var path = baseUrl + "/books/?search=" + title.replaceAll(" ", "%20");
            final var client = HttpClient.newHttpClient();
            final var request = HttpRequest.newBuilder().uri(URI.create(path)).build();
            final var responseBody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            return Optional.of( parse(responseBody, clazz) );

        } catch (UncheckedIOException | IOException e) {
            throw new RuntimeException("");
        } catch (InterruptedException e) {
            throw new RuntimeException("");
        }
    }

    protected <T> T parse(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("");
        }
    }

}
