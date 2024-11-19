package br.com.challenge.alura.liter.models.converters;

import br.com.challenge.alura.liter.models.entites.Author;
import br.com.challenge.alura.liter.models.transfers.GutendexAuthorResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorConverter {

    public Author convert(GutendexAuthorResponse author) {
        return new Author(
            author.name(),
            author.birthYear(),
            author.deathYear()
        );
    }

    public List<Author> convert(List<GutendexAuthorResponse> authors) {
        return authors.stream().map(e -> new Author(
            e.name(),
            e.birthYear(),
            e.deathYear()
        )).collect(Collectors.toList());
    }

}
