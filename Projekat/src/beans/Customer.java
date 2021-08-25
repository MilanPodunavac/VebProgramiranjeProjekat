package beans;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Customer extends User {
	private int points;
	@JsonManagedReference
	private java.util.List<Delivery> deliveries;
	private CustomerType customerType;
	@JsonManagedReference
	private ShoppingCart shoppingCart;

	public Customer() {
		super();
	}
	
	public Customer(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			int points, List<Delivery> deliveries, CustomerType customerType, ShoppingCart shoppingCart) {
		super(username, password, name, surname, gender, dateOfBirth);
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
   
	/*public java.util.Iterator getIteratorDeliveries() {
		if (deliveries == null)
			deliveries = new java.util.Vector<Delivery>();
		return deliveries.iterator();
	}*/
   
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
			for (java.util.Iterator iter = deliveries.iterator(); iter.hasNext();)
			{
				oldDelivery = (Delivery)iter.next();
				iter.remove();
				oldDelivery.setCustomer((Customer)null);
			}
		}
	}
	
	public boolean addPoints(int points) {
		this.points += points;
		return customerType.changeCustomerType(this.points);
	}
	
	public boolean removePoints(int points) {
		this.points -= points;
		return customerType.changeCustomerType(this.points);
	}
	
	public double getDiscountedShoppingCartCost() {
		return shoppingCart.getTotalCost() * (1.0 - customerType.getDiscount()/100);
	}
	
	public double getDiscountedPrice(double price) {
		return price * (1.0 - customerType.getDiscount()/100);
	}
	
	public double getDiscount() {
		return customerType.getDiscount();
	}

}