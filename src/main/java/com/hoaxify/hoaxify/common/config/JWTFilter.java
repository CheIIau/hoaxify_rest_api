package com.hoaxify.hoaxify.common.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoaxify.hoaxify.common.security.JWTUtil;
import com.hoaxify.hoaxify.common.security.PersonDetailsService;
import com.hoaxify.hoaxify.common.utils.responses.CommonErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTFilter(JWTUtil jwtUtil, PersonDetailsService personDetailsService) {
        this.jwtUtil = jwtUtil;
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var jwt = authHeader.substring(7);

            if (!jwt.isBlank()) {
                try {
                    var username = jwtUtil.verifyTokenAndRetrieveClaim(jwt);
                    var userDetails = personDetailsService.loadUserByUsername(username);
                    var authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException e) {
//     при запросе с невалидным токеном на незащищенные эндпоинты, если запросе будет возвращать
//     ошибку, getWriter будет выполняться в
//     этом методе и при попытке записать ошибку (2ошибки типа), в итоге будет ошибка на сервера,
//     юзеру будет
//     приходить 500ая. можно либо подумать, как решить, либо раскомментить строку ниже и
//     закомменить код
//                    System.out.println(e.getMessage());
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    var errorResponse = new CommonErrorResponse(e.getMessage());
                    var jsonResponse = objectMapper.writeValueAsString(errorResponse);
                    response.getWriter().write(jsonResponse);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
