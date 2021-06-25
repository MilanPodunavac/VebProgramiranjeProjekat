package beans;

import java.util.*;

public class ShoppingCart {
	private double totalCost;
   	private Customer customer;
   	private java.util.List<Article> articles;

	public ShoppingCart(double totalCost, Customer customer, List<Article> articles) {
	super();
	this.totalCost = totalCost;
	this.customer = customer;
	this.articles = articles;
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
   
	public java.util.List<Article> getArticles() {
		if (articles == null)
			articles = new java.util.Vector<Article>();
		return articles;
	}
	   
	public java.util.Iterator getIteratorArticles() {
		if (articles == null)
			articles = new java.util.Vector<Article>();
		return articles.iterator();
	}
	   
	public void setArticles(java.util.List<Article> newArticles) {
		removeAllArticles();
			for (java.util.Iterator iter = newArticles.iterator(); iter.hasNext();)
		addArticles((Article)iter.next());
	}
	   
	public void addArticles(Article newArticle) {
		if (newArticle == null)
			return;
		if (this.articles == null)
			this.articles = new java.util.Vector<Article>();
		if (!this.articles.contains(newArticle))
			this.articles.add(newArticle);
	}
	   
	public void removeArticles(Article oldArticle) {
		if (oldArticle == null)
			return;
		if (this.articles != null)
			if (this.articles.contains(oldArticle))
				this.articles.remove(oldArticle);
	}
	   
	public void removeAllArticles() {
		if (articles != null)
			articles.clear();
	}

}