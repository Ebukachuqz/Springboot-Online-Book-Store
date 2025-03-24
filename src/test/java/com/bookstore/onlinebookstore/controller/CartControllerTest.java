package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.service.CartService;
import com.bookstore.onlinebookstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CartController cartController;

    @Test
    void getCart_returnsCart() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Cart cart = new Cart();
        cart.setUser(user);

        when(userService.getUserById(userId)).thenReturn(user);
        when(cartService.getOrCreateUserCart(user)).thenReturn(cart);

        // Act
        ResponseEntity<Cart> response = cartController.getCart(userId);

        // Assert
        assertNotNull(response);
        assertEquals(cart, response.getBody());
        verify(userService).getUserById(userId);
        verify(cartService).getOrCreateUserCart(user);
    }

    @Test
    void addToCart_returnsUpdatedCart() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;
        int quantity = 3;
        Cart cart = new Cart();

        when(cartService.addBookToCart(userId, bookId, quantity)).thenReturn(cart);

        // Act
        ResponseEntity<Cart> response = cartController.addToCart(userId, bookId, quantity);

        // Assert
        assertNotNull(response);
        assertEquals(cart, response.getBody());
        verify(cartService).addBookToCart(userId, bookId, quantity);
    }

    @Test
    void removeFromCart_returnsUpdatedCart() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;
        Cart cart = new Cart();

        when(cartService.removeBookFromCart(userId, bookId)).thenReturn(cart);

        // Act
        ResponseEntity<Cart> response = cartController.removeFromCart(userId, bookId);

        // Assert
        assertNotNull(response);
        assertEquals(cart, response.getBody());
        verify(cartService).removeBookFromCart(userId, bookId);
    }

    @Test
    void clearCart_returnsUpdatedCart() {
        // Arrange
        Long userId = 1L;
        Cart cart = new Cart();

        when(cartService.clearCart(userId)).thenReturn(cart);

        // Act
        ResponseEntity<Cart> response = cartController.clearCart(userId);

        // Assert
        assertNotNull(response);
        assertEquals(cart, response.getBody());
        verify(cartService).clearCart(userId);
    }
}

