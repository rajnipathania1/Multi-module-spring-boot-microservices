package com.project.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank(message = "Role is mandatory")
  @Pattern(regexp = "ROLE_ADMIN|ROLE_STUDENT|ROLE_INSTRUCTOR", message = "Role must be ROLE_ADMIN, ROLE_STUDENT, or ROLE_INSTRUCTOR")
  private String role;
}
