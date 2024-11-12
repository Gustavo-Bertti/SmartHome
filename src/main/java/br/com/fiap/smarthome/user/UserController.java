package br.com.fiap.smarthome.user;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import br.com.fiap.smarthome.auth.Credentials;
import br.com.fiap.smarthome.email.dto.EmailDto;
import br.com.fiap.smarthome.user.dto.UserRequest;
import br.com.fiap.smarthome.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest, UriComponentsBuilder uriBuilder) {
        User user = userService.create(userRequest.toModel());
        EmailDto emailDto = new EmailDto(user.getEmail(), user.getName());
        rabbitTemplate.convertAndSend("email-queue", emailDto);

        var uri = uriBuilder
                .path("/user/{id}")
                .buildAndExpand(user.getUserId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(UserResponse.from(user));
    }

    @GetMapping
    public List<User> readAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> readItem(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("email")
    public UserResponse getUserByEmail(@RequestParam String email) {
        var user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserResponse.from(user);
    }

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        return userService.update(user);
    }

    @PutMapping("newPwd")
    public UserResponse updatePassword(@RequestBody Credentials credentials) {
        var user = userService.findByEmail(credentials.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userService.update(user, credentials);
        return UserResponse.from(user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.verifyUserExist(id);
        userService.deleteById(id);
    }
}
