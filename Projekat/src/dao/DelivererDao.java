package dao;

import java.util.ArrayList;

import beans.Customer;
import beans.Deliverer;
import beans.Manager;

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
	
	public Deliverer getDelivererByUsername(String username) {
		Deliverer deliverer = null;
		for(Deliverer d : deliverers) {
			if(d.getUsername().equals(username) && !d.isDeleted()) {
				deliverer = d;
			}
		}
		return deliverer;
	}
	
	public boolean IsUniqueUsername(String username) {
		for(Deliverer deliverer : deliverers) {
			if(deliverer.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}

	public void deleteDeliverer(Deliverer deliverer) {
		for(Deliverer ctxDeliverer : deliverers) {
			if(ctxDeliverer.getUsername().equals(deliverer.getUsername())){
				ctxDeliverer.setDeleted(true);
			}
		}
		
	}
}
