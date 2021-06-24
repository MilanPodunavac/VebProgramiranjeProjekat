package beans;

import java.awt.Image;
import java.util.*;

public class Article {
	private String name;
	private double price;
	private ArticleType articleType;
	private int size;
	private String description;
	private Image picture;
	private Restaurant restaurant;
	private boolean deleted;
   
	public Article(String name, double price, ArticleType articleType, int size, String description, Image picture,
			Restaurant restaurant) {
	super();
	this.name = name;
	this.price = price;
	this.articleType = articleType;
	this.size = size;
	this.description = description;
	this.picture = picture;
	this.restaurant = restaurant;
	deleted = false;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant newRestaurant) {
		if (this.restaurant == null || !this.restaurant.equals(newRestaurant))
		{
			if (this.restaurant != null)
			{
				Restaurant oldRestaurant = this.restaurant;
				this.restaurant = null;
				oldRestaurant.removeArticles(this);
			}
			if (newRestaurant != null)
			{
				this.restaurant = newRestaurant;
				this.restaurant.addArticles(this);
			}
		}
	}
   
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public ArticleType getArticleType() {
		return articleType;
	}
	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Image getPicture() {
		return picture;
	}
	public void setPicture(Image picture) {
		this.picture = picture;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}  
}