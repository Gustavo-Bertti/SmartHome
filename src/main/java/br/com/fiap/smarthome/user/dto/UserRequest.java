package br.com.fiap.smarthome.user.dto;

import br.com.fiap.smarthome.user.User;

public record UserRequest(String name, String email, String password) {

    public User toModel(){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

}
