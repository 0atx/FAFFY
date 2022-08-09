package com.faffy.web.service;

import com.faffy.web.service.mail.MailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    JavaMailSender emailSender;

    public static final String ePw = createKey();

    private static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    @Override
    public String sendMsg(String to) throws Exception {
//        MimeMessage message = createTempPwdMsg(to);
        MailHandler mailHandler = new MailHandler(emailSender);
        mailHandler.setTo(to);
        mailHandler.setSubject("<FAFFY> 임시 비밀번호 입니다");

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<div style='text-align: center'><img src='cid:logo-img'/><br></div>";
        msgg+= "<h1> 안녕하세요 FAFFY입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>임시 비밀번호를 보내드립니다.<p>";
        msgg+= "<p>이 비밀번호로 로그인 하시기 바랍니다.<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>임시 비밀번호 입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "password : <strong>";
        msgg+= ePw+"</strong><div>";
        msgg+= "</div>";

        mailHandler.setText(msgg, true);
        mailHandler.setInline("logo-img", "static/FAFFY.png");
        try {
            mailHandler.send();
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
//        try{
//            emailSender.send(message);
//        }catch(MailException es){
//            es.printStackTrace();
//            throw new IllegalArgumentException();
//        }
        return ePw;
    }

//        private MimeMessage createTempPwdMsg(String to)throws Exception{
//        System.out.println("보내는 대상 : "+ to);
//        System.out.println("임시 비밀번호 : "+ePw);
//        MimeMessage message = emailSender.createMimeMessage();
//
//        message.addRecipients(RecipientType.TO, to);//보내는 대상
//        message.setSubject("<FAFFY> 임시 비밀번호 입니다");//제목
//
//        String msgg="";
//        msgg+= "<div style='margin:100px;'>";
//        msgg+= "<img src='cid:logo-img'/><br>";
//        msgg+= "<h1> 안녕하세요 FAFFY입니다. </h1>";
//        msgg+= "<br>";
//        msgg+= "<p>임시 비밀번호를 보내드립니다.<p>";
//        msgg+= "<p>이 비밀번호로 로그인 하시기 바랍니다.<p>";
//        msgg+= "<br>";
//        msgg+= "<p>감사합니다!<p>";
//        msgg+= "<br>";
//        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
//        msgg+= "<h3 style='color:blue;'>임시 비밀번호 입니다.</h3>";
//        msgg+= "<div style='font-size:130%'>";
//        msgg+= "password : <strong>";
//        msgg+= ePw+"</strong><div><br/> ";
//        msgg+= "</div>";
//
//        //메일에 로고 이미지 추가
//        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
//        File file = new ClassPathResource("static/FAFFY.png").getFile();
//        System.out.println(file.getAbsolutePath());
//        FileSystemResource fsr = new FileSystemResource(file);
//        messageHelper.addInline("logo-img", fsr);
//
//        message.setText(msgg, "utf-8", "html");//내용
//        message.setFrom(new InternetAddress("admin@faffy.com","FAFFY"));//보내는 사람
//
//        return message;
//    }
}
