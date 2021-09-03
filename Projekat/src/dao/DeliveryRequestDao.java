package dao;

import java.util.ArrayList;
import java.util.List;

import beans.Delivery;
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
	
	public void declineAllDeliveryRequests(Delivery delivery) {
		for(DeliveryRequest deliveryRequest : deliveryRequests) {
			if(deliveryRequest.getDeliveryId().equals(deliveryRequest.getDeliveryId())) {
				deliveryRequest.setPending(false);
			}
		}
	}
	
	public List<DeliveryRequest> getPendingDeliveryRequestsForManager(Manager manager){
		List<DeliveryRequest> pendingRequests = new ArrayList<DeliveryRequest>();
		for(DeliveryRequest deliveryRequest : deliveryRequests) {
			if(deliveryRequest.getManagerUsername().equals(manager.getUsername())) {
				pendingRequests.add(deliveryRequest);
			}
		}
		return pendingRequests;
	}
	
}
