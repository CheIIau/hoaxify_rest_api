package com.hoaxify.hoaxify.person.services;

import com.hoaxify.hoaxify.common.services.FileService;
import com.hoaxify.hoaxify.person.dto.PersonDTOForUpdate;
import com.hoaxify.hoaxify.person.models.Person;
import com.hoaxify.hoaxify.person.repositories.PeopleRepository;
import com.hoaxify.hoaxify.person.utils.exceptions.PersonNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class PeopleService {
    PeopleRepository peopleRepository;
    FileService fileService;

    public PeopleService(PeopleRepository peopleRepository, FileService fileService) {
        this.peopleRepository = peopleRepository;
        this.fileService = fileService;
    }

    public Page<Person> getUsers(Pageable pageable) {
        return peopleRepository.findAll(pageable);
    }

    public Page<Person> getPeopleWithoutCurrentOne(Person loggedInUser, Pageable pageable) {
        if (loggedInUser == null) {
            throw new PersonNotFoundException();
        }
        return peopleRepository.findByUsernameNot(loggedInUser.getUsername(), pageable);
    }

    public Person getByUsername(String username) {
        var person = peopleRepository.findByUsername(username);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return person.get();
    }

    public Person getById(int id) {
        var person = peopleRepository.findById(id);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User with id " + id + " is not found");
        }
        return person.get();
    }

    @Transactional
    public void update(int id, PersonDTOForUpdate personToBeUpdated) throws IOException {
        System.out.println(personToBeUpdated);
        var person = peopleRepository.findById(id).orElse(null);
        if (person == null) {
            throw new PersonNotFoundException();
        }
        var displayName = personToBeUpdated.getDisplayName();
        if (displayName != null && !displayName.isEmpty()) {
            person.setDisplayName(displayName);
        }
        var avatar = personToBeUpdated.getAvatar();
        if (avatar != null) {
            var savedImageName = fileService.saveProfileImage(avatar);
            fileService.deleteProfileImage(person.getAvatar());
            person.setAvatar(savedImageName);
        }
        if (avatar != null && avatar.isEmpty()) {
            person.setAvatar(null);
        }
        peopleRepository.save(person);
    }
}
