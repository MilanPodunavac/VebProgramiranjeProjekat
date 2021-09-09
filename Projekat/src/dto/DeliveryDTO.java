package dto;

import java.util.ArrayList;
import java.util.Date;

import beans.Customer;
import beans.Delivery;
import beans.DeliveryStatus;
import beans.Restaurant;
import beans.ShoppingCartItem;

public class DeliveryDTO {
	private String id;
	private Date time;
	private double totalCost;
	private DeliveryStatus deliveryStatus; 
	private Customer customer;
	private Restaurant restaurant;
	private ArrayList<ShoppingCartItem> items;
	public DeliveryDTO(Delivery delivery) {
		super();
		id = delivery.getId();
		time = delivery.getTime();
		totalCost = delivery.getTotalCost();
		deliveryStatus = delivery.getDeliveryStatus();
		customer = delivery.getCustomer();
		restaurant = delivery.getRestaurant();
		items = delivery.getItems();
	}
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
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public ArrayList<ShoppingCartItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<ShoppingCartItem> items) {
		this.items = items;
	}
	
	
}
