package beans;

import java.util.*;

public class Delivery {
   private String id;
   private Date time;
   private double totalCost;
   private DeliveryStatus deliveryStatus;   
   private Customer customer;
   
   
   
   
   public String getId() {
	   return id;
   }

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}
	
	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Customer getCustomer() {
      return customer;
	}
   
   public void setCustomer(Customer newCustomer) {
      if (this.customer == null || !this.customer.equals(newCustomer))
      {
         if (this.customer != null)
         {
            Customer oldCustomer = this.customer;
            this.customer = null;
            oldCustomer.removeDeliveries(this);
         }
         if (newCustomer != null)
         {
            this.customer = newCustomer;
            this.customer.addDeliveries(this);
         }
      }
   }

}