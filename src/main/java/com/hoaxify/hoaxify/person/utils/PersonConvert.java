package com.hoaxify.hoaxify.person.utils;

import com.hoaxify.hoaxify.person.dto.PersonDTOForAuth;
import com.hoaxify.hoaxify.person.dto.PersonDTOForGetUsers;
import com.hoaxify.hoaxify.person.models.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonConvert {

    private static ModelMapper modelMapper;

    @Autowired
    public PersonConvert(ModelMapper modelMapper) {
        PersonConvert.modelMapper = modelMapper;
    }

    public static PersonDTOForGetUsers convertPersonToPersonDTOForUserGet(Person person) {
        return modelMapper.map(person, PersonDTOForGetUsers.class);
    }

    public static Person convertToPersonDTOForAuth(PersonDTOForAuth personDTOForAuth) {
        return modelMapper.map(personDTOForAuth, Person.class);
    }
}