package com.bookstore.onlinebookstore.service;

import com.bookstore.onlinebookstore.model.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Transactional
    @CircuitBreaker(name = "checkout", fallbackMethod = "checkoutFallback")
    public Order checkout(User user, PaymentMethod paymentMethod) {

        Cart cart = cartService.getOrCreateUserCart(user);

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot checkout with empty cart");
        }

        // Create a new order
        Order order = orderService.createOrder(user);

        // Copy items from cart to order and calculate total amount
        double totalAmount = 0.0;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getBook().getPrice());
            order.getItems().add(orderItem);

            totalAmount += cartItem.getBook().getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(totalAmount);

        // Process payment
        Payment payment = processPayment(user, totalAmount, paymentMethod);
        order.setPayment(payment);

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            order.setStatus(OrderStatus.PAID);
        }

        Order savedOrder = orderService.saveOrder(order);

        cartService.clearCart(cart.getUser().getId());

        return savedOrder;
    }

    private Payment processPayment(User user, double amount, PaymentMethod paymentMethod) {
        return paymentService.processPayment(amount, paymentMethod);
    }

    // Fallback method for circuit breaker
    public Order checkoutFallback(User user, PaymentMethod paymentMethod, Exception e) {
        Order fallbackOrder = new Order();
        fallbackOrder.setStatus(OrderStatus.CANCELLED);
        fallbackOrder.setOrderDate(LocalDateTime.now());
        fallbackOrder.setUser(user);
        System.err.println("Checkout fallback triggered due to: " + e.getMessage());

        return fallbackOrder;
    }
}
