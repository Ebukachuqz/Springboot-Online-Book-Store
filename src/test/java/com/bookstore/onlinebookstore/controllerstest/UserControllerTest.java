package com.bookstore.onlinebookstore.controllerstest;

import com.bookstore.onlinebookstore.model.User;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_returnsNewUser() {
        // Arrange
        User request = new User();
        request.setUsername("john_doe");

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setUsername("john_doe");

        when(userService.createUser("john_doe")).thenReturn(createdUser);

        // Act
        ResponseEntity<User> response = userController.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals(createdUser, response.getBody());
        verify(userService).createUser("john_doe");
    }

    @Test
    void getUserByUsername_returnsUser() {
        // Arrange
        String username = "john_doe";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userService.getUserByUsername(username)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUserByUsername(username);

        // Assert
        assertNotNull(response);
        assertEquals(user, response.getBody());
        verify(userService).getUserByUsername(username);
    }

    @Test
    void getUserById_returnsUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUserById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(user, response.getBody());
        verify(userService).getUserById(userId);
    }
}
