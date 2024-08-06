package com.project.enrollmentservice.client;

import com.project.enrollmentservice.dto.AssignmentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "assignment-service")
public interface AssignmentClient {

  @GetMapping("/api/assignments/course/{courseId}")
  List<AssignmentResponseDTO> getAssignmentsByCourseId(@PathVariable("courseId") Long courseId);
}
