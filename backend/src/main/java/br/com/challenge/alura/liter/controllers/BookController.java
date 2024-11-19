package br.com.challenge.alura.liter.controllers;

import br.com.challenge.alura.liter.models.entites.Book;
import br.com.challenge.alura.liter.models.transfers.BookStatisticResponse;
import br.com.challenge.alura.liter.models.transfers.BookTop10Response;
import br.com.challenge.alura.liter.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookStatisticResponse>> findAll(Pageable pageable) {
        final Page<BookStatisticResponse> result = bookService.findAll(pageable);
        final var nextPageUrl = result.hasNext() ?
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", result.getNumber() +1)
                    .toUriString()
                : null;
        return ResponseEntity.ok()
                .header("X-Next-Page", nextPageUrl != null ?  nextPageUrl : "")
                .body(result.getContent());
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> findByTitle(@PathVariable String title) {
        final var headerCollectionUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/books")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .toUriString();
        return ResponseEntity.ok()
                .header("Collection", headerCollectionUri)
                .body(bookService.findByTitle(title));
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<Book>> findByLanguage(@PathVariable String language,
                                                     Pageable pageable) {
        final var result = bookService.findByLanguage(language, pageable);
        final var nextPageUrl = result.hasNext() ?
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("page", result.getNumber() +1)
                    .toUriString()
                : null;
        return ResponseEntity.ok()
                .header("X-Next-Page", nextPageUrl != null ?  nextPageUrl : "")
                .body(result.getContent());
    }

    @GetMapping("/language/count/{language}")
    public ResponseEntity<Long> countByLanguage(@PathVariable String language) {
        final var result = bookService.countByLanguage(language);
        final var findLanguageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/books/languages/" + language)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .toUriString();
        return ResponseEntity.ok()
                .header("Collection", findLanguageUrl)
                .body(result);
    }

    @GetMapping("/top-ten")
    public ResponseEntity<BookTop10Response> findTop10() {
        final var result = bookService.findTop10();
        final var headerCollectionUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/books")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .toUriString();
        return ResponseEntity.ok()
                .header("Collection", headerCollectionUri)
                .body(result);
    }

}
