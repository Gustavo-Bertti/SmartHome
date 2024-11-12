package br.com.fiap.smarthome.user.dto;

import br.com.fiap.smarthome.user.User;

public record UserResponse(String name, String email) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getName(),
                user.getEmail());
    }

}
