package com.hoaxify.hoaxify.person.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProfileImageValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProfileImage {
    String message() default "Разрешены только PNG и JPG файлы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
