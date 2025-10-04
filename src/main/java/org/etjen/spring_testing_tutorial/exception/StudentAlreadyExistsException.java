package org.etjen.spring_testing_tutorial.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String email) {
        super("Student with email = " + email + " already exists!");
    }
}
