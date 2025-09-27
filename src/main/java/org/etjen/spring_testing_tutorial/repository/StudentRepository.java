package org.etjen.spring_testing_tutorial.repository;

import org.etjen.spring_testing_tutorial.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);
    @NativeQuery("""
            SELECT *
            FROM student
            WHERE first_name = :firstName and last_name = :lastName
            """)
    Optional<Student> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
