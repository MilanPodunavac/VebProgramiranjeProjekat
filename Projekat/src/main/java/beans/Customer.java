package beans;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User {

	private ArrayList<Delivery> deliveries;
	private ShoppingCart shoppingCart;
	private int points;
	private CustomerType customerType;
	
	public Customer(String username, String password, String name, String surname, Gender gender, Date dateOfBirth,
			ArrayList<Delivery> deliveries, ShoppingCart shoppingCart, int points, CustomerType customerType) {
		super(username, password, name, surname, gender, dateOfBirth);
		this.deliveries = deliveries;
		this.shoppingCart = shoppingCart;
		this.points = points;
		this.customerType = customerType;
	}

	public ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(ArrayList<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
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
	
	
	
}
