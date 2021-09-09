package beans;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ShoppingCart {
	private double totalCost;
	@JsonBackReference
   	private Customer customer;
   	private java.util.List<ShoppingCartItem> items;

   	public ShoppingCart() {
   		super();
   		items = new ArrayList<ShoppingCartItem>();
   		totalCost = 0;
   	}
   	
	public ShoppingCart(double totalCost, Customer customer, List<ShoppingCartItem> items) {
	super();
	this.totalCost = totalCost;
	this.customer = customer;
	this.items = items;
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
	
	public java.util.List<ShoppingCartItem> getItems() {
		if (items == null)
			items = new java.util.Vector<ShoppingCartItem>();
		return items;
	}
	   
/*	public java.util.Iterator getIteratorArticles() {
		if (articles == null)
			articles = new java.util.Vector<Article>();
		return articles.iterator();
	}*/
	   
	public void setItems(java.util.List<ShoppingCartItem> newItems) {
		removeAllItems();
/*			for (java.util.Iterator iter = newArticles.iterator(); iter.hasNext();)
		addArticles((Article)iter.next());*/
		for(ShoppingCartItem item : newItems) {
			addItem(item);
		}
	}
	   
	public void addItem(ShoppingCartItem newItem) {
		if (newItem == null)
			return;
		if (this.items == null)
			this.items = new java.util.Vector<ShoppingCartItem>();
		for(ShoppingCartItem item : items) {
			if(item.getArticle().getName().equals(newItem.getArticle().getName())) {
				totalCost += item.getArticle().getPrice();
				item.setAmount(newItem.getAmount());
				return;
			}
		}		
		{
			this.items.add(newItem);
			totalCost += newItem.getAmount() * newItem.getArticle().getPrice();
		}
			
	}
	   
	public void removeItem(ShoppingCartItem newItem) {
		if (newItem == null)
			return;
		if (this.items != null)
			for(ShoppingCartItem item : items) {
				if(item.getArticle().getName().equals(newItem.getArticle().getName())) {
					totalCost -= item.getArticle().getPrice();
					if(newItem.getAmount() == 0) {
						items.remove(item);
					}
					else {
						item.setAmount(newItem.getAmount());
					}
					return;
				}
			}	
	}
	   
	public void removeAllItems() {
		if (items != null) {
			items.clear();
			totalCost = 0;
		}
	}

}