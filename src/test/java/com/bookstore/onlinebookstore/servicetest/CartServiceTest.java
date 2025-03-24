package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.exceptions.ResourceNotFoundException;
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
    private BookRepository bookRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void getOrCreateUserCart_whenCartExists_returnsExistingCart() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Cart existingCart = new Cart();
        existingCart.setUser(user);

        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(existingCart));

        // Act
        Cart result = cartService.getOrCreateUserCart(user);

        // Assert
        assertNotNull(result);
        assertEquals(existingCart, result);
        verify(cartRepository).findByUserId(user.getId());
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void getOrCreateUserCart_whenCartDoesNotExist_createsNewCart() {
        // Arrange
        User user = new User();
        user.setId(1L);

        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        Cart newCart = new Cart();
        newCart.setUser(user);
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);

        // Act
        Cart result = cartService.getOrCreateUserCart(user);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.getUser());
        verify(cartRepository).findByUserId(user.getId());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void addBookToCart_whenCartNotFound_throwsException() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> cartService.addBookToCart(userId, bookId, 1));
        verify(cartRepository).findByUserId(userId);
        verify(bookRepository, never()).findById(bookId);
    }

    @Test
    void addBookToCart_whenBookNotFound_throwsException() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;

        Cart cart = new Cart();
        cart.setId(1L);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> cartService.addBookToCart(userId, bookId, 1));
        verify(bookRepository).findById(bookId);
    }

    @Test
    void addBookToCart_whenBookExistsInCart_incrementsQuantity() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;

        Book book = new Book();
        book.setId(bookId);

        CartItem existingItem = new CartItem();
        existingItem.setBook(book);
        existingItem.setQuantity(2);

        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.getItems().add(existingItem);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.save(cart)).thenReturn(cart);

        // Act
        Cart result = cartService.addBookToCart(userId, bookId, 3);

        // Assert
        assertEquals(1, result.getItems().size());
        assertEquals(5, result.getItems().get(0).getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void addBookToCart_whenBookNotInCart_addsNewItem() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;

        Book book = new Book();
        book.setId(bookId);

        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Cart result = cartService.addBookToCart(userId, bookId, 2);

        // Assert
        assertEquals(1, result.getItems().size());
        assertEquals(2, result.getItems().get(0).getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeBookFromCart_removesItem() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;

        Book book = new Book();
        book.setId(bookId);

        CartItem item = new CartItem();
        item.setBook(book);

        Cart cart = new Cart();
        cart.getItems().add(item);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Cart result = cartService.removeBookFromCart(userId, bookId);

        // Assert
        assertTrue(result.getItems().isEmpty());
        verify(cartRepository).save(cart);
    }

    @Test
    void clearCart_clearsItems() {
        // Arrange
        Long userId = 1L;
        Cart cart = new Cart();
        cart.getItems().add(new CartItem());

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Cart result = cartService.clearCart(userId);

        // Assert
        assertTrue(result.getItems().isEmpty());
        verify(cartRepository).save(cart);
    }
}
