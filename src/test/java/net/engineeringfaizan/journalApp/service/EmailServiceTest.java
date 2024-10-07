package net.engineeringfaizan.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.mailSender("ff2697589@gmail.com" , "Checking mail sender" , "ha bhai kesa dea ni dea acha");
    }
}
