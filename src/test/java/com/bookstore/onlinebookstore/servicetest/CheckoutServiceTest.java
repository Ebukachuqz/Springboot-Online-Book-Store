package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.model.*;
import com.bookstore.onlinebookstore.repository.CartRepository;
import com.bookstore.onlinebookstore.repository.OrderRepository;
import com.bookstore.onlinebookstore.service.CheckoutService;
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
class CheckoutServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CheckoutService checkoutService;

    @Test
    void checkout_withItemsInCart_createsOrderAndClearsCart() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setPrice(10.0);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(2);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.getItems().add(cartItem);

        // Assuming getOrCreateUserCart is called and returns our cart
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> {
            Order order = i.getArgument(0);
            order.setId(1L);
            return order;
        });
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // Act
        Order result = checkoutService.checkout(user, PaymentMethod.WEB);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getUser().getId());
        // You may also assert that payment is set if PaymentStatus is COMPLETED
        assertEquals(OrderStatus.PAID, result.getStatus());
        assertEquals(1, result.getItems().size());
        assertTrue(cart.getItems().isEmpty());

        verify(orderRepository).save(any(Order.class));
        verify(cartRepository).save(cart);
    }

    @Test
    void checkout_withEmptyCart_throwsException() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());

        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));

        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
                checkoutService.checkout(user, PaymentMethod.WEB)
        );

        verify(orderRepository, never()).save(any());
    }
}
