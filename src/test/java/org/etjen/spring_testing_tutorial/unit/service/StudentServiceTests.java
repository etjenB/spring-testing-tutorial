package org.etjen.spring_testing_tutorial.unit.service;

import org.etjen.spring_testing_tutorial.domain.Student;
import org.etjen.spring_testing_tutorial.exception.*;
import org.etjen.spring_testing_tutorial.infrastructure.EmailService;
import org.etjen.spring_testing_tutorial.repository.StudentRepository;
import org.etjen.spring_testing_tutorial.service.custom.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    // tests for getAllStudents()

    @Test
    void givenStudents_whenGetAllStudents_thenReturnStudents() {
        // given
        Student student1 = Student.builder()
                .firstName("Yellow")
                .lastName("Stone")
                .email("yellow.stone@email.com")
                .build();
        Student student2 = Student.builder()
                .firstName("Purple")
                .lastName("Stone")
                .email("purple.stone@email.com")
                .build();
        Student student3 = Student.builder()
                .firstName("Blue")
                .lastName("Stone")
                .email("blue.stone@email.com")
                .build();
        BDDMockito.given(studentRepository.findAll()).willReturn(List.of(student1, student2, student3));

        // when
        List<Student> foundStudents = studentServiceImpl.getAllStudents();

        // then
        assertEquals(3, foundStudents.size());
    }

    // tests for getStudentById()

    @Test
    void givenStudentId_whenGetStudentById_thenReturnStudent() {
        // given
        Student student = Student.builder()
                .id(1)
                .firstName("Yellow")
                .lastName("Stone")
                .email("yellow.stone@email.com")
                .build();
        BDDMockito.given(studentRepository.findById(1)).willReturn(Optional.of(student));

        // when
        Optional<Student> foundStudent = studentServiceImpl.getStudentById(1);

        // then
        assertTrue(foundStudent.isPresent());
        assertEquals(student, foundStudent.get());
    }

    @Test
    void givenInvalidStudentId_whenGetStudentById_thenThrowInvalidIdParameterException() {
        // given

        // when
        assertThrowsExactly(InvalidIdParameterException.class, () -> studentServiceImpl.getStudentById(0));

        // then
        Mockito.verify(studentRepository, Mockito.never()).findById(0);
    }

    // tests for getStudentByFullName()

    @Test
    void givenFirstAndLastName_whenGetStudentByFullName_thenReturnStudent() {
        // given
        Student student = Student.builder()
                .id(1)
                .firstName("Yellow")
                .lastName("Stone")
                .email("yellow.stone@email.com")
                .build();
        BDDMockito.given(studentRepository.findByFullName(student.getFirstName(), student.getLastName()))
                .willReturn(Optional.of(student));

        // when
        Optional<Student> foundStudent = studentServiceImpl.getStudentByFullName(student.getFirstName(), student.getLastName());

        // then
        assertTrue(foundStudent.isPresent());
        assertEquals(student, foundStudent.get());
        Mockito.verify(studentRepository, Mockito.times(1)).findByFullName(student.getFirstName(), student.getLastName());
    }

    @Test
    void givenBlankFirstName_whenGetStudentByFullName_thenThrowFirstNameBlankException() {
        // given

        // when
        assertThrowsExactly(FirstNameBlankException.class, () -> studentServiceImpl.getStudentByFullName("", "Stone"));

        // then
        Mockito.verify(studentRepository, Mockito.never()).findByFullName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    void givenBlankLastName_whenGetStudentByFullName_thenThrowLastNameBlankException() {
        // given

        // when
        assertThrowsExactly(LastNameBlankException.class, () -> studentServiceImpl.getStudentByFullName("Yellow", ""));

        // then
        Mockito.verify(studentRepository, Mockito.never()).findByFullName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    // tests for saveStudent()

    @Test
    void givenStudent_whenSaveStudent_thenStudentSaved() {
        // given
        BDDMockito.given(studentRepository.findByEmail(ArgumentMatchers.anyString())).willReturn(Optional.empty());
        Student student = Student.builder()
                .id(1)
                .firstName("Yellow")
                .lastName("Stone")
                .email("yellow.stone@email.com")
                .build();

        // when
        Student savedStudent = studentServiceImpl.saveStudent(student);

        // then
        Mockito.verify(studentRepository, Mockito.times(1)).findByEmail(student.getEmail());
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }

    @Test
    void givenExistingEmail_whenSaveStudent_thenThrowStudentAlreadyExistsException() {
        // given
        Student student = Student.builder()
                .id(1)
                .firstName("Yellow")
                .lastName("Stone")
                .email("yellow.stone@email.com")
                .build();
        BDDMockito.given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.of(student));

        // when
        assertThrowsExactly(StudentAlreadyExistsException.class, () -> studentServiceImpl.saveStudent(student));

        // then
        Mockito.verify(studentRepository, Mockito.times(1)).findByEmail(student.getEmail());
        Mockito.verify(studentRepository, Mockito.never()).save(student);
    }

    @Test
    void givenInvalidFirstName_whenSaveStudent_thenThrowFirstNameLengthException() {
        // given
        Student student = Student.builder()
                .id(1)
                .firstName("Ye")
                .lastName("Stone")
                .email("yellow.stone@email.com")
                .build();
        BDDMockito.given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.empty());

        // when
        assertThrowsExactly(FirstNameLengthException.class, () -> studentServiceImpl.saveStudent(student));

        // then
        Mockito.verify(studentRepository, Mockito.times(1)).findByEmail(student.getEmail());
        Mockito.verify(studentRepository, Mockito.never()).save(student);
    }

    @Test
    void givenInvalidLastName_whenSaveStudent_thenThrowLastNameLengthException() {
        // given
        Student student = Student.builder()
                .id(1)
                .firstName("Yellow")
                .lastName("Stoneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
                .email("yellow.stone@email.com")
                .build();
        BDDMockito.given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.empty());

        // when
        assertThrowsExactly(LastNameLengthException.class, () -> studentServiceImpl.saveStudent(student));

        // then
        Mockito.verify(studentRepository, Mockito.times(1)).findByEmail(student.getEmail());
        Mockito.verify(studentRepository, Mockito.never()).save(student);
    }

    @Test
    void givenStudent_whenSaveStudent_thenSendEmailAndSaveStudent() {
        // given
        Student student = Student.builder()
                .id(1)
                .firstName("Yellow")
                .lastName("Stone")
                .email("yellow.stone@email.com")
                .build();
        BDDMockito.given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.empty());

        // when
        Student savedStudent = studentServiceImpl.saveStudent(student);

        // then
        Mockito.verify(studentRepository, Mockito.times(1)).findByEmail(student.getEmail());
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(emailService, Mockito.times(1))
                .sendEmail(emailCaptor.capture(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        assertEquals(student.getEmail(), emailCaptor.getValue());
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }
}
