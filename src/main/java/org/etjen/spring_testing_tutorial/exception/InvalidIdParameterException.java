package org.etjen.spring_testing_tutorial.exception;

public class InvalidIdParameterException extends RuntimeException {
    public InvalidIdParameterException() {
        super("Invalid ID was provided!");
    }
}
