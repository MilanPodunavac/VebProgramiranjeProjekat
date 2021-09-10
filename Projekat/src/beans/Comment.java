package beans;

import java.util.*;

public class Comment {
   private String text;
   private int grade;
   private boolean approved;
   private Customer customer;
   private Restaurant restaurant;
   private boolean rejected;
   
   public Comment() {
		super();
	}
   
   public Comment(String text, int grade, boolean approved, Customer customer, Restaurant restaurant) {
		super();
		this.text = text;
		this.grade = grade;
		this.approved = approved;
		this.customer = customer;
		this.restaurant = restaurant;
		this.rejected = false;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
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
	public boolean isRejected() {
		return rejected;
	}
	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
}