package com.project.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponseDTO {
  private String token;

  public LoginResponseDTO(String token) {
    this.token = token;
  }

}
