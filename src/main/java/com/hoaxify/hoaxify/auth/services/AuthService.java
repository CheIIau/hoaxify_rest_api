package com.hoaxify.hoaxify.auth.services;

import com.hoaxify.hoaxify.person.models.Person;
import com.hoaxify.hoaxify.person.repositories.PeopleRepository;
import com.hoaxify.hoaxify.person.utils.exceptions.PersonSQLException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {

        var encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        try {
            peopleRepository.save(person);
        } catch (Exception e) {
            throw new PersonSQLException(e.getMessage());
        }
    }

}
