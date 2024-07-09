package com.hoaxify.hoaxify.person.constraints;

import com.hoaxify.hoaxify.person.repositories.PeopleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        // по дефолту валидатор срабатывает дважды, сначала перед тем, как передать в
        // PersonController, а второй раз hibernate перед тем как занести в бд. в первом случае мы
        // инжектим userRepository и все окей, во втором hibernate создает инстанс класса сам и
        // не инжектит, поэтому будет нулл, можно либо сделать так, либо отключить валидатор
        // hibernate spring.jpa.properties.javax.persistence.validation.mode=none сделал 2 варик
        // в итоге
        // if (userRepository == null) {
        //     return true;
        // }
        return peopleRepository.findByUsername(value).isEmpty();
    }
}
