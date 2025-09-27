package org.etjen.spring_testing_tutorial.unit.repository;

import jakarta.persistence.EntityManager;
import org.etjen.spring_testing_tutorial.domain.Student;
import org.etjen.spring_testing_tutorial.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudentRepositoryTests {
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    EntityManager entityManager;
    @Autowired
    StudentRepository studentRepository;
    Student student;

    @BeforeEach
    void setUp() {
        jdbc.execute("TRUNCATE TABLE student");
        jdbc.execute("ALTER TABLE student ALTER COLUMN id RESTART WITH 1");
        student = Student.builder()
                .firstName("Lea")
                .lastName("Lion")
                .email("lea.lion@email.com")
                .build();
    }

    @Test
    void testFindStudentById() {
        studentRepository.saveAndFlush(student);
        entityManager.clear();
        Optional<Student> foundStudent = studentRepository.findById(student.getId());
        assertTrue(foundStudent.isPresent());
        assertEquals(student, foundStudent.get());
    }

    @Test
    void givenStudent_whenFindById_thenReturnStudent() {
        // given
        studentRepository.saveAndFlush(student);
        entityManager.clear();

        // when
        Optional<Student> foundStudent = studentRepository.findById(student.getId());

        // then
        assertTrue(foundStudent.isPresent());
        assertEquals(student, foundStudent.get());
    }

    @Test
    void givenStudent_whenDeleteById_thenDeletedStudent() {
        // given
        studentRepository.saveAndFlush(student);
        entityManager.clear();

        // when
        studentRepository.deleteById(student.getId());
        studentRepository.flush();
        entityManager.clear();

        // then
        Optional<Student> dbStudent = studentRepository.findById(student.getId());
        assertTrue(dbStudent.isEmpty());
    }

    @Test
    void givenStudent_whenUpdateStudent_thenReturnUpdatedStudent() {
        // given
        Student savedStudent = studentRepository.saveAndFlush(student);
        entityManager.clear();

        // when
        savedStudent.setFirstName("Ragnar");
        savedStudent.setLastName("Lothbrok");
        savedStudent.setEmail("ragnar.lothbrok@email.com");
        studentRepository.saveAndFlush(savedStudent);
        entityManager.clear();

        // then
        Optional<Student> foundStudent = studentRepository.findById(student.getId());
        assertTrue(foundStudent.isPresent());
        assertNotEquals("Lea", foundStudent.get().getFirstName());
        assertEquals(savedStudent, foundStudent.get());
    }

    @Test
    void givenStudent_whenFindByEmail_thenReturnStudent() {
        // given
        studentRepository.saveAndFlush(student);
        entityManager.clear();

        // when
        Optional<Student> foundStudent = studentRepository.findByEmail(student.getEmail());

        // then
        assertTrue(foundStudent.isPresent());
        assertEquals(student.getId(), foundStudent.get().getId());
    }

    @Test
    void givenStudentList_whenFindAll_thenReturnStudents() {
        // given
        Student student2 = Student.builder()
                .firstName("Leo")
                .lastName("Lion")
                .email("leo.lion@email.com")
                .build();
        Student student3 = Student.builder()
                .firstName("Loki")
                .lastName("Lion")
                .email("loki.lion@email.com")
                .build();
        studentRepository.saveAllAndFlush(List.of(student, student2, student3));
        entityManager.clear();

        // when
        List<Student> foundStudents = studentRepository.findAll();

        // then
        assertThat(foundStudents.size()).isGreaterThan(0);
        assertEquals(3, foundStudents.size());
    }

    @Test
    void givenStudentFirstNameLastName_whenFindByFullName_thenReturnStudent() {
        // given
        studentRepository.saveAndFlush(student);
        entityManager.clear();

        // when
        Optional<Student> foundStudent = studentRepository.findByFullName("Lea", "Lion");

        // then
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getId()).isEqualTo(student.getId());
        assertThat(foundStudent.get().getFirstName()).isEqualTo(student.getFirstName());
        assertThat(foundStudent.get().getLastName()).isEqualTo(student.getLastName());
        assertThat(foundStudent.get().getEmail()).isEqualTo(student.getEmail());
        assertThat(studentRepository.findByFullName("Nope", "Nobody")).isEmpty();
    }
}
