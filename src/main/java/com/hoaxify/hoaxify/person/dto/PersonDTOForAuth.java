package com.hoaxify.hoaxify.person.dto;

import com.hoaxify.hoaxify.person.constraints.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PersonDTOForAuth {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @UniqueUsername
    private String username;

    @NotEmpty(message = "Отображаемое имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Отображаемое имя должно быть от 2 до 100 символов длиной")
    private String displayName;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 5, max = 100, message = "Пароль должен быть от 5 до 100 символов длиной")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "В пароле должны быть хотя" +
            " бы 1 заглавная буква, строчная и одна цифра ")
    private String password;

    public @NotEmpty(message = "Имя не должно быть пустым") @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Имя не должно быть пустым") @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной") String username) {
        this.username = username;
    }

    public @NotEmpty(message = "Отображаемое имя не должно быть пустым") @Size(min = 2, max = 100, message = "Отображаемое имя должно быть от 2 до 100 символов длиной") String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@NotEmpty(message = "Отображаемое имя не должно быть пустым") @Size(min = 2, max = 100, message = "Отображаемое имя должно быть от 2 до 100 символов длиной") String displayName) {
        this.displayName = displayName;
    }

    public @NotEmpty(message = "Пароль не должен быть пустым") @Size(min = 5, max = 100, message = "Пароль должен быть от 5 до 100 символов длиной") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "В пароле должны быть хотя" +
            " бы 1 заглавная буква, строчная и одна цифра ") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Пароль не должен быть пустым") @Size(min = 5, max = 100, message = "Пароль должен быть от 5 до 100 символов длиной") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "В пароле должны быть хотя" +
            " бы 1 заглавная буква, строчная и одна цифра ") String password) {
        this.password = password;
    }
}
