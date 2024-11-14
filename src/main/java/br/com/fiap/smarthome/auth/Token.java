package br.com.fiap.smarthome.auth;

import br.com.fiap.smarthome.user.User;

public record Token(String token, User user) {
}
