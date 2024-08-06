package com.project.userservice.controller;

import com.project.userservice.dto.LoginRequestDTO;
import com.project.userservice.dto.LoginResponseDTO;
import com.project.userservice.dto.UserResponseDTO;
import com.project.userservice.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class IdentityController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> authenticateAndGetToken(@RequestBody LoginRequestDTO loginRequestDTO) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
    if (authentication.isAuthenticated()) {
      List<String> roles = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
      LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
      loginResponseDTO.setToken(jwtService.generateToken(loginRequestDTO.getUsername(), roles));
      return new ResponseEntity<>(loginResponseDTO, HttpStatus.CREATED);
    } else {
      throw new UsernameNotFoundException("invalid user request !");
    }
  }
}
