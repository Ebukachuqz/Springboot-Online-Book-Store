package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Genre;
import com.bookstore.onlinebookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getAllBooks_returnsBooks() {
        // Arrange
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookService.getAllBooks()).thenReturn(books);

        // Act
        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        // Assert
        assertNotNull(response);
        assertEquals(books, response.getBody());
        verify(bookService).getAllBooks();
    }

    @Test
    void getBookById_returnsBook() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        when(bookService.getBookById(bookId)).thenReturn(book);

        // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(book, response.getBody());
        verify(bookService).getBookById(bookId);
    }

    @Test
    void searchBooks_returnsMatchingBooks() {
        // Arrange
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookService.searchBooks("title", "author", 2023, Genre.FICTION)).thenReturn(books);

        // Act
        ResponseEntity<List<Book>> response = bookController.searchBooks("title", "author", 2023, Genre.FICTION);

        // Assert
        assertNotNull(response);
        assertEquals(books, response.getBody());
        verify(bookService).searchBooks("title", "author", 2023, Genre.FICTION);
    }
}

