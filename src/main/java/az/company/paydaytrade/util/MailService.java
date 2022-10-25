package az.company.paydaytrade.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendSimpleMessage(SimpleMailMessage email){
        javaMailSender.send(email);
    }

    @Async
    public void sendEmail(String to, String subject,String from , String text){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setFrom(from);
        mailMessage.setText(text);
        sendSimpleMessage(mailMessage);

    }




}
