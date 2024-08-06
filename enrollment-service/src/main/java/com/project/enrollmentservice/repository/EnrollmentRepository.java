package com.project.enrollmentservice.repository;

import com.project.enrollmentservice.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  List<Enrollment> findByStudentName(String studentName);

  List<Enrollment> findByCourseId(Long courseId);
}
