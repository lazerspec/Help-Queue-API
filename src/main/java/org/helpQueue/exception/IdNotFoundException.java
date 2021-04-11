package org.helpQueue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(long id) {
        super("No entry found for id: " + id);
    }
}
