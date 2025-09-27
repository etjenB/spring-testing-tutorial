package org.etjen.spring_testing_tutorial;

import org.etjen.spring_testing_tutorial.domain.Student;
import org.etjen.spring_testing_tutorial.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringTestingTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTestingTutorialApplication.class, args);
	}

}
