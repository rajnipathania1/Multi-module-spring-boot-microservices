package com.project.courseservice.controller;


import com.project.courseservice.dto.CourseRequestDTO;
import com.project.courseservice.dto.CourseResponseDTO;
import com.project.courseservice.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @PostMapping
  public ResponseEntity<CourseResponseDTO> createCourse(@RequestHeader(value = "userRole", required = false) String userRole, @RequestHeader(value = "userName", required = false) String userName, @Valid @RequestBody CourseRequestDTO courseRequestDTO) {
    if(userRole.equalsIgnoreCase("ROLE_INSTRUCTOR")) {
      CourseResponseDTO courseResponseDTO = courseService.saveCourse(courseRequestDTO, userName);
      return new ResponseEntity<>(courseResponseDTO, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<CourseResponseDTO> updateCourse(@RequestHeader(value = "userRole", required = false) String userRole, @RequestHeader(value = "userName", required = false) String userName,
                                                        @PathVariable Long id, @Valid @RequestBody CourseRequestDTO courseRequestDTO) {
    if(userRole.equalsIgnoreCase("ROLE_INSTRUCTOR") && courseService.isCourseOwner(id, userName)) {
      CourseResponseDTO courseResponseDTO = courseService.updateCourse(id, courseRequestDTO);
      return new ResponseEntity<>(courseResponseDTO, HttpStatus.OK);
    }else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCourse(@RequestHeader(value = "userRole", required = false) String userRole, @RequestHeader(value = "userName", required = false) String userName, @PathVariable Long id) {
    if(userRole.equalsIgnoreCase("ROLE_INSTRUCTOR") && courseService.isCourseOwner(id, userName)) {
      courseService.deleteCourse(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping
  public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
    return ResponseEntity.ok(courseService.getAllCourses());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CourseResponseDTO> getCourseById(@RequestHeader(value = "userRole", required = false) String userRole,  @RequestHeader(value = "userName", required = false) String userName, @PathVariable Long id) {
    if(userRole.equalsIgnoreCase("ROLE_INSTRUCTOR") && courseService.isCourseOwner(id, userName)) {
      return ResponseEntity.ok(courseService.getCourseById(id));
    }else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/students/enrolled")
  public ResponseEntity<List<CourseResponseDTO>> getCoursesWithStudents(@RequestHeader(value = "userRole", required = false) String userRole) {
    if(userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_INSTRUCTOR")) {
      return ResponseEntity.ok(courseService.getCoursesWithStudents());
    } else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping("/instructor/{username}")
  public ResponseEntity<List<CourseResponseDTO>> getCoursesByInstructor(@RequestHeader(value = "userRole", required = false) String userRole,  @RequestHeader(value = "userName", required = false) String authUserName, @PathVariable String username) {
    if((userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_INSTRUCTOR")) && username.equalsIgnoreCase(authUserName)) {
      return ResponseEntity.ok(courseService.getCoursesByInstructor(username));
    }else {
      return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
  }
}
