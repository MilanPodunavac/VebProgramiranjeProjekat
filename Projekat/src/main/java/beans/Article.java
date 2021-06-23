package beans;

import java.awt.Image;

enum ArticleType{
	Drink,
	Food
}

public class Article {

	private String name;
	private double price;
	private ArticleType articleType;
	private Restaurant restaurant;
	private int size;
	private String description;
	private Image picture;
	
	public Article(String name, double price, ArticleType articleType, Restaurant restaurant, int size,
			String description, Image picture) {
		super();
		this.name = name;
		this.price = price;
		this.articleType = articleType;
		this.restaurant = restaurant;
		this.size = size;
		this.description = description;
		this.picture = picture;
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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
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
	
	
	
}
