package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.exceptions.ResourceNotFoundException;
import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Genre;
import com.bookstore.onlinebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String title, String author, Integer year, Genre genre) {
        return bookRepository.searchBooks(title, author, year, genre);
    }
}
