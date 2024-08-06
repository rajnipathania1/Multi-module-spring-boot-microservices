package com.project.courseservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Course name cannot be blank")
  @Size(max = 100, message = "Course name should not exceed 100 characters")
  @Column(nullable = false, length = 100)
  private String name;

  @NotBlank(message = "Course description cannot be blank")
  @Size(max = 500, message = "Course description should not exceed 500 characters")
  @Column(nullable = false, length = 500)
  private String description;

  @NotBlank(message = "instructor username cannot be blank")
  @Size(max = 50, message = "instructor username should not exceed 50 characters")
  @Column(nullable = false, length = 50)
  private String instructorUsername;
}
