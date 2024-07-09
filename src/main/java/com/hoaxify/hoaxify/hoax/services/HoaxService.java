package com.hoaxify.hoaxify.hoax.services;

import com.hoaxify.hoaxify.common.security.PersonDetails;
import com.hoaxify.hoaxify.hoax.models.Hoax;
import com.hoaxify.hoaxify.hoax.repositories.HoaxRepository;
import com.hoaxify.hoaxify.person.services.PeopleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HoaxService {

    HoaxRepository hoaxRepository;
    PeopleService peopleService;

    public HoaxService(HoaxRepository hoaxRepository, PeopleService peopleService) {
        this.hoaxRepository = hoaxRepository;
        this.peopleService = peopleService;
    }

    @Transactional
    public void save(Hoax hoax, PersonDetails user) {
        hoax.setTimestamp(LocalDateTime.now());
        hoax.setPerson(user.getPerson());
        hoaxRepository.save(hoax);
    }

    public Page<Hoax> getAllHoaxes(Pageable pageable) {
        return hoaxRepository.findAll(pageable);
    }

    public Page<Hoax> getHoaxesByPerson(String username, Pageable pageable) {
        var person = peopleService.getByUsername(username);
        return hoaxRepository.findByPerson(person, pageable);
    }
}
