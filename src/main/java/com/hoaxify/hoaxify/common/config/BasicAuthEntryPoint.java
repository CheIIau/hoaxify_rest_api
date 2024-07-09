package com.hoaxify.hoaxify.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoaxify.hoaxify.common.utils.responses.CommonErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BasicAuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    //нужно чтобы переопределить стандартную ошибку при неудачной аутентификации
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //тут нужно обрабатывать все ошибки видимо
        response.setContentType("application/json");
        var status = response.getStatus();

        if (status == 200 || status == HttpServletResponse.SC_UNAUTHORIZED) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            var errorResponse = new CommonErrorResponse(authException.getMessage());
            var jsonResponse = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
        }

    }
}
