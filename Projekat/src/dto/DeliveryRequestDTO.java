package dto;

import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.Manager;

public class DeliveryRequestDTO {
	private Deliverer deliverer;
	private Delivery delivery;
	private Customer customer;
	public DeliveryRequestDTO(Deliverer deliverer, Delivery delivery, Customer customer) {
		super();
		this.deliverer = deliverer;
		this.delivery = delivery;
		this.customer = customer;
	}
	public Deliverer getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(Deliverer deliverer) {
		this.deliverer = deliverer;
	}
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
