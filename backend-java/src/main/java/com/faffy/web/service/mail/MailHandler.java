package com.faffy.web.service.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class MailHandler {
    private final JavaMailSender sender;
    private final MimeMessage mimeMessage;
    private final MimeMessageHelper mimeMessageHelper;

    public MailHandler(JavaMailSender javaMailSender) throws MessagingException, UnsupportedEncodingException {
        this.sender = javaMailSender;
        this.mimeMessage = javaMailSender.createMimeMessage();
        this.mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        this.mimeMessage.setFrom(new InternetAddress("kimsm9822@naver.com","FAFFY"));
    }

    public void setFrom(String fromAddress) throws MessagingException, UnsupportedEncodingException {
        mimeMessageHelper.setFrom(fromAddress);
    }

    public void setTo(String email) throws MessagingException {
        mimeMessageHelper.setTo(email);
    }

    public void setSubject(String subject) throws MessagingException {
        mimeMessageHelper.setSubject(subject);
    }

    public void setText(String text, boolean useHtml) throws MessagingException {
        mimeMessageHelper.setText(text, useHtml);
    }

    public void setAttach(String fileName, String path) throws MessagingException, IOException {
        File file = new ClassPathResource(path).getFile();
        FileSystemResource fsr = new FileSystemResource(file);

        mimeMessageHelper.addAttachment(fileName, fsr);
    }

    public void setInline(String fileName, String path) throws MessagingException, IOException {
        File file = new ClassPathResource(path).getFile();
        FileSystemResource fileSystemResource = new FileSystemResource(file);

        mimeMessageHelper.addInline(fileName, fileSystemResource);
    }

    public void send() {
        sender.send(mimeMessage);
    }
}
