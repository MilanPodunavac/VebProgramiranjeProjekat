package beans;

import java.util.*;

public class ShoppingCart {
	private double totalCost;
	private Customer customer;
   
	public ShoppingCart(double totalCost, Customer customer) {
		super();
		this.totalCost = totalCost;
		this.customer = customer;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
   
   

}