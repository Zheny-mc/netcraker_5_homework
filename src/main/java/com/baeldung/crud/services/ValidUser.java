package com.baeldung.crud.services;

import com.baeldung.crud.entities.User;
import com.baeldung.crud.thowable.InvalidEmail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUser {
	public static boolean validEmail(User user) throws InvalidEmail {
		boolean result = true;
		Pattern pattern = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
		Matcher matcher = pattern.matcher(user.getEmail());

		if (!matcher.matches()) {
			throw new InvalidEmail("Error: " + user.getEmail());
		}

		return result;
	}
}
