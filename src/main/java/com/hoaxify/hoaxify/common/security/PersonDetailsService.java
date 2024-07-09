package com.hoaxify.hoaxify.common.security;

import com.hoaxify.hoaxify.person.repositories.PeopleRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public PersonDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //первая версия кода
//        var person = peopleRepository.findByUsername(username);
//        if (person.isEmpty()) {
//            throw new UsernameNotFoundException("User is not found: " + username);
//        }
//        return new PersonDetails(person.get());
        return peopleRepository.findByUsername(username)
                .map(PersonDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found: " + username));
    }
}
