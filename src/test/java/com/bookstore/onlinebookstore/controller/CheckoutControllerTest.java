package com.bookstore.onlinebookstore.controller;

import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.PaymentMethod;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.service.CheckoutService;
import com.bookstore.onlinebookstore.service.OrderService;
import com.bookstore.onlinebookstore.service.UserService;
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
class CheckoutControllerTest {

    @Mock
    private CheckoutService checkoutService;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CheckoutController checkoutController;

    @Test
    void checkout_returnsOrder() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Order order = new Order();
        order.setUser(user);
        // Assuming the checkout service sets payment method internally, here we pass PaymentMethod.WEB
        when(userService.getUserById(userId)).thenReturn(user);
        when(checkoutService.checkout(user, PaymentMethod.WEB)).thenReturn(order);

        // Act
        ResponseEntity<Order> response = checkoutController.checkout(userId, PaymentMethod.WEB);

        // Assert
        assertNotNull(response);
        assertEquals(order, response.getBody());
        verify(userService).getUserById(userId);
        verify(checkoutService).checkout(user, PaymentMethod.WEB);
    }

    @Test
    void getUserOrderHistory_returnsOrders() {
        // Arrange
        Long userId = 1L;
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderService.getUserOrderHistory(userId)).thenReturn(orders);

        // Act
        ResponseEntity<List<Order>> response = checkoutController.getUserOrderHistory(userId);

        // Assert
        assertNotNull(response);
        assertEquals(orders, response.getBody());
        verify(orderService).getUserOrderHistory(userId);
    }

    @Test
    void getOrderDetails_returnsOrder() {
        // Arrange
        Long userId = 1L;
        Long orderId = 2L;
        Order order = new Order();
        order.setId(orderId);
        when(orderService.getOrderById(orderId)).thenReturn(order);

        // Act
        ResponseEntity<Order> response = checkoutController.getOrderDetails(userId, orderId);

        // Assert
        assertNotNull(response);
        assertEquals(order, response.getBody());
        verify(orderService).getOrderById(orderId);
    }
}

