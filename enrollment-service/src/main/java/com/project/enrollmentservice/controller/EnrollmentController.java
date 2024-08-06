package com.project.enrollmentservice.controller;

import com.project.enrollmentservice.dto.CourseEnrollmentDTO;
import com.project.enrollmentservice.dto.EnrollmentRequestDTO;
import com.project.enrollmentservice.dto.EnrollmentResponseDTO;
import com.project.enrollmentservice.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

  @Autowired
  private EnrollmentService enrollmentService;

  @PostMapping
  public ResponseEntity<EnrollmentResponseDTO> enrollStudent(@RequestHeader(value = "userRole", required = false) String userRole, @RequestHeader(value = "userName", required = false) String userName, @Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {
    if(userRole.equalsIgnoreCase("ROLE_STUDENT")) {
      EnrollmentResponseDTO enrollmentResponseDTO = enrollmentService.enrollStudent(enrollmentRequestDTO, userName);
      return new ResponseEntity<>(enrollmentResponseDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/student")
  public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByStudent(@RequestHeader(value = "userRole", required = false) String userRole, @RequestHeader(value = "userName", required = false) String userName) {
    if(userRole.equalsIgnoreCase("ROLE_STUDENT")) {
      List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByStudent(userName);
      return ResponseEntity.ok(enrollments);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }


  @GetMapping("/course")
  public ResponseEntity<List<CourseEnrollmentDTO>> getEnrollments(@RequestHeader(value = "userRole", required = false) String userRole) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_INSTRUCTOR")) {
      List<CourseEnrollmentDTO> enrollments = enrollmentService.getEnrollments();
      return ResponseEntity.ok(enrollments);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}

