package com.hoaxify.hoaxify.common.services;

import com.hoaxify.hoaxify.common.config.AppConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {
    AppConfiguration appConfiguration;

    Tika tika;

    public FileService(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
        tika = new Tika();
    }

    public String saveProfileImage(String base64Image) throws IOException {
        String imageName = UUID.randomUUID().toString().replaceAll("-", "");

        var decodedBytes = Base64.getDecoder().decode(base64Image);
        var target = new File(appConfiguration.getFullAvatarsFolderPath() + "/" + imageName);
        FileUtils.writeByteArrayToFile(target, decodedBytes);
        return imageName;
    }

    public void deleteProfileImage(String image) throws IOException {
        Files.deleteIfExists(Paths.get(appConfiguration.getFullAvatarsFolderPath() + "/" + image));
    }

    public String detectType(byte[] fileArr) {
        return tika.detect(fileArr);
    }
}
