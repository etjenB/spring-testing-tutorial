package org.etjen.spring_testing_tutorial.exception;

public class FirstNameLengthException extends RuntimeException {
    public FirstNameLengthException(int minLength, int maxLength) {
        super("First name has to be between " + minLength + " and " + maxLength + " characters long!");
    }
}
