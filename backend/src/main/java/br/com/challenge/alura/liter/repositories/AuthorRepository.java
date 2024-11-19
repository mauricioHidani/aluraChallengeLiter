package br.com.challenge.alura.liter.repositories;

import br.com.challenge.alura.liter.models.entites.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Boolean existsByName(String name);

    Optional<Author> findByName(String name);

    Page<Author> findByDeathYearGreaterThan(Integer deathYear, Pageable pageable);

}
