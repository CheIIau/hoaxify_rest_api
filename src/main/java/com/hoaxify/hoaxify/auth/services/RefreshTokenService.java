package com.hoaxify.hoaxify.auth.services;

import com.hoaxify.hoaxify.auth.models.RefreshToken;
import com.hoaxify.hoaxify.person.repositories.PeopleRepository;
import com.hoaxify.hoaxify.auth.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, PeopleRepository peopleRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.peopleRepository = peopleRepository;
    }

    public boolean hasRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken).isPresent();
    }

    @Transactional
    public void addRefreshToken(String username, String refreshToken) {
        var person =
                peopleRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not " +
                        "found: " + username));

        RefreshToken token = new RefreshToken();
        token.setRefreshToken(refreshToken);
        token.setPerson(person);
        refreshTokenRepository.save(token);
    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    @Transactional
    public void removeRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    @Transactional
    public void removeAllRefreshTokens(String username) {
        var person =
                peopleRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not " +
                        "found: " + username));

        refreshTokenRepository.deleteAllByPerson(person);
    }
}