package ru.netcracker.services;

import ru.netcracker.entities.User;
import ru.netcracker.thowable.FileStorageException;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileService {

	public String readFromInputStream(InputStream inputStream) {
		StringBuilder resultStringBuilder = new StringBuilder("");
		try (BufferedReader br
				     = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append(", ");
			}
		} catch (IOException e) {
			throw new FileStorageException("File not read");
		}
		return resultStringBuilder.toString();
	}

	public User createUserFromFile(String userString) {
		String[] userField = userString.split(", ");
		User user = new User();

		if (userField.length > 0) {
			user.setFirName(userField[0]);
			user.setMidlName(userField[1]);
			user.setLastName(userField[2]);
			user.setEmail(userField[3]);
			user.setAge(Integer.parseInt(userField[4]));
			user.setSalary(Integer.parseInt(userField[5]));
			user.setPlaceWork(userField[6]);
		}

		return user;
	}
}