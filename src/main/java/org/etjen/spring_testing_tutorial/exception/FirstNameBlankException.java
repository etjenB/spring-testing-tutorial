package org.etjen.spring_testing_tutorial.exception;

public class FirstNameBlankException extends RuntimeException {
    public FirstNameBlankException() {
        super("First name cannot be blank!");
    }
}
