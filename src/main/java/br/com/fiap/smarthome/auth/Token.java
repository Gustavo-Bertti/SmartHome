package br.com.fiap.smarthome.auth;

public record Token(Long userId, String token, String email) {
}
