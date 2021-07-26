package dao;

import java.util.ArrayList;

import beans.Administrator;
import beans.Customer;

public class CustomerDao {
	private ArrayList<Customer> customers;

	public CustomerDao(ArrayList<Customer> customers) {
		super();
		this.customers = customers;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
	public Customer find(String username, String password) {
		Customer customer = null;
		for(Customer c : customers) {
			if(c.getUsername().equals(username) && c.getPassword().equals(password)) {
				customer = c;
			}
		}
		return customer;
	}
}
