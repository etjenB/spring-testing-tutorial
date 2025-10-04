package org.etjen.spring_testing_tutorial.infrastructure.custom;

import org.etjen.spring_testing_tutorial.infrastructure.EmailService;

public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String to, String title, String content) {
        System.out.println("TO: " + to);
        System.out.println("TITLE: " + title);
        System.out.println("CONTENT: " + content);
    }
}
