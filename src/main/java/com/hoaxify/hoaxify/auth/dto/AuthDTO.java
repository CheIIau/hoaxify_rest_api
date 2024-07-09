package com.hoaxify.hoaxify.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public class AuthDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    private String password;

    public @NotEmpty(message = "Имя не должно быть пустым") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Имя не должно быть пустым") String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Пароль не должен быть пустым") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Пароль не должен быть пустым") String password) {
        this.password = password;
    }
}
