package com.hoaxify.hoaxify.person.dto;

import com.hoaxify.hoaxify.person.constraints.ProfileImage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonDTOForUpdate {
    @Size(min = 2, max = 100, message = "Отображаемое имя должно быть от 2 до 100 символов длиной")
    private String displayName;

    @ProfileImage
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if (avatar == null || avatar.trim().isEmpty()) {
            this.avatar = "";
            return;
        }
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            this.displayName = "";
            return;
        }
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "PersonDTOForUpdate{" +
                "displayName='" + displayName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
