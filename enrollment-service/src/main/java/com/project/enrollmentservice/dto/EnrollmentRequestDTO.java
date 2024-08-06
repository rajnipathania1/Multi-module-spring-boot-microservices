package com.project.enrollmentservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EnrollmentRequestDTO {
  @NotNull(message = "Course ID cannot be null")
  private Long courseId;
}
