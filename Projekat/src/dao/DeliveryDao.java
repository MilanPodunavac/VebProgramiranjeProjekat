package dao;

import java.util.ArrayList;
import java.util.List;

import beans.Delivery;
import beans.DeliveryStatus;

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
	
	public String generateId() {
		int max = 0;
		String retVal = "0000000000";
		for(Delivery delivery : deliveries) {
			if(max < Integer.parseInt(delivery.getId())) {
				max = Integer.parseInt(delivery.getId());
			}
		}
		max += 1;
		String id = String.valueOf(max);
		retVal = retVal.substring(0, retVal.length() - id.length());
		retVal = retVal.concat(id);
		return retVal;
	}
	
	public List<Delivery> getWaitingDeliveries(){
		List<Delivery> waitingDeliveries = new ArrayList<Delivery>();
		for(Delivery delivery : deliveries) {
			if(delivery.getDeliveryStatus() == DeliveryStatus.waitingDelivery)waitingDeliveries.add(delivery);
		}
		return waitingDeliveries;
	}
	
}
