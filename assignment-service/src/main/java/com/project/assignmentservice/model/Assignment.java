package com.project.assignmentservice.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Data
public class Assignment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Title cannot be blank")
  @Size(max = 100, message = "Title should not exceed 100 characters")
  @Column(nullable = false, length = 100)
  private String title;

  @NotBlank(message = "Description cannot be blank")
  @Size(max = 500, message = "Description should not exceed 500 characters")
  @Column(nullable = false, length = 500)
  private String description;

  @NotNull(message = "Course ID cannot be null")
  @Column(nullable = false)
  private Long courseId;

  // Optionally, you can add more fields like due date, max grade, etc.
}
