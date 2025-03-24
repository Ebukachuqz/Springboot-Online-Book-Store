package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.exceptions.ResourceNotFoundException;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.model.OrderStatus;
import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.OrderRepository;
import com.bookstore.onlinebookstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getUserOrderHistory_returnsOrders() {
        // Arrange
        Long userId = 1L;
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByUserIdOrderByOrderDateDesc(userId)).thenReturn(expectedOrders);

        // Act
        List<Order> result = orderService.getUserOrderHistory(userId);

        // Assert
        assertEquals(expectedOrders.size(), result.size());
        verify(orderRepository).findByUserIdOrderByOrderDateDesc(userId);
    }

    @Test
    void getOrderById_whenOrderExists_returnsOrder() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        Order result = orderService.getOrderById(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepository).findById(orderId);
    }

    @Test
    void getOrderById_whenOrderDoesNotExist_throwsException() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(orderId));
        verify(orderRepository).findById(orderId);
    }

    @Test
    void createOrder_setsCorrectProperties() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Act
        Order order = orderService.createOrder(user);

        // Assert
        assertNotNull(order);
        assertEquals(user, order.getUser());
        assertNotNull(order.getOrderDate());
        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void saveOrder_returnsPersistedOrder() {
        // Arrange
        Order order = new Order();
        order.setUser(new User());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order savedOrder = orderService.saveOrder(order);

        // Assert
        assertNotNull(savedOrder);
        verify(orderRepository).save(order);
    }
}
