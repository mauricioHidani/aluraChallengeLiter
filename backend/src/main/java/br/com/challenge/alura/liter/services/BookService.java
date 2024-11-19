package br.com.challenge.alura.liter.services;

import br.com.challenge.alura.liter.exceptions.NotFoundException;
import br.com.challenge.alura.liter.models.converters.BookConverter;
import br.com.challenge.alura.liter.models.entites.Book;
import br.com.challenge.alura.liter.models.transfers.BookStatisticResponse;
import br.com.challenge.alura.liter.models.transfers.BookTop10Response;
import br.com.challenge.alura.liter.models.transfers.GutendexResponse;
import br.com.challenge.alura.liter.models.transfers.LanguageRequest;
import br.com.challenge.alura.liter.repositories.AuthorRepository;
import br.com.challenge.alura.liter.repositories.BookRepository;
import br.com.challenge.alura.liter.repositories.LanguageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LanguageRepository languageRepository;
    private final GutendexService gutendexService;
    private final BookConverter bookConverter;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       LanguageRepository languageRepository,
                       GutendexService gutendexService,
                       BookConverter bookConverter) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.languageRepository = languageRepository;
        this.gutendexService = gutendexService;
        this.bookConverter = bookConverter;
    }

    public List<Book> findByTitle(String title) {
        final var founded = bookRepository.findByTitleLikeIgnoreCase(title);
        if (founded.isEmpty()) {
            final var gutendexFounded = gutendexService.findByTitle(title, GutendexResponse.class);

            if (gutendexFounded.isEmpty() || gutendexFounded.get().results().isEmpty()) {
                throw new NotFoundException(
                    "Não foi encontrado o livro com o titulo indicado."
                );
            }

            var books = bookConverter.convert(gutendexFounded.get());
            return books.stream().map(e -> {
                saveLanguages(e);
                saveAuthor(e);

                return bookRepository.save(e);
            }).collect(Collectors.toList());
        }

        return founded;
    }

    public Page<BookStatisticResponse> findAll(Pageable pageable) {
        final Page<Book> founded = bookRepository.findAll(pageable);
        if (founded.isEmpty()) {
            throw new NotFoundException(
                "Não foram encontrados livros cadastrados"
            );
        }
        final var averageDownloadCount = bookRepository.averageDownloadCount();

        return founded.map(e -> new BookStatisticResponse(
            e.getId(),
            e.getTitle(),
            e.getAuthor(),
            e.getImageUrl(),
            e.getLanguages(),
            e.getDownloadCount(),
            averageDownloadCount,
            e.getDownloadCount() > averageDownloadCount
        ));
    }

    public Page<Book> findByLanguage(String language, Pageable pageable) {
        final var targetLanguage = findTargetLanguage(language);
        final var founded = bookRepository.findByLanguages_Language(targetLanguage, pageable);
        if (founded.isEmpty()) {
            throw new NotFoundException(
                "Não foi encontrado livros correspondentes com a linguagem selecionada."
            );
        }

        return founded;
    }

    public Long countByLanguage(String language) {
        final var targetLanguage = findTargetLanguage(language);
        final var result = bookRepository.countByLanguage(targetLanguage);
        if (result == 0) {
            throw new NotFoundException(
                "Não foram encontrado livros correspondentes a linguagem indicada."
            );
        }

        return result;
    }

    public BookTop10Response findTop10() {
        final var founded = bookRepository.findTop10ByOrderByDownloadCountDesc();
        if (founded.isEmpty()) {
            throw new NotFoundException(
                "Não foram encontrados os top 10 livros."
            );
        }
        final var statistical = founded.stream()
                .filter(e -> e.getDownloadCount() > 0)
                .collect(Collectors.summarizingLong(Book::getDownloadCount));

        return new BookTop10Response(
            statistical.getAverage(),
            statistical.getMax(),
            statistical.getMin(),
            statistical.getCount(),
            founded
        );
    }

    private void saveAuthor(Book book) {
        final var authorFounded = authorRepository.findByName(book.getAuthor().getName());
        if (authorFounded.isEmpty()) {
            var authorSaved = authorRepository.save(book.getAuthor());
            book.setAuthor(authorSaved);
        } else {
            book.setAuthor(authorFounded.get());
        }
    }

    private void saveLanguages(Book book) {
        book.getLanguages().forEach(l -> {
            final var languageFounded = languageRepository.findByLanguage(l.getLanguage());
            if (languageFounded.isEmpty()) {
                final var languageSave = languageRepository.save(l);
                book.addLanguage(languageSave);
            } else {
                final var sameLang = book.getLanguages().stream()
                        .filter(f -> f.getLanguage().equalsIgnoreCase(languageFounded.get().getLanguage()))
                        .collect(Collectors.toSet());
                book.getLanguages().removeAll(sameLang);
                book.addLanguage(languageFounded.get());
            }
        });
    }

    private String findTargetLanguage(String language) {
        for (LanguageRequest lang : LanguageRequest.values()) {
            for (String languageGoal : lang.getLanguages()) {
                if (languageGoal.equalsIgnoreCase(language)) {
                    return lang.getTarget();
                }
            }
        }
        return LanguageRequest.EN.getTarget();
    }

}
