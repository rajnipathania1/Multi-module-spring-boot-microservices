package com.project.courseservice.repository;

import com.project.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  // Find courses by instructor username
  List<Course> findByInstructorUsername(String instructorUsername);

  // Optional: Define custom queries if needed
}
