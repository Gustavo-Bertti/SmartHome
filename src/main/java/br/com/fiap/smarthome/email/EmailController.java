package br.com.fiap.smarthome.email;

import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("email")

public class EmailController {
    
    private final EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }
    
    @PostMapping
    public String sendEmail(@RequestBody @Valid Email email) {
        try {
            emailService.sendEmailText(email.getTo(), email.getSubject(), email.getBody());
            return "Email enviado para " + email.getTo();
        } catch (Exception e) {
            return "Falha no envio" + e.getLocalizedMessage();
        }
        
    }    
    
}
