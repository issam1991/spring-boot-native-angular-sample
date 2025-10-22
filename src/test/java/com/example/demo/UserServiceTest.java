package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        testUser1 = new User(1L, "John Doe", "john@example.com");
        testUser2 = new User(2L, "Jane Smith", "jane@example.com");
    }

    @Test
    @DisplayName("Should return all users")
    void testGetAllUsers() {
        // Given
        List<User> expectedUsers = Arrays.asList(testUser1, testUser2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // When
        List<User> users = userService.getAllUsers();

        // Then
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(expectedUsers, users);
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should return user by ID when user exists")
    void testGetUserById_UserExists() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser1));

        // When
        Optional<User> user = userService.getUserById(1L);

        // Then
        assertTrue(user.isPresent());
        assertEquals("John Doe", user.get().getName());
        assertEquals("john@example.com", user.get().getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when user does not exist")
    void testGetUserById_UserDoesNotExist() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<User> user = userService.getUserById(999L);

        // Then
        assertFalse(user.isPresent());
        verify(userRepository).findById(999L);
    }

    @Test
    @DisplayName("Should create a new user")
    void testCreateUser() {
        // Given
        String name = "Alice Johnson";
        String email = "alice@example.com";
        User savedUser = new User(3L, name, email);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User newUser = userService.createUser(name, email);

        // Then
        assertNotNull(newUser);
        assertEquals(name, newUser.getName());
        assertEquals(email, newUser.getEmail());
        assertEquals(3L, newUser.getId());
        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user when user exists")
    void testDeleteUser_UserExists() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        // When
        boolean deleted = userService.deleteUser(1L);

        // Then
        assertTrue(deleted);
        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should return false when deleting non-existent user")
    void testDeleteUser_UserDoesNotExist() {
        // Given
        when(userRepository.existsById(999L)).thenReturn(false);

        // When
        boolean deleted = userService.deleteUser(999L);

        // Then
        assertFalse(deleted);
        verify(userRepository).existsById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should update user when user exists")
    void testUpdateUser_UserExists() {
        // Given
        String newName = "John Updated";
        String newEmail = "john.updated@example.com";
        User updatedUser = new User(1L, newName, newEmail);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser1));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // When
        Optional<User> result = userService.updateUser(1L, newName, newEmail);

        // Then
        assertTrue(result.isPresent());
        assertEquals(newName, result.get().getName());
        assertEquals(newEmail, result.get().getEmail());
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should return empty when updating non-existent user")
    void testUpdateUser_UserDoesNotExist() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<User> updatedUser = userService.updateUser(999L, "Test", "test@example.com");

        // Then
        assertFalse(updatedUser.isPresent());
        verify(userRepository).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should return correct user count")
    void testGetUserCount() {
        // Given
        when(userRepository.count()).thenReturn(2L);

        // When
        int count = userService.getUserCount();

        // Then
        assertEquals(2, count);
        verify(userRepository).count();
    }

    @Test
    @DisplayName("Should maintain unique IDs for new users")
    void testCreateUser_UniqueIds() {
        // Given
        User user1Saved = new User(1L, "User1", "user1@example.com");
        User user2Saved = new User(2L, "User2", "user2@example.com");
        when(userRepository.existsByEmail("user1@example.com")).thenReturn(false);
        when(userRepository.existsByEmail("user2@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user1Saved).thenReturn(user2Saved);

        // When
        User user1 = userService.createUser("User1", "user1@example.com");
        User user2 = userService.createUser("User2", "user2@example.com");

        // Then
        assertNotEquals(user1.getId(), user2.getId());
        verify(userRepository, times(2)).existsByEmail(anyString());
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when creating user with existing email")
    void testCreateUser_ExistingEmail() {
        // Given
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> userService.createUser("Test User", email));
        
        assertEquals("User with email " + email + " already exists", exception.getMessage());
        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }
}
