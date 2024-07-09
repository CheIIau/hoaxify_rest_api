package com.hoaxify.hoaxify.person.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Такое имя уже существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
