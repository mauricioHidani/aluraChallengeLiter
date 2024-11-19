package br.com.challenge.alura.liter.models.converters;

import br.com.challenge.alura.liter.models.entites.Book;
import br.com.challenge.alura.liter.models.transfers.GutendexResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {

    private final AuthorConverter authorConverter;
    private final LanguageConverter languageConverter;

    public BookConverter(AuthorConverter authorConverter,
                         LanguageConverter languageConverter) {
        this.authorConverter = authorConverter;
        this.languageConverter = languageConverter;
    }

    public List<Book> convert(GutendexResponse gutendex) {
        if (gutendex == null || gutendex.results().isEmpty()) {
            throw new IllegalArgumentException(
                "Não é possivel realizar o processamento das informações."
            );
        }

        final var results = gutendex.results();
        return results.stream().map(e -> new Book(
            e.title(),
            authorConverter.convert(e.authors().getFirst()),
            e.formats().imageUrl(),
            languageConverter.convert(e.languages()),
            e.downloadCount()
        )).collect(Collectors.toList());
    }

}
