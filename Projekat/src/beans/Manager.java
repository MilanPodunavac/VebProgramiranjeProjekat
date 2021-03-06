package beans;

import java.util.*;

public class Manager extends User {
	private Restaurant restaurant;
	
	public Manager() {
		super();
	}
	
	public Manager(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			Restaurant restaurant) {
		super(username, password, name, surname, gender, dateOfBirth);
		this.restaurant = restaurant;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}