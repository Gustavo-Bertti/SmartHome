package br.com.fiap.smarthome.user;

import br.com.fiap.smarthome.user.dto.UserRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User by email")
    void findByEmailCase1() {
        String email = "test@gmail.com";
        UserRequest userRequest = new UserRequest("Rafael", email, "12345678");
        this.createUser(userRequest);
        Optional<User> user = userRepository.findByEmail(email);

        assertThat(user.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User by email if user not exists")
    void findByEmailCase2() {
        String email = "test@gmail.com";
        Optional<User> user = userRepository.findByEmail(email);
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should fail trying get User by wrong email")
    void findByEmailCase3() {
        String email = "test@gmail.com";
        String wrongEmail = "wrong@gmail.com";
        UserRequest userRequest = new UserRequest("Rafael", email, "12345678");
        this.createUser(userRequest);
        Optional<User> user = userRepository.findByEmail(wrongEmail);

        assertThat(user.isPresent()).isTrue();
    }

    private void createUser(UserRequest userRequest) {
        User user = userRequest.toModel();
        this.entityManager.persist(user);
    }
}