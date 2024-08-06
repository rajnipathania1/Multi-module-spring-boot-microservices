package com.project.assignmentservice.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class AssignmentRequestDTO {

  @NotBlank(message = "Title cannot be blank")
  @Size(max = 100, message = "Title should not exceed 100 characters")
  private String title;

  @NotBlank(message = "Description cannot be blank")
  @Size(max = 500, message = "Description should not exceed 500 characters")
  private String description;

  @NotNull(message = "Course ID cannot be null")
  private Long courseId;
}

