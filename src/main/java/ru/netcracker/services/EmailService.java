package ru.netcracker.services;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public interface EmailService {
    void sendSimpleEmail(String toAddress, String subject, String message);
}
