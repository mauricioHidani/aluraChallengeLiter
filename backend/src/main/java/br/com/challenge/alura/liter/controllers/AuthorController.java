package br.com.challenge.alura.liter.controllers;

import br.com.challenge.alura.liter.models.entites.Author;
import br.com.challenge.alura.liter.services.AuthorService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> findAll(Pageable pageable) {
        final var result = authorService.findAll(pageable);
        final var nextPageUrl = result.hasNext() ?
                ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/v1/authors")
                    .queryParam("page", result.getNumber() +1)
                    .queryParam("size", "10")
                    .toUriString()
                : null;
        return ResponseEntity.ok()
                .header("X-Next-Page", nextPageUrl != null ?  nextPageUrl : "")
                .body(result.getContent());
    }

    @GetMapping("/alive")
    public ResponseEntity<List<Author>> findAliveAfterBy(@RequestParam Integer after,
                                                         Pageable pageable) {
        final var result = authorService.findAliveAfterBy(after, pageable);
        final var path = "/v1/authors";
        final var nextPageUrl = result.hasNext() ?
                ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path + "/alive")
                    .queryParam("page", result.getNumber() +1)
                    .queryParam("size", "10")
                    .toUriString()
                : ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(path)
                    .queryParam("page", "0")
                    .queryParam("size", "10")
                    .toUriString();
        return ResponseEntity.ok()
                .header("X-Next-Page", nextPageUrl != null ?  nextPageUrl : "")
                .body(result.getContent());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Author> findByName(@PathVariable String name) {
        final var result = authorService.findByName(name);
        final var collectionHeaderUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/authors")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .toUriString();
        return ResponseEntity.ok()
                .header("Collection", collectionHeaderUri)
                .body(result);
    }

}
