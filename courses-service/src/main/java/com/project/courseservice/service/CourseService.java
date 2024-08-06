package com.project.courseservice.service;

import com.project.courseservice.dto.CourseRequestDTO;
import com.project.courseservice.dto.CourseResponseDTO;
import com.project.courseservice.model.Course;
import com.project.courseservice.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

  @Autowired
  private CourseRepository courseRepository;

  public CourseResponseDTO saveCourse(CourseRequestDTO courseRequestDTO, String userName) {
    log.info("Saving course: {}", courseRequestDTO.getName());
    Course course = new Course();
    course.setName(courseRequestDTO.getName());
    course.setDescription(courseRequestDTO.getDescription());
    course.setInstructorUsername(userName);
    Course savedCourse = courseRepository.save(course);
    return new CourseResponseDTO(savedCourse.getId(), savedCourse.getName(), savedCourse.getDescription(), savedCourse.getInstructorUsername());
  }

  public CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDTO) {
    log.info("Updating course with ID: {}", id);
    Course course = courseRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));

    course.setName(courseRequestDTO.getName());
    course.setDescription(courseRequestDTO.getDescription());
    Course updatedCourse = courseRepository.save(course);
    return new CourseResponseDTO(updatedCourse.getId(), updatedCourse.getName(), updatedCourse.getDescription(), updatedCourse.getInstructorUsername());
  }

  public void deleteCourse(Long id) {
    log.info("Deleting course with ID: {}", id);
    Course course = courseRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
    courseRepository.delete(course);
  }

  public CourseResponseDTO getCourseById(Long id) {
    log.info("Fetching course with ID: {}", id);
    Course course = courseRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
    return new CourseResponseDTO(course.getId(), course.getName(), course.getDescription(), course.getInstructorUsername());
  }

  public List<CourseResponseDTO> getAllCourses() {
    log.info("Fetching all courses");
    List<Course> courses = courseRepository.findAll();
    return courses.stream()
      .map(course -> new CourseResponseDTO(course.getId(), course.getName(), course.getDescription(), course.getInstructorUsername()))
      .collect(Collectors.toList());
  }

  public List<CourseResponseDTO> getCoursesByInstructor(String instructorUsername) {
    log.info("Fetching courses by instructor: {}", instructorUsername);
    List<Course> courses = courseRepository.findByInstructorUsername(instructorUsername);
    return courses.stream()
      .map(course -> new CourseResponseDTO(course.getId(), course.getName(), course.getDescription(), course.getInstructorUsername()))
      .collect(Collectors.toList());
  }

  // Method to check if the current user is the owner of the course
  public boolean isCourseOwner(Long courseId, String username) {
    Course course = courseRepository.findById(courseId)
      .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    return course.getInstructorUsername().equals(username);
  }

  // Optional: List courses with enrolled students (if you have an enrollment feature)
  @Transactional
  public List<CourseResponseDTO> getCoursesWithStudents() {
    // Implement logic to fetch courses and their enrolled students
    // This is just a placeholder implementation
    return getAllCourses(); // Replace with actual implementation
  }
}

