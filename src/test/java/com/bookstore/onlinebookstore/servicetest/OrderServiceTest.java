package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.exceptions.ResourceNotFoundException;
import com.bookstore.onlinebookstore.model.Order;
import com.bookstore.onlinebookstore.repository.OrderRepository;
import com.bookstore.onlinebookstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void getUserOrderHistory_returnsUserOrders() {
        // Arrange
        Long userId = 1L;
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByUserIdOrderByOrderDateDesc(userId)).thenReturn(expectedOrders);

        // Act
        List<Order> result = orderService.getUserOrderHistory(userId);

        // Assert
        assertEquals(2, result.size());
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
}
