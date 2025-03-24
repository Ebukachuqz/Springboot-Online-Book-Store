package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.CartItem;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.BookRepository;
import com.bookstore.onlinebookstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    @Transactional
    public Cart getOrCreateCart(User user){
        return cartRepository.findByUserId(user.getId()).orElseGet(()->{
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public Cart addToCart(Long userId, Long bookId, int quantity){
        Cart userCart = cartRepository.
                findByUserId(userId)
                .orElseThrow(()->new RuntimeException("User not found")); // !TODO: modify this exception

        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(()->new RuntimeException("Book not found")); // !TODO: modify this exception

        // check if book already exists in Cart
        CartItem existingCartItem = userCart.getItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setCart(userCart);
            newCartItem.setQuantity(quantity);
            userCart.getItems().add(newCartItem);
        }

        return cartRepository.save(userCart);

    }

    @Transactional
    public Cart removeFromCart(Long userId, Long bookId){
        Cart userCart = cartRepository
                .findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("Cart not found for user with id: " + userId)); // !TODO: modify this exception

        userCart.getItems().removeIf(item -> item.getBook().getId().equals(bookId));
        return cartRepository.save(userCart);
    }

    @Transactional
    public Cart clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: " + userId)); // !TODO: modify this exception

        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
