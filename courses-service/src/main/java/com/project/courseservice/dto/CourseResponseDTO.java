package com.project.courseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseResponseDTO {

  private Long id;
  private String name;
  private String description;
  private String instructorUsername;

  // Optionally, you can add more fields if needed, e.g., list of ROLE_STUDENTs or assignments
}
