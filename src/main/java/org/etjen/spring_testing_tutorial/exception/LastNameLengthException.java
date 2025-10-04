package org.etjen.spring_testing_tutorial.exception;

public class LastNameLengthException extends RuntimeException {
    public LastNameLengthException(int minLength, int maxLength) {
        super("Last name has to be between " + minLength + " and " + maxLength + " characters long!");
    }
}
