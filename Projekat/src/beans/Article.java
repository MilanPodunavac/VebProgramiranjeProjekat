package beans;

import java.awt.Image;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Article {
	private String name;
	private double price;
	private ArticleType articleType;
	private int size;
	private String description;
	private String imageId;
	@JsonBackReference
	private Restaurant restaurant;
	private boolean deleted;
   
	public Article() {
		super();
	}
	
	public Article(String name, double price, ArticleType articleType, int size, String description, String imageId,
			Restaurant restaurant) {
	super();
	this.name = name;
	this.price = price;
	this.articleType = articleType;
	this.size = size;
	this.description = description;
	this.imageId = imageId;
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
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}  
}