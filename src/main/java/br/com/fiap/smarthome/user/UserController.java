package br.com.fiap.smarthome.user;

import static org.springframework.http.HttpStatus.CREATED;

import br.com.fiap.smarthome.email.dto.EmailDto;
import br.com.fiap.smarthome.user.dto.UserRequest;
import br.com.fiap.smarthome.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("user")
@Tag(name = "Users", description = "User-related endpoint")
public class UserController {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    public UserController(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public User create(@RequestBody @Valid UserRequest userRequest, UriComponentsBuilder uriBuilder) {
        User user = userService.create(userRequest.toModel());
        EmailDto emailDto = new EmailDto(userRequest.email(), userRequest.name());
        rabbitTemplate.convertAndSend("email-queue", emailDto);

        return user;
    }
}
