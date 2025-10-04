package org.etjen.spring_testing_tutorial.exception;

public class LastNameBlankException extends RuntimeException {
    public LastNameBlankException() {
        super("Last name cannot be blank!");
    }
}
