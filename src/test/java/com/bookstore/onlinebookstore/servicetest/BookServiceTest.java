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
import static org.mockito.ArgumentMatchers.any;
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
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(1L);

        // Assert
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookById_whenBookDoesNotExist_throwsException() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository).findById(1L);
    }

    @Test
    void searchBooks_returnsMatchingBooks() {
        // Arrange
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        when(bookRepository.searchBooks(any(), any(), any(), any())).thenReturn(expectedBooks);

        // Act
        List<Book> result = bookService.searchBooks("title", "author", 2023, Genre.FICTION);

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository).searchBooks("title", "author", 2023, Genre.FICTION);
    }
}
