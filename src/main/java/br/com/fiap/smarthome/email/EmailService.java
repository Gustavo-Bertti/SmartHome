package br.com.fiap.smarthome.email;

import br.com.fiap.smarthome.email.dto.EmailConsumptionDto;
import br.com.fiap.smarthome.email.dto.EmailDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

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

    @RabbitListener(queues = "consumption-queue")
    public void sendConsumptionNotify(EmailConsumptionDto emailConsumptionDto){
        boolean exceedLimitCost = false;
        StringBuilder body = new StringBuilder();
        String subject = "Energy consumption notification";

        body.append("New energy consumption recorded at ")
                .append(emailConsumptionDto.getRecordedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .append("\nTotal energy used: ")
                .append(emailConsumptionDto.getTotalEnergy()).append("kWh\n")
                .append("Cost: R$ ").append(emailConsumptionDto.getCost());

        if (emailConsumptionDto.getCost().compareTo(emailConsumptionDto.getCostLimit()) > 0){
            body.append("\nYou have exceeded your cost limit!\n")
                .append("Cost limit: R$ ").append(emailConsumptionDto.getCostLimit())
                .append("\nTotal exceeded: R$ ").append(emailConsumptionDto.getCost().subtract(emailConsumptionDto.getCostLimit()));
            exceedLimitCost = true;
        }

        if (!exceedLimitCost){
            if (emailConsumptionDto.getCost().compareTo(emailConsumptionDto.getCostLimit().divide(new BigDecimal("2"), RoundingMode.HALF_UP)) >= 0){
                body.append("\nYou have reached 50% of your cost limit!\n")
                    .append("Cost limit: R$ ").append(emailConsumptionDto.getCostLimit());
            }
        }

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailConsumptionDto.getTo());
        mail.setSubject(subject);
        mail.setText(String.valueOf(body));

        mailSender.send(mail);
    }

}
