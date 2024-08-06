package com.project.assignmentservice.controller;

import com.project.assignmentservice.dto.AssignmentRequestDTO;
import com.project.assignmentservice.dto.AssignmentResponseDTO;
import com.project.assignmentservice.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

  @Autowired
  private AssignmentService assignmentService;

  @PostMapping
  public ResponseEntity<AssignmentResponseDTO> createAssignment(@RequestHeader(value = "userRole", required = false) String userRole, @Valid @RequestBody AssignmentRequestDTO assignmentRequestDTO) {
    if(userRole.equalsIgnoreCase("ROLE_INSTRUCTOR")) {
      AssignmentResponseDTO assignmentResponseDTO = assignmentService.createAssignment(assignmentRequestDTO);
      return new ResponseEntity<>(assignmentResponseDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<AssignmentResponseDTO> getAssignmentById(@PathVariable Long id) {
    AssignmentResponseDTO assignmentResponseDTO = assignmentService.getAssignmentById(id);
    return ResponseEntity.ok(assignmentResponseDTO);
  }

  @GetMapping("/course/{courseId}")
  public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsByCourse(@PathVariable Long courseId) {
      List<AssignmentResponseDTO> assignments = assignmentService.getAssignmentsByCourse(courseId);
      return ResponseEntity.ok(assignments);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AssignmentResponseDTO> updateAssignment(@RequestHeader(value = "userRole", required = false) String userRole, @PathVariable Long id, @Valid @RequestBody AssignmentRequestDTO assignmentRequestDTO) {
    if(userRole.equalsIgnoreCase("ROLE_INSTRUCTOR")) {
      AssignmentResponseDTO assignmentResponseDTO = assignmentService.updateAssignment(id, assignmentRequestDTO);
      return ResponseEntity.ok(assignmentResponseDTO);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAssignment(@RequestHeader(value = "userRole", required = false) String userRole, @PathVariable Long id) {
    if(userRole.equalsIgnoreCase("ROLE_INSTRUCTOR")) {
      assignmentService.deleteAssignment(id);
    return ResponseEntity.noContent().build();
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
