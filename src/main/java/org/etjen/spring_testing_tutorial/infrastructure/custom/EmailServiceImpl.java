package org.etjen.spring_testing_tutorial.infrastructure.custom;

import org.etjen.spring_testing_tutorial.infrastructure.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String to, String title, String content) {
        System.out.println("TO: " + to);
        System.out.println("TITLE: " + title);
        System.out.println("CONTENT: " + content);
    }
}
