package br.com.challenge.alura.liter.models.entites;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String language;

    public Language() {
    }

    public Language(String language) {
        this.language = language;
    }

    public Language(Long id, String language) {
        this.id = id;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language1 = (Language) o;
        return Objects.equals(language, language1.language);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(language);
    }
}
