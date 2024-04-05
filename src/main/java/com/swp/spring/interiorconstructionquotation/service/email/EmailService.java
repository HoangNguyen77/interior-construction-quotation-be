package com.swp.spring.interiorconstructionquotation.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailSerivce {
    private JavaMailSender emailSender;
    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public void sendEmail(String from, String to, String subject, String text) {
        // MimeMailSender => co dinh kem tep, file
        // SimpleMailSender => chi co text
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("<html><body>" + text.replace("\n", "<br>") + "</body></html>", true);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

        emailSender.send(message);
    }
}
