package com.project.courseservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class CourseRequestDTO {

  @NotBlank(message = "Course name cannot be blank")
  @Size(max = 100, message = "Course name should not exceed 100 characters")
  private String name;

  @NotBlank(message = "Course description cannot be blank")
  @Size(max = 500, message = "Course description should not exceed 500 characters")
  private String description;

}
