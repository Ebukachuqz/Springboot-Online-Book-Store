package com.bookstore.onlinebookstore.servicetest;

import com.bookstore.onlinebookstore.model.User;
import com.bookstore.onlinebookstore.repository.UserRepository;
import com.bookstore.onlinebookstore.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_whenUsernameNotTaken_createsUser() {
        // Arrange
        String username = "ebukachuqz";
        User userToCreate = new User();
        userToCreate.setUsername(username);

        // Simulate that username does not exist
        when(userRepository.existsByUsername(username)).thenReturn(false);
        // Simulate repository saving and assigning an ID
        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setUsername(username);
        when(userRepository.save(any(User.class))).thenReturn(createdUser);

        // Act
        User result = userService.createUser(username);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(username, result.getUsername());
        verify(userRepository).existsByUsername(username);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_whenUsernameTaken_throwsException() {
        // Arrange
        String username = "ebukachuqz";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(username));
        verify(userRepository).existsByUsername(username);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_whenUserExists_returnsUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_whenUserDoesNotExist_throwsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserByUsername_whenUserExists_returnsUser() {
        // Arrange
        String username = "ebukachuqz";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void getUserByUsername_whenUserDoesNotExist_throwsException() {
        // Arrange
        String username = "ebukachuqz";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getUserByUsername(username));
        verify(userRepository).findByUsername(username);
    }
}
