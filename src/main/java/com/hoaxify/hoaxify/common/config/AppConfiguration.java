package com.hoaxify.hoaxify.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "hoaxify")
public class AppConfiguration {
    String uploadPath;
    String avatarsFolder = "profile";
    String attachmentsFolder = "attachments";

    public String getAvatarsFolder() {
        return avatarsFolder;
    }

    public void setAvatarsFolder(String avatarsFolder) {
        this.avatarsFolder = avatarsFolder;
    }

    public String getAttachmentsFolder() {
        return attachmentsFolder;
    }

    public void setAttachmentsFolder(String attachmentsFolder) {
        this.attachmentsFolder = attachmentsFolder;
    }

    public String getFullAvatarsFolderPath() {
        return uploadPath + "/" + avatarsFolder;
    }

    public String getFullAttachmentsFolderPath() {
        return this.uploadPath + "/" + attachmentsFolder;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppConfiguration that = (AppConfiguration) o;
        return Objects.equals(uploadPath, that.uploadPath) && Objects.equals(avatarsFolder, that.avatarsFolder) && Objects.equals(attachmentsFolder, that.attachmentsFolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uploadPath, avatarsFolder, attachmentsFolder);
    }

}
