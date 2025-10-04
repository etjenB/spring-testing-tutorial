package org.etjen.spring_testing_tutorial.service.custom;

import lombok.RequiredArgsConstructor;
import org.etjen.spring_testing_tutorial.domain.Student;
import org.etjen.spring_testing_tutorial.exception.*;
import org.etjen.spring_testing_tutorial.infrastructure.EmailService;
import org.etjen.spring_testing_tutorial.repository.StudentRepository;
import org.etjen.spring_testing_tutorial.service.StudentService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final EmailService emailService;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Integer studentId) {
        if (studentId <= 0) {
            throw new InvalidIdParameterException();
        }
        return studentRepository.findById(studentId);
    }

    @Override
    public Optional<Student> getStudentByFullName(String firstName, String lastName) {
        if (firstName.isBlank()) {
            throw new FirstNameBlankException();
        } else if (lastName.isBlank()) {
            throw new LastNameBlankException();
        }
        return studentRepository.findByFullName(firstName, lastName);
    }

    @Override
    public Student saveStudent(Student student) {
        Optional<Student> foundStudent = studentRepository.findByEmail(student.getEmail());
        if (foundStudent.isPresent()) {
            throw new StudentAlreadyExistsException(foundStudent.get().getEmail());
        }

        int minLength = 3;
        int maxLength = 25;
        int firstNameLength = student.getFirstName().length();
        int lastNameLength = student.getLastName().length();
        if (firstNameLength < minLength || firstNameLength > maxLength) {
            throw new FirstNameLengthException(minLength, maxLength);
        } else if (lastNameLength < minLength || lastNameLength > maxLength) {
            throw new LastNameLengthException(minLength, maxLength);
        }

        emailService.sendEmail(student.getEmail(), "Welcome!", "Welcome new student and happy studies.");
        return studentRepository.save(student);
    }
}
