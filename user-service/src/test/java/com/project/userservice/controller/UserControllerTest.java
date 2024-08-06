package com.project.userservice.controller;

import com.project.userservice.dto.UserRequestDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UserControllerTest {

  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void testCreateUser_AdminRole() throws Exception {
    UserRequestDTO userRequestDTO = new UserRequestDTO("john_doe", "password123", "ROLE_STUDENT");
    UserResponseDTO userResponseDTO = new UserResponseDTO();

    when(userService.saveUser(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .header("userRole", "ROLE_ADMIN")
        .content("{\"username\":\"john_doe\",\"password\":\"password123\",\"role\":\"ROLE_STUDENT\"}")
        .contentType("application/json"))
      .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void testCreateUser_NotAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
        .header("userRole", "ROLE_USER")
        .content("{\"username\":\"john_doe\",\"password\":\"password123\",\"role\":\"ROLE_STUDENT\"}")
        .contentType("application/json"))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void testUpdateUser_AdminRole() throws Exception {
    UserRequestDTO userRequestDTO = new UserRequestDTO("john_doe", "new_password123", "ROLE_INSTRUCTOR");
    UserResponseDTO userResponseDTO = new UserResponseDTO();

    when(userService.updateUser(anyLong(), any(UserRequestDTO.class))).thenReturn(userResponseDTO);

    mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
        .header("userRole", "ROLE_ADMIN")
        .content("{\"username\":\"john_doe\",\"password\":\"new_password123\",\"role\":\"ROLE_INSTRUCTOR\"}")
        .contentType("application/json"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testUpdateUser_NotAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
        .header("userRole", "ROLE_USER")
        .content("{\"username\":\"john_doe\",\"password\":\"new_password123\",\"role\":\"ROLE_INSTRUCTOR\"}")
        .contentType("application/json"))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void testDeleteUser_AdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
        .header("userRole", "ROLE_ADMIN"))
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void testDeleteUser_NotAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
        .header("userRole", "ROLE_USER"))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void testGetAllUsers_AdminRole() throws Exception {
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    List<UserResponseDTO> users = Collections.singletonList(userResponseDTO);

    when(userService.getAllUsers()).thenReturn(users);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
        .header("userRole", "ROLE_ADMIN"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testGetAllUsers_NotAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
        .header("userRole", "ROLE_USER"))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void testGetUserById_AdminRole() throws Exception {
    UserResponseDTO userResponseDTO = new UserResponseDTO();

    when(userService.getUserById(anyLong())).thenReturn(userResponseDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")
        .header("userRole", "ROLE_ADMIN"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testGetUserById_NotAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1")
        .header("userRole", "ROLE_USER"))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void testGetAllStudents_AdminRole() throws Exception {
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    List<UserResponseDTO> students = Collections.singletonList(userResponseDTO);

    when(userService.getAllUsersByRole("ROLE_STUDENT")).thenReturn(students);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/students")
        .header("userRole", "ROLE_ADMIN"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testGetAllStudents_NotAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/students")
        .header("userRole", "ROLE_USER"))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void testGetAllInstructors_AdminRole() throws Exception {
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    List<UserResponseDTO> instructors = Collections.singletonList(userResponseDTO);

    when(userService.getAllUsersByRole("ROLE_INSTRUCTOR")).thenReturn(instructors);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/instructors")
        .header("userRole", "ROLE_ADMIN"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testGetAllInstructors_NotAdminRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/instructors")
        .header("userRole", "ROLE_USER"))
      .andExpect(MockMvcResultMatchers.status().isForbidden());
  }
}
