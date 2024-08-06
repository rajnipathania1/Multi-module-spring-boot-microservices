package com.project.enrollmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignmentResponseDTO {

  private Long id;
  private String title;
  private String description;
  private Long courseId;
}
