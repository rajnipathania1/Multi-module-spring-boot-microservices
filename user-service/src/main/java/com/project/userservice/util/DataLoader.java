package com.project.userservice.util;


import com.project.userservice.model.User;
import com.project.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
    public void run(String... args) throws Exception {
      User user = new User();
      user.setUsername("SuperAdmin");
      user.setPassword(passwordEncoder.encode("SA@123"));
      user.setRole("ROLE_ADMIN");
      userRepository.save(user);
    }
}
