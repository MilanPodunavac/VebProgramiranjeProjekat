package beans;

import java.util.*;

public class Administrator extends User {
	public Administrator() {
		super();
	}

	public Administrator(String username, String password, String name, String surname, Gender gender,
			Date dateOfBirth) {
		super(username, password, name, surname, gender, dateOfBirth);
	}
	
	
}