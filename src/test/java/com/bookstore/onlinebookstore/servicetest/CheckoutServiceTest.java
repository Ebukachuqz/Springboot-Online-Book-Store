package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.model.*;
import com.bookstore.onlinebookstore.service.CartService;
import com.bookstore.onlinebookstore.service.CheckoutService;
import com.bookstore.onlinebookstore.service.OrderService;
import com.bookstore.onlinebookstore.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    @Mock
    private CartService cartService;

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CheckoutService checkoutService;

    @Test
    void checkout_withNonEmptyCart_createsOrderAndClearsCart() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(2L);
        book.setPrice(10.0);

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(2);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.getItems().add(cartItem);

        Order order = new Order();
        order.setItems(new ArrayList<>());

        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.COMPLETED);

        when(cartService.getOrCreateUserCart(user)).thenReturn(cart);
        when(orderService.createOrder(user)).thenReturn(order);
        when(paymentService.processPayment(20.0, PaymentMethod.WEB)).thenReturn(payment);
        when(orderService.saveOrder(any(Order.class))).thenAnswer(inv -> {
            Order savedOrder = inv.getArgument(0);
            savedOrder.setId(10L);
            return savedOrder;
        });

        // Act
        Order result = checkoutService.checkout(user, PaymentMethod.WEB);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(OrderStatus.PAID, result.getStatus());
        assertEquals(1, result.getItems().size());

        verify(cartService).clearCart(user.getId());
    }

    @Test
    void checkout_withEmptyCart_throwsException() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());

        when(cartService.getOrCreateUserCart(user)).thenReturn(cart);

        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
                checkoutService.checkout(user, PaymentMethod.WEB));
        verify(orderService, never()).createOrder(any(User.class));
    }

    @Test
    void checkoutFallback_returnsCancelledOrder() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Exception exception = new RuntimeException("Simulated failure");

        // Act
        Order fallbackOrder = checkoutService.checkoutFallback(user, exception);

        // Assert
        assertNotNull(fallbackOrder);
        assertEquals(OrderStatus.CANCELLED, fallbackOrder.getStatus());
        assertEquals(user, fallbackOrder.getUser());
    }
}
