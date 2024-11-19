package br.com.challenge.alura.liter.services;

import br.com.challenge.alura.liter.exceptions.NotFoundException;
import br.com.challenge.alura.liter.models.entites.Author;
import br.com.challenge.alura.liter.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<Author> findAll(Pageable pageable) {
        final var founded = authorRepository.findAll(pageable);
        if (founded.isEmpty()) {
            throw new NotFoundException(
                "Não foram encontrados Autores registrados."
            );
        }

        return founded;
    }

    public Page<Author> findAliveAfterBy(Integer year, Pageable pageable) {
        final var founded = authorRepository.findByDeathYearGreaterThan(year, pageable);
        if (founded.isEmpty()) {
            throw new NotFoundException(
                "Não foram encontrados autores vivos após a data informada."
            );
        }

        return founded;
    }

}
