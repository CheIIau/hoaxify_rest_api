package com.hoaxify.hoaxify.common.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class Utils {
    public static <T extends RuntimeException> void fieldChecker(BindingResult bindingResult,
                                                                 Class<T> exceptionClass) {
        if (bindingResult.hasErrors()) {
            var errorMessage = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessage
                        .append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ");
            }

            try {
                var constructor = exceptionClass.getConstructor(String.class);
                throw constructor.newInstance(errorMessage.toString());
            } catch (Exception e) {
                // Обработка ошибки, если невозможно создать экземпляр исключения
                throw new RuntimeException("Failed to create exception", e);
            }
        }
    }
}
