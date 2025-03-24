package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.service.CartService;
import com.bookstore.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(cartService.getOrCreateUserCart(user));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addToCart(
            @PathVariable Long userId,
            @RequestParam Long bookId,
            @RequestParam(defaultValue = "1") int quantity) {
        return ResponseEntity.ok(cartService.addBookToCart(userId, bookId, quantity));
    }

    @DeleteMapping("/{userId}/items/{bookId}")
    public ResponseEntity<Cart> removeFromCart(
            @PathVariable Long userId,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(cartService.removeBookFromCart(userId, bookId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
}
