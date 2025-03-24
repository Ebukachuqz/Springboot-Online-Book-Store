package com.bookstore.onlinebookstore.controllers;

import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.PaymentMethod;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.service.CheckoutService;
import com.bookstore.onlinebookstore.service.OrderService;
import com.bookstore.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<Order> checkout(
            @PathVariable Long userId,
            @RequestParam PaymentMethod paymentMethod) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(checkoutService.checkout(user, paymentMethod));
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Order>> getUserOrderHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrderHistory(userId));
    }

    @GetMapping("/orders/{userId}/{orderId}")
    public ResponseEntity<Order> getOrderDetails(
            @PathVariable Long userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
