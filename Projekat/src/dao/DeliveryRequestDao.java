package dao;

import java.util.ArrayList;

import beans.DeliveryRequest;
import beans.Manager;

public class DeliveryRequestDao {
	private ArrayList<DeliveryRequest> deliveryRequests;

	public DeliveryRequestDao(ArrayList<DeliveryRequest> deliveryRequests) {
		super();
		this.deliveryRequests = deliveryRequests;
	}

	public ArrayList<DeliveryRequest> getDeliveryRequests() {
		return deliveryRequests;
	}

	public void setDeliveryRequests(ArrayList<DeliveryRequest> deliveryRequests) {
		this.deliveryRequests = deliveryRequests;
	}
	
}
