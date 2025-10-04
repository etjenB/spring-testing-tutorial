package org.etjen.spring_testing_tutorial.infrastructure;

public interface EmailService {
    void sendEmail(String to, String title, String content);
}
