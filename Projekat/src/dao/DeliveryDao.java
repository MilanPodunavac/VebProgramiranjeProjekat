package dao;

import java.util.ArrayList;

import beans.Delivery;

public class DeliveryDao {
	private ArrayList<Delivery> deliveries;

	public DeliveryDao(ArrayList<Delivery> deliveries) {
		super();
		this.deliveries = deliveries;
	}

	public ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(ArrayList<Delivery> deliveries) {
		this.deliveries = deliveries;
	}
	
}
