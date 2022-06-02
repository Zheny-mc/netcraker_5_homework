package com.baeldung.crud.thowable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class InvalidEmail extends Exception {
	public InvalidEmail(String message) {
		super(message);
	}
}
