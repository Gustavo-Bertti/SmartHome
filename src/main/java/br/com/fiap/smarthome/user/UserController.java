package br.com.fiap.smarthome.user;

import static org.springframework.http.HttpStatus.CREATED;

import br.com.fiap.smarthome.email.dto.EmailDto;
import br.com.fiap.smarthome.user.dto.UserRequest;
import br.com.fiap.smarthome.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("user")
@Tag(name = "Users", description = "User-related endpoint")
public class UserController {

    private final UserService userService;
    private final UserRepository repository ;
    private final RabbitTemplate rabbitTemplate;

    public UserController(UserService userService, RabbitTemplate rabbitTemplate, UserRepository repository) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserResponse create(@RequestBody @Valid UserRequest userRequest) {
        User user = userService.create(userRequest.toModel());
        EmailDto emailDto = new EmailDto(userRequest.email(), userRequest.name());
        rabbitTemplate.convertAndSend("email-queue", emailDto);

        return UserResponse.from(user);
    }

    @GetMapping
    public List<User> getAll() {
        return repository.findAll();
    }

}
