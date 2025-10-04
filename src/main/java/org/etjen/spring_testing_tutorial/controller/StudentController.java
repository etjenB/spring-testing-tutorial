package org.etjen.spring_testing_tutorial.controller;

import lombok.RequiredArgsConstructor;
import org.etjen.spring_testing_tutorial.domain.Student;
import org.etjen.spring_testing_tutorial.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer studentId) {
        Optional<Student> foundStudent = studentService.getStudentById(studentId);
        return foundStudent.isPresent() ? ResponseEntity.ok(foundStudent.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getStudentByFullName")
    public ResponseEntity<Student> getStudentByFullName(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        Optional<Student> foundStudent = studentService.getStudentByFullName(firstName, lastName);
        return foundStudent.isPresent() ? ResponseEntity.ok(foundStudent.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/saveStudent")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }
}
