package br.com.fiap.smarthome.email;

import br.com.fiap.smarthome.email.dto.EmailDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService (JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmailText(String to, String subject, String body){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            mailSender.send(simpleMailMessage);
    }

    @RabbitListener(queues = "email-queue")
    public void sendEmail(EmailDto emailDto){

        String subject = "Account register";
        String body = "Account successfully created, Welcome to SmartHome " + emailDto.getName() + "!\nNow you can start registering your devices" ;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailDto.getTo());
        mail.setSubject(subject);
        mail.setText(body);

        mailSender.send(mail);
    }

}
