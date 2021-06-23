package beans;

import java.util.ArrayList;
import java.util.Date;

public class Deliverer extends User {

	private ArrayList<Delivery> deliveries;

	public Deliverer(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			ArrayList<Delivery> deliveries) {
		super(username, password, name, surname, gender, dateOfBirth);
		this.deliveries = deliveries;
	}

	public ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(ArrayList<Delivery> deliveries) {
		this.deliveries = deliveries;
	}
	
	
}
