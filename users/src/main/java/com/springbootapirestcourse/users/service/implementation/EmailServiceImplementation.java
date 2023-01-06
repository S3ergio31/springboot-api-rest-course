package com.springbootapirestcourse.users.service.implementation;

import com.springbootapirestcourse.users.service.EmailService;
import com.springbootapirestcourse.users.shared.email.AmazonSES;
import com.springbootapirestcourse.users.shared.email.Message;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailServiceImplementation implements EmailService {
    private final AmazonSES amazonSES = new AmazonSES();
    @Override
    public void send(Message message) {
        try {
            amazonSES.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
