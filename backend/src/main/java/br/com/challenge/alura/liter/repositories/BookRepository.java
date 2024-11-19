package br.com.challenge.alura.liter.repositories;

import br.com.challenge.alura.liter.models.entites.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleLikeIgnoreCase(String title);

    Page<Book> findByLanguages_Language(String language, Pageable pageable);

    List<Book> findTop10ByOrderByDownloadCountDesc();

    @Query("SELECT COUNT(b) FROM Book b JOIN b.languages l WHERE l.language = :language")
    Long countByLanguage(@Param("language") String language);

    @Query("SELECT AVG(b.downloadCount) FROM Book b")
    Long averageDownloadCount();

}
