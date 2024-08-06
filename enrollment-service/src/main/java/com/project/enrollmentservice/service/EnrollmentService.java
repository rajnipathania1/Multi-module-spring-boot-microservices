package com.project.enrollmentservice.service;

import com.project.enrollmentservice.client.AssignmentClient;
import com.project.enrollmentservice.dto.AssignmentResponseDTO;
import com.project.enrollmentservice.dto.CourseEnrollmentDTO;
import com.project.enrollmentservice.dto.EnrollmentRequestDTO;
import com.project.enrollmentservice.dto.EnrollmentResponseDTO;
import com.project.enrollmentservice.model.Enrollment;
import com.project.enrollmentservice.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EnrollmentService {

  @Autowired
  private EnrollmentRepository enrollmentRepository;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AssignmentClient assignmentClient;

  @Transactional
  public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO enrollmentRequestDTO, String studentName) {
    log.info("Enrolling student with ID: {} in course with ID: {}", studentName, enrollmentRequestDTO.getCourseId());
    Enrollment enrollment = new Enrollment();
    enrollment.setCourseId(enrollmentRequestDTO.getCourseId());
    enrollment.setStudentName(studentName);
    Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
    List<AssignmentResponseDTO> assignments = assignmentClient.getAssignmentsByCourseId(enrollmentRequestDTO.getCourseId());
    return new EnrollmentResponseDTO(savedEnrollment.getId(),savedEnrollment.getStudentName(), savedEnrollment.getCourseId(), assignments);
  }

  public List<EnrollmentResponseDTO> getEnrollmentsByStudent(String studentName) {
    log.info("Fetching enrollments for student name: {}", studentName);

    List<Enrollment> enrollments = enrollmentRepository.findByStudentName(studentName);

    // Map enrollments to EnrollmentResponseDTO, including assignments
    return enrollments.stream()
      .map(enrollment -> {
        // Fetch assignments for the course associated with the enrollment
        List<AssignmentResponseDTO> assignments = assignmentClient.getAssignmentsByCourseId(enrollment.getCourseId());

        return EnrollmentResponseDTO.builder()
          .id(enrollment.getId())
          .studentName(enrollment.getStudentName())
          .courseId(enrollment.getCourseId())
          .assignments(assignments)
          .build();
      })
      .collect(Collectors.toList());
  }


  public List<CourseEnrollmentDTO> getEnrollments() {
    log.info("Fetching all enrollments by courses");
    List<Enrollment> enrollments = enrollmentRepository.findAll();
    // Group enrollments by course ID
    Map<Long, List<Enrollment>> enrollmentsByCourseId = enrollments.stream()
      .collect(Collectors.groupingBy(Enrollment::getCourseId));

    // Transform the grouped data into CourseEnrollmentDTOs
    List<CourseEnrollmentDTO> courseEnrollments = enrollmentsByCourseId.entrySet().stream()
      .map(entry -> new CourseEnrollmentDTO(
        entry.getKey(),
        entry.getValue().stream()
          .map(Enrollment::getStudentName)
          .collect(Collectors.toList())))
      .collect(Collectors.toList());

    return courseEnrollments;
  }
}
