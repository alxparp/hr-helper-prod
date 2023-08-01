package com.hrsol.helper.service;

import com.hrsol.helper.model.NotificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class EmailService {

    private final static Logger LOGGER = Logger.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Async
    public void send(NotificationRequest notification) {
        try {
            LOGGER.info("Sending email...");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            helper.setText(notification.getText(), true);
            helper.setTo(notification.getTo());
            helper.setSubject(notification.getSubject());
            helper.setFrom(notification.getFrom());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

}
