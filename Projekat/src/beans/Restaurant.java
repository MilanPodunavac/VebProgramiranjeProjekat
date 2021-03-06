package beans;

import java.awt.Image;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Restaurant {
	private String name;
	private boolean working;
	private String imageId;
	private boolean deleted;
	private RestaurantType restaurantType;
	@JsonManagedReference
	private java.util.List<Article> articles;
	private Location location;
   
   
	public Restaurant() {
		super();
	}
   
   
	public Restaurant(String name, boolean working, String imageId, RestaurantType restaurantType,
			List<Article> articles, Location location) {
		super();
		this.name = name;
		this.working = working;
		this.imageId = imageId;
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
	
	/*public java.util.Iterator getIteratorArticles() {
      if (articles == null)
         articles = new java.util.Vector<Article>();
      return articles.iterator();
	}*/
   
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
			for (java.util.Iterator iter = articles.iterator(); iter.hasNext();)
			{
				oldArticle = (Article)iter.next();
				iter.remove();
				oldArticle.setRestaurant((Restaurant)null);
			}
		}
	}
	
	public boolean checkLocation(Restaurant restaurant) {
		return restaurant.getLocation().getCityName().equals(location.getCityName())
				&& restaurant.getLocation().getStreetNumber() == location.getStreetNumber()
				&& restaurant.getLocation().getStreetName().equals(location.getStreetName());
	}
	
	public Article findArticle(Article testArticle) {
		for(Article article : articles) {
			if(article.getName().equals(testArticle.getName())) {
				return article;
			}
		}
		return testArticle;
	}

}