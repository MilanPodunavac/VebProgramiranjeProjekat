package beans;

import java.util.*;

public class Customer extends User {
	private int points;
	private java.util.List<Delivery> deliveries;
	private CustomerType customerType;
	private ShoppingCart shoppingCart;
   
   
 
	public Customer(int points, List<Delivery> deliveries, CustomerType customerType, ShoppingCart shoppingCart) {
	   	super();
		this.points = points;
		this.deliveries = deliveries;
		this.customerType = customerType;
		this.shoppingCart = shoppingCart;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public CustomerType getCustomerType() {
		return customerType;
	}
	
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	
	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public java.util.List<Delivery> getDeliveries() {
      if (deliveries == null)
         deliveries = new java.util.Vector<Delivery>();
      return deliveries;
   }
   
	public java.util.Iterator getIteratorDeliveries() {
		if (deliveries == null)
			deliveries = new java.util.Vector<Delivery>();
		return deliveries.iterator();
	}
   
	public void setDeliveries(java.util.List<Delivery> newDeliveries) {
		removeAllDeliveries();
		for (java.util.Iterator iter = newDeliveries.iterator(); iter.hasNext();)
			addDeliveries((Delivery)iter.next());
	}
   
	public void addDeliveries(Delivery newDelivery) {
		if (newDelivery == null)
			return;
		if (this.deliveries == null)
			this.deliveries = new java.util.Vector<Delivery>();
		if (!this.deliveries.contains(newDelivery))
		{
			this.deliveries.add(newDelivery);
			newDelivery.setCustomer(this);      
		}
	}
   
	public void removeDeliveries(Delivery oldDelivery) {
		if (oldDelivery == null)
			return;
		if (this.deliveries != null)
			if (this.deliveries.contains(oldDelivery))
			{
				this.deliveries.remove(oldDelivery);
				oldDelivery.setCustomer((Customer)null);
			}
	}
   
	public void removeAllDeliveries() {
		if (deliveries != null)
		{
			Delivery oldDelivery;
			for (java.util.Iterator iter = getIteratorDeliveries(); iter.hasNext();)
			{
				oldDelivery = (Delivery)iter.next();
				iter.remove();
				oldDelivery.setCustomer((Customer)null);
			}
		}
	}

}