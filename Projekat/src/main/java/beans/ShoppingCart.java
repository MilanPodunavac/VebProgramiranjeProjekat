package beans;

import java.util.ArrayList;

public class ShoppingCart {

	private ArrayList<Article> articles;
	private Customer customer;
	private double totalCost;
	
	public ShoppingCart(ArrayList<Article> articles, Customer customer, double totalCost) {
		super();
		this.articles = articles;
		this.customer = customer;
		this.totalCost = totalCost;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	
}
