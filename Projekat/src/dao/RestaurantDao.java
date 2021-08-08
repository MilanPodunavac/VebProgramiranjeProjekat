package dao;

import java.util.ArrayList;


import beans.Restaurant;

public class RestaurantDao {
	private ArrayList<Restaurant> restaurants;

	public RestaurantDao(ArrayList<Restaurant> restaurants) {
		super();
		this.restaurants = restaurants;
	}

	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(ArrayList<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	
	public Restaurant find(Restaurant restaurant) {//ZA IZMENU (KAKO IDENTIFIKOVATI BOLJE (JEDINSTVENO) RESTORAN?)
		for(Restaurant iterRestaurant : restaurants) {
			if(restaurant.getName().equals(iterRestaurant.getName())){
				return iterRestaurant;
			}
		}
		return null;
	}
	
}
