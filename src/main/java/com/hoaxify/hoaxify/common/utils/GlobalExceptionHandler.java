package com.hoaxify.hoaxify.common.utils;

import com.hoaxify.hoaxify.hoax.utils.exceptions.HoaxNotCreated;
import com.hoaxify.hoaxify.person.utils.exceptions.*;
import com.hoaxify.hoaxify.common.utils.responses.CommonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //global

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonErrorResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        return new CommonErrorResponse("Resource not found");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        return new CommonErrorResponse("Access Denied");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonErrorResponse handleAuthenticationException(AuthenticationException e) {
        return new CommonErrorResponse("Full authentication is required to access this resource");
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public CommonErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        return new CommonErrorResponse(e.getMessage());
//    }
// лучше это обрабатывать на уровне методов в классе конечно
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonErrorResponse handleIOException(IOException e) {
        return new CommonErrorResponse(e.getMessage());
    }

    //auth
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private CommonErrorResponse handleException(final PersonNotCreatedException e) {
        return new CommonErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private CommonErrorResponse handleException(final PersonSQLException e) {
        return new CommonErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private CommonErrorResponse handleException(final PersonAlreadyExistsException e) {
        return new CommonErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private CommonErrorResponse handleException(final BadCredentialsException e) {
        return new CommonErrorResponse(e.getMessage());
    }

    //person

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private CommonErrorResponse handleException(final PersonNotFoundException e) {
        return new CommonErrorResponse("Current user is not found");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private CommonErrorResponse handleException(final UsernameNotFoundException e) {
        return new CommonErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private CommonErrorResponse handleException(final PersonNotUpdated e) {
        return new CommonErrorResponse(e.getMessage());
    }

    //hoaxes

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private CommonErrorResponse handleException(final HoaxNotCreated e) {
        return new CommonErrorResponse(e.getMessage());
    }
}