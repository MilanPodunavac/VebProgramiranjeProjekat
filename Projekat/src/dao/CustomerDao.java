package dao;

import java.util.ArrayList;

import beans.Administrator;
import beans.Customer;
import beans.Delivery;

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
			if(c.getUsername().equals(username) && c.getPassword().equals(password) && !c.isDeleted()) {
				customer = c;
			}
		}
		return customer;
	}
	
	public Delivery findDelivery(String id) {
		for(Customer customer : customers) {
			for(Delivery delivery : customer.getDeliveries()) {
				if(delivery.getId().equals(id)) {
					return delivery;
				}
			}
		}
		return null;
	}
	
	public boolean IsUniqueUsername(String username) {
		for(Customer customer : customers) {
			if(customer.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}

	public void deleteCustomer(Customer customer) {
		for(Customer ctxCustomer : customers) {
			if(ctxCustomer.getUsername().equals(customer.getUsername())){
				ctxCustomer.setDeleted(true);
			}
		}
		
	}

	public Customer getCustomerByUsername(String username) {
		for(Customer customer : customers) {
			if(customer.getUsername().equals(username) && !customer.isDeleted()) {
				return customer;
			}
		}
		return null;
	}
}
