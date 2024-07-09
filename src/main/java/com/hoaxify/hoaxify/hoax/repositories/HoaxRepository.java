package com.hoaxify.hoaxify.hoax.repositories;

import com.hoaxify.hoaxify.hoax.models.Hoax;
import com.hoaxify.hoaxify.person.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaxRepository extends JpaRepository<Hoax, Integer> {
    Page<Hoax> findByPerson(Person person, Pageable pageable);
}
