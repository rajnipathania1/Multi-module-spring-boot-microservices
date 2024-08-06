# Course Management Application
This project is a simple course management application built using microservices with Spring Boot. The application uses an embedded H2 database and implements role-based access control with Spring Security. It includes the following features:

1. User Management: Admins can manage users (create, update, delete, list).
2. Course Management: Instructors can create and manage courses, and students can enroll in them.
3. Enrollment: Students can enroll in multiple courses.
4. Assignments: Optional feature for creating and managing assignments.
## Features

   ### User Types:
   1. System Admin: Can create, update, delete, and list users, list all courses, and list students.
   2. Instructor: Can create and manage courses, and list students enrolled in courses they created.
   3. Student: Can view all courses, enroll in courses, and access only the courses and assignments they are enrolled in.

## How to run the application

1. Run `mvn clean verify -DskipTests` by going inside each folder to build the applications.
2. After that run `mvn spring-boot:run` by going inside each folder to start the applications.

