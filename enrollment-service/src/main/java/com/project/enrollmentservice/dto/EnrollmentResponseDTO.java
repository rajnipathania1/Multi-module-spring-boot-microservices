package com.project.enrollmentservice.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentResponseDTO {

  private Long id;
  private String studentName;
  private Long courseId;
  private List<AssignmentResponseDTO> assignments;

}

