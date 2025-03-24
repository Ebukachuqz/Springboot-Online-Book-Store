package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.exceptions.ResourceNotFoundException;
import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Genre;
import com.bookstore.onlinebookstore.repository.BookRepository;
import com.bookstore.onlinebookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBookById_whenBookExists_returnsBook() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(bookId);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        verify(bookRepository).findById(bookId);
    }

    @Test
    void getBookById_whenBookDoesNotExist_throwsException() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(bookId));
        verify(bookRepository).findById(bookId);
    }

    @Test
    void getAllBooks_returnsAllBooks() {
        // Arrange
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    void searchBooks_returnsMatchedBooks() {
        // Arrange
        String title = "test";
        String author = "author";
        Integer year = 2023;
        Genre genre = Genre.FICTION;

        List<Book> matchedBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.searchBooks(title, author, year, genre)).thenReturn(matchedBooks);

        // Act
        List<Book> result = bookService.searchBooks(title, author, year, genre);

        // Assert
        assertEquals(matchedBooks.size(), result.size());
        verify(bookRepository).searchBooks(title, author, year, genre);
    }
}
