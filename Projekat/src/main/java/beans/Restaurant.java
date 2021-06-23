package beans;

import java.awt.Image;
import java.util.ArrayList;

enum RestaurantType{
	Italian,
	Chinese,
	Barbecue,
	Japanese,
	Thai,
	McDonalds,
	KFC,
	Seafood,
	Fastfood
}

public class Restaurant {

	private String name;
	private RestaurantType restaurantType;
	private ArrayList<Article> articles;
	private boolean working;
	private Location location;
	private Image logo;
	private boolean deleted;
	
	public Restaurant(String name, RestaurantType restaurantType, ArrayList<Article> articles, boolean working,
			Location location, Image logo) {
		super();
		this.name = name;
		this.restaurantType = restaurantType;
		this.articles = articles;
		this.working = working;
		this.location = location;
		this.logo = logo;
		this.deleted = false;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RestaurantType getRestaurantType() {
		return restaurantType;
	}

	public void setRestaurantType(RestaurantType restaurantType) {
		this.restaurantType = restaurantType;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Article> articles) {
		this.articles = articles;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Image getLogo() {
		return logo;
	}

	public void setLogo(Image logo) {
		this.logo = logo;
	}
	
	
	
}
