package beans;

import java.util.ArrayList;
import java.util.Date;

enum DeliveryStatus{
	Processing,
	Preparation,
	WaitingDelivery,
	InDelivery,
	Delivered,
	Cancelled
}

public class Delivery {

	private String id; // 10 chars, unique
	private ArrayList<Article> articles;
	private Restaurant restaurant;
	private Date time;
	private double totalCost;
	private Customer customer;
	private DeliveryStatus deliveryStatus;
	
	public Delivery(String id, ArrayList<Article> articles, Restaurant restaurant, Date time, double totalCost,
			Customer customer, DeliveryStatus deliveryStatus) {
		super();
		this.id = id;
		this.articles = articles;
		this.restaurant = restaurant;
		this.time = time;
		this.totalCost = totalCost;
		this.customer = customer;
		this.deliveryStatus = deliveryStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	
	
}
