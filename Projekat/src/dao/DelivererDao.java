package dao;

import java.util.ArrayList;

import beans.Deliverer;

public class DelivererDao {
	private ArrayList<Deliverer> deliverers;

	public DelivererDao(ArrayList<Deliverer> deliverers) {
		super();
		this.deliverers = deliverers;
	}

	public ArrayList<Deliverer> getDeliverers() {
		return deliverers;
	}

	public void setDeliverers(ArrayList<Deliverer> deliverers) {
		this.deliverers = deliverers;
	}

	public Deliverer find(String username, String password) {
		Deliverer deliverer = null;
		for(Deliverer d : deliverers) {
			if(d.getUsername().equals(username) && d.getPassword().equals(password)) {
				deliverer = d;
			}
		}
		return deliverer;
	}
}
