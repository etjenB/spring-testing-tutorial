package org.etjen.spring_testing_tutorial;

import org.etjen.spring_testing_tutorial.domain.Student;
import org.etjen.spring_testing_tutorial.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringTestingTutorialApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringTestingTutorialApplication.class, args);
        StudentRepository repository = context.getBean(StudentRepository.class);

        Student student = Student.builder()
                .firstName("Lea")
                .lastName("Lion")
                .email("lea.lion@email.com")
                .build();

        repository.save(student);

        //Student foundStudent = repository.findById(student.getId()).get();
        //Student foundStudent = repository.findByEmail(student.getEmail()).get();
        Student foundStudent = repository.findByFullName(student.getFirstName(), student.getLastName()).get();

        System.out.println("\n\n----------------\n"+foundStudent+"\n-------------------------\n\n");

        repository.delete(student);
	}

}
