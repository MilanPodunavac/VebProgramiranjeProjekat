package beans;

import java.awt.Image;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Restaurant {
	private String name;
	private boolean working;
	private Image logo;
	private boolean deleted;
	private RestaurantType restaurantType;
	@JsonManagedReference
	private java.util.List<Article> articles;
	private Location location;
   
   
	public Restaurant() {
		super();
	}
   
   
	public Restaurant(String name, boolean working, Image logo, RestaurantType restaurantType,
			List<Article> articles, Location location) {
		super();
		this.name = name;
		this.working = working;
		this.logo = logo;
		this.deleted = false;
		this.restaurantType = restaurantType;
		this.articles = articles;
		this.location = location;
   }
   
   
   
   public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public boolean isWorking() {
		return working;
	}
	
	
	
	public void setWorking(boolean working) {
		this.working = working;
	}
	
	
	
	public Image getLogo() {
		return logo;
	}



	public void setLogo(Image logo) {
		this.logo = logo;
	}



	public boolean isDeleted() {
		return deleted;
	}



	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}



	public RestaurantType getRestaurantType() {
		return restaurantType;
	}
	
	
	
	public void setRestaurantType(RestaurantType restaurantType) {
		this.restaurantType = restaurantType;
	}



	public Location getLocation() {
		return location;
	}
	
	
	
	public void setLocation(Location location) {
		this.location = location;
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
		{
			this.articles.add(newArticle);
			newArticle.setRestaurant(this);      
		}
	}
   
	public void removeArticles(Article oldArticle) {
		if (oldArticle == null)
			return;
		if (this.articles != null)
			if (this.articles.contains(oldArticle))
			{
				this.articles.remove(oldArticle);
				oldArticle.setRestaurant((Restaurant)null);
			}
	}
   
	public void removeAllArticles() {
		if (articles != null)
		{
			Article oldArticle;
			for (java.util.Iterator iter = getIteratorArticles(); iter.hasNext();)
			{
				oldArticle = (Article)iter.next();
				iter.remove();
				oldArticle.setRestaurant((Restaurant)null);
			}
		}
	}

}