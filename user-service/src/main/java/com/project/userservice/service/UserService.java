package com.project.userservice.service;


import com.project.userservice.model.User;
import com.project.userservice.repository.UserRepository;
import com.project.userservice.dto.UserRequestDTO;
import com.project.userservice.dto.UserResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
    log.info("Saving user with username: {}", userRequestDTO.getUsername());
    User user = new User();
    user.setUsername(userRequestDTO.getUsername());
    user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
    user.setRole(userRequestDTO.getRole());
    User savedUser = userRepository.save(user);
    log.info("User saved with ID: {}", savedUser.getId());
    return new UserResponseDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getRole());
  }

  public List<UserResponseDTO> getAllUsers() {
    return userRepository.findAll().stream()
      .filter(user -> user.getRole().equals("ROLE_STUDENT") || user.getRole().equals("ROLE_INSTRUCTOR"))
      .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getRole()))
      .collect(Collectors.toList());
  }

  public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
    log.info("Updating user with ID: {}", id);
    User user = userRepository.findById(id)
      .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));

    user.setUsername(userRequestDTO.getUsername());
    user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
    user.setRole(userRequestDTO.getRole());

    User updatedUser = userRepository.save(user);
    log.info("User updated with ID: {}", updatedUser.getId());
    return new UserResponseDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getRole());
  }

  public void deleteUser(Long id) {
    log.info("Deleting user with ID: {}", id);
    User user = userRepository.findById(id)
      .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
    userRepository.delete(user);
    log.info("User deleted with ID: {}", id);
  }

  public UserResponseDTO getUserById(Long id) {
    log.info("Fetching user with ID: {}", id);
    User user = userRepository.findById(id)
      .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
    return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole());
  }

  public List<UserResponseDTO> getAllUsersByRole(String role) {
    log.info("Fetching users with role: {}", role);
    List<User> users = userRepository.findByRole(role);
    return users.stream()
      .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getRole()))
      .collect(Collectors.toList());
  }

}
