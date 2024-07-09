package com.hoaxify.hoaxify.person.dto;

import com.hoaxify.hoaxify.person.constraints.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PersonDTOForGetUsers {
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @UniqueUsername
    private String username;

    @NotEmpty(message = "Отображаемое имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Отображаемое имя должно быть от 2 до 100 символов длиной")
    private String displayName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

}
