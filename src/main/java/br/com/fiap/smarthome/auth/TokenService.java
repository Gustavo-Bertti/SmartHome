package br.com.fiap.smarthome.auth;

import br.com.fiap.smarthome.user.User;
import br.com.fiap.smarthome.user.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public static final Algorithm ALGORITHM = Algorithm.HMAC256("signature");

    public final UserRepository userRepository;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Token generateToken(String email){

        var expirationAt = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3));
        String token = JWT.create()
                .withSubject(email)
                .withExpiresAt(expirationAt)
                .withIssuer("Marketplace")
                .sign(ALGORITHM);

        return new Token(token, email);
    }

    public User getUserFromToken(String token) {
        var email = JWT.require(ALGORITHM)
                .withIssuer("SmartHome")
                .build()
                .verify(token)
                .getSubject();

        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

}
