package org.etjen.spring_testing_tutorial.service;

import org.etjen.spring_testing_tutorial.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Integer studentId);
    Optional<Student> getStudentByFullName(String firstName, String lastName);
    Student saveStudent(Student student);
}
