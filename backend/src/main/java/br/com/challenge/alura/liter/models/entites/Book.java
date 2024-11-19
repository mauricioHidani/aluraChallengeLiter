package br.com.challenge.alura.liter.models.entites;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER,
               cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "author_id")
    private Author author;

    @Column
    private String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "books_has_languages",
               joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "language_id"))
    private Set<Language> languages = new HashSet<>();

    @Column
    private Long downloadCount;

    public Book() {
    }

    public Book(String title,
                Author author,
                String imageUrl,
                Set<Language> languages,
                Long downloadCount) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.languages = languages;
        this.downloadCount = downloadCount;
    }

    public Book(Long id,
                String title,
                Author author,
                String imageUrl,
                Set<Language> languages,
                Long downloadCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.languages = languages;
        this.downloadCount = downloadCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void addLanguage(Language language) {
        this.languages.add(language);
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

}
