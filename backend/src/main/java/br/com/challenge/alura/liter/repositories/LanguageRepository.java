package br.com.challenge.alura.liter.repositories;

import br.com.challenge.alura.liter.models.entites.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByLanguage(String language);

}
