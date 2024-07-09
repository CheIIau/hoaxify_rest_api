package com.hoaxify.hoaxify.person.constraints;


import com.hoaxify.hoaxify.common.services.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

public class ProfileImageValidator implements ConstraintValidator<ProfileImage, String> {
    @Autowired
    FileService fileService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        var decodedBytes = Base64.getDecoder().decode(value);
        var fileType = fileService.detectType(decodedBytes);
        return fileType.equalsIgnoreCase("image/png") || fileType.equalsIgnoreCase("image/jpeg");
    }
}
