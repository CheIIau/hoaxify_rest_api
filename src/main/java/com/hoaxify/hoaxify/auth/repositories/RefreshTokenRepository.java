package com.hoaxify.hoaxify.auth.repositories;

import com.hoaxify.hoaxify.person.models.Person;
import com.hoaxify.hoaxify.auth.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    void deleteAllByPerson(Person person);
}
