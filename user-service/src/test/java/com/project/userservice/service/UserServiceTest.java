package com.project.userservice.service;

import com.project.userservice.dto.UserRequestDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.model.User;
import com.project.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSaveUser() {
    UserRequestDTO userRequestDTO = new UserRequestDTO("username", "password", "ROLE_STUDENT");
    User user = new User();
    user.setId(1L);
    user.setUsername("username");
    user.setPassword("encodedPassword");
    user.setRole("ROLE_STUDENT");

    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponseDTO savedUser = userService.saveUser(userRequestDTO);

    assertNotNull(savedUser);
    assertEquals(1L, savedUser.getId());
    assertEquals("username", savedUser.getUsername());
    assertEquals("ROLE_STUDENT", savedUser.getRole());

    verify(passwordEncoder, times(1)).encode(userRequestDTO.getPassword());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testGetAllUsers() {
    User user1 = new User(1L, "user1", "password1", "ROLE_STUDENT");
    User user2 = new User(2L, "user2", "password2", "ROLE_INSTRUCTOR");
    when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

    List<UserResponseDTO> users = userService.getAllUsers();

    assertNotNull(users);
    assertEquals(2, users.size());

    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testUpdateUser() {
    Long userId = 1L;
    UserRequestDTO userRequestDTO = new UserRequestDTO("updatedUser", "updatedPassword", "ROLE_INSTRUCTOR");
    User user = new User(userId, "user", "password", "ROLE_STUDENT");

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponseDTO updatedUser = userService.updateUser(userId, userRequestDTO);

    assertNotNull(updatedUser);
    assertEquals(userId, updatedUser.getId());
    assertEquals("updatedUser", updatedUser.getUsername());
    assertEquals("ROLE_INSTRUCTOR", updatedUser.getRole());

    verify(userRepository, times(1)).findById(userId);
    verify(passwordEncoder, times(1)).encode(userRequestDTO.getPassword());
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testDeleteUser() {
    Long userId = 1L;
    User user = new User(userId, "user", "password", "ROLE_STUDENT");

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    userService.deleteUser(userId);

    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(1)).delete(user);
  }

  @Test
  void testGetUserById() {
    Long userId = 1L;
    User user = new User(userId, "user", "password", "ROLE_STUDENT");

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    UserResponseDTO foundUser = userService.getUserById(userId);

    assertNotNull(foundUser);
    assertEquals(userId, foundUser.getId());
    assertEquals("user", foundUser.getUsername());
    assertEquals("ROLE_STUDENT", foundUser.getRole());

    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void testGetAllUsersByRole() {
    String role = "ROLE_STUDENT";
    User user1 = new User(1L, "user1", "password1", role);
    User user2 = new User(2L, "user2", "password2", role);

    when(userRepository.findByRole(role)).thenReturn(Arrays.asList(user1, user2));

    List<UserResponseDTO> users = userService.getAllUsersByRole(role);

    assertNotNull(users);
    assertEquals(2, users.size());

    verify(userRepository, times(1)).findByRole(role);
  }

  @Test
  void testGetUserById_NotFound() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(userId));

    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void testDeleteUser_NotFound() {
    Long userId = 1L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userService.deleteUser(userId));

    verify(userRepository, times(1)).findById(userId);
  }
}
