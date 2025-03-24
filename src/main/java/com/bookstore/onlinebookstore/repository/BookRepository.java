package com.bookstore.onlinebookstore.repository;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByPublicationYear(int year);
    List<Book> findByGenre(Genre genre);

    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
            "(:year IS NULL OR b.publicationYear = :year) AND " +
            "(:genre IS NULL OR b.genre = :genre)")
    List<Book> searchBooks(
            @Param("title") String title,
            @Param("author") String author,
            @Param("year") Integer year,
            @Param("genre") Genre genre);
}
