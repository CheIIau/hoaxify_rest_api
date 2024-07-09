package com.hoaxify.hoaxify.auth.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hoaxify.hoaxify.auth.dto.AuthDTO;
import com.hoaxify.hoaxify.auth.services.AuthService;
import com.hoaxify.hoaxify.auth.services.RefreshTokenService;
import com.hoaxify.hoaxify.person.dto.PersonDTOForAuth;
import com.hoaxify.hoaxify.common.security.JWTUtil;
import com.hoaxify.hoaxify.person.utils.exceptions.PersonNotCreatedException;
import com.hoaxify.hoaxify.common.utils.responses.CommonErrorResponse;
import com.hoaxify.hoaxify.common.utils.responses.TokensResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.hoaxify.hoaxify.common.utils.Utils.fieldChecker;
import com.hoaxify.hoaxify.person.utils.PersonConvert;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, JWTUtil jwtUtil, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokensResponse> login(@Valid @RequestBody AuthDTO authDTO) {
        try {
            var authInputToken = new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());

            //BadCredentialsException
            authenticationManager.authenticate(authInputToken);

            var tokens = this.generateTokens(authDTO.getUsername());

            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TokensResponse> createPerson(@RequestBody @Valid PersonDTOForAuth personDTOForAuth,
                                                            BindingResult bindingResult) {
        var person = PersonConvert.convertToPersonDTOForAuth(personDTOForAuth);
        fieldChecker(bindingResult, PersonNotCreatedException.class);
        authService.register(person);

        var tokens = this.generateTokens(person.getUsername());

        return new ResponseEntity<>(tokens, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody Map<String, String> request) {
        var refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.badRequest().body(new CommonErrorResponse("Refresh token is " +
                    "required"));
        }

        if (!refreshTokenService.hasRefreshToken(refreshToken)) {
            ResponseEntity.badRequest().body(new CommonErrorResponse("Invalid refresh token"));
        }

        var refreshTokenObject = refreshTokenService.getRefreshToken(refreshToken).orElse(null);

        if (refreshTokenObject == null) {
            return ResponseEntity.badRequest().body(new CommonErrorResponse("Invalid refresh " +
                    "token"));
        }
        refreshTokenService.removeRefreshToken(refreshTokenObject.getRefreshToken());

        var tokens = this.generateTokens(refreshTokenObject.getPerson().getUsername());

        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(new CommonErrorResponse("Authorization header is missing or invalid"),
                    HttpStatus.UNAUTHORIZED);
        }
        var token = authorizationHeader.substring(7);
        try {
            var username = jwtUtil.verifyTokenAndRetrieveClaim(token);
            refreshTokenService.removeAllRefreshTokens(username);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (JWTVerificationException e) {
            return new ResponseEntity<>(new CommonErrorResponse("Invalid token"), HttpStatus.BAD_REQUEST);
        }

    }

    private TokensResponse generateTokens(String username) {
        var accessToken = jwtUtil.generateToken(username);
        var refreshToken = jwtUtil.generateRefreshToken();

        refreshTokenService.addRefreshToken(username, refreshToken);

        return new TokensResponse(accessToken, refreshToken);
    }

}
