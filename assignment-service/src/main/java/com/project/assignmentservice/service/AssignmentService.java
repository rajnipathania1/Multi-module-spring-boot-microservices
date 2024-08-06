package com.project.assignmentservice.service;

import com.project.assignmentservice.dto.AssignmentRequestDTO;
import com.project.assignmentservice.dto.AssignmentResponseDTO;
import com.project.assignmentservice.model.Assignment;
import com.project.assignmentservice.repository.AssignmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AssignmentService {

  @Autowired
  private AssignmentRepository assignmentRepository;

  @Transactional
  public AssignmentResponseDTO createAssignment(AssignmentRequestDTO assignmentRequestDTO) {
    log.info("Creating assignment with title: {}", assignmentRequestDTO.getTitle());
    Assignment assignment = new Assignment();
    assignment.setTitle(assignmentRequestDTO.getTitle());
    assignment.setDescription(assignmentRequestDTO.getDescription());
    assignment.setCourseId(assignmentRequestDTO.getCourseId());
    Assignment savedAssignment = assignmentRepository.save(assignment);
    return new AssignmentResponseDTO(savedAssignment.getId(), savedAssignment.getTitle(), savedAssignment.getDescription(), savedAssignment.getCourseId());
  }

  public AssignmentResponseDTO getAssignmentById(Long id) {
    log.info("Fetching assignment with ID: {}", id);
    Assignment assignment = assignmentRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + id));
    return new AssignmentResponseDTO(assignment.getId(), assignment.getTitle(), assignment.getDescription(), assignment.getCourseId());
  }

  public List<AssignmentResponseDTO> getAssignmentsByCourse(Long courseId) {
    log.info("Fetching assignments for course ID: {}", courseId);
    List<Assignment> assignments = assignmentRepository.findByCourseId(courseId);
    return assignments.stream()
      .map(assignment -> new AssignmentResponseDTO(assignment.getId(), assignment.getTitle(), assignment.getDescription(), assignment.getCourseId()))
      .collect(Collectors.toList());
  }

  @Transactional
  public AssignmentResponseDTO updateAssignment(Long id, AssignmentRequestDTO assignmentRequestDTO) {
    log.info("Updating assignment with ID: {}", id);
    Assignment assignment = assignmentRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + id));
    assignment.setTitle(assignmentRequestDTO.getTitle());
    assignment.setDescription(assignmentRequestDTO.getDescription());
    assignment.setCourseId(assignmentRequestDTO.getCourseId());
    Assignment updatedAssignment = assignmentRepository.save(assignment);
    return new AssignmentResponseDTO(updatedAssignment.getId(), updatedAssignment.getTitle(), updatedAssignment.getDescription(), updatedAssignment.getCourseId());
  }

  @Transactional
  public void deleteAssignment(Long id) {
    log.info("Deleting assignment with ID: {}", id);
    Assignment assignment = assignmentRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + id));
    assignmentRepository.delete(assignment);
  }
}
