package com.hoaxify.hoaxify.person.utils.exceptions;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException(String msg) {
        super(msg);
    }
}
