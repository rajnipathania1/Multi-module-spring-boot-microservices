package com.project.userservice.controller;


import com.project.userservice.dto.UserRequestDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserResponseDTO> createUser(@RequestHeader(value = "userRole", required = false) String userRole, @Valid @RequestBody UserRequestDTO userRequestDTO) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
      UserResponseDTO userResponseDTO = userService.saveUser(userRequestDTO);
      return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(@RequestHeader(value = "userRole", required = false) String userRole, @PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
      UserResponseDTO userResponseDTO = userService.updateUser(id, userRequestDTO);
      return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@RequestHeader(value = "userRole", required = false) String userRole, @PathVariable Long id) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
      userService.deleteUser(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestHeader(value = "userRole", required = false) String userRole) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
      return ResponseEntity.ok(userService.getAllUsers());
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@RequestHeader(value = "userRole", required = false) String userRole, @PathVariable Long id) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
      return ResponseEntity.ok(userService.getUserById(id));
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/students")
  public ResponseEntity<List<UserResponseDTO>> getAllStudents(@RequestHeader(value = "userRole", required = false) String userRole) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
      return ResponseEntity.ok(userService.getAllUsersByRole("ROLE_STUDENT"));
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/instructors")
  public ResponseEntity<List<UserResponseDTO>> getAllInstructors(@RequestHeader(value = "userRole", required = false) String userRole) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN")) {
      return ResponseEntity.ok(userService.getAllUsersByRole("ROLE_INSTRUCTOR"));
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }


}
