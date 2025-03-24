package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.CartItem;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.BookRepository;
import com.bookstore.onlinebookstore.repository.CartRepository;
import com.bookstore.onlinebookstore.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void addToCart_whenItemNotInCart_addsNewItem() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;

        User user = new User();
        user.setId(userId);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setItems(new ArrayList<>());

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Test Book");
        book.setPrice(10.0);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Cart result = cartService.addBookToCart(userId, bookId, 1);

        // Assert
        assertEquals(1, result.getItems().size());
        assertEquals(bookId, result.getItems().get(0).getBook().getId());
        assertEquals(1, result.getItems().get(0).getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeFromCart_removesItemFromCart() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        CartItem item = new CartItem();
        item.setBook(book);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.getItems().add(item);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Cart result = cartService.removeBookFromCart(userId, bookId);

        // Assert
        assertTrue(result.getItems().isEmpty());
        verify(cartRepository).save(cart);
    }
}
