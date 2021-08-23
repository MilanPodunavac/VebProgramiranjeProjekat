package dao;

import java.util.ArrayList;


import beans.Restaurant;
import beans.RestaurantType;

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
	
	public ArrayList<Restaurant> getWorkingRestaurants(){
		ArrayList<Restaurant> workingRestaurants = new ArrayList<Restaurant>();
		for(Restaurant restaurant : restaurants) {
			if(restaurant.isWorking() && !restaurant.isDeleted())
				workingRestaurants.add(restaurant);
		}
		return workingRestaurants;
	}
	
	public ArrayList<Restaurant> getWorkingRestaurantsByType(RestaurantType type){
		ArrayList<Restaurant> workingRestaurants = new ArrayList<Restaurant>();
		for(Restaurant restaurant : restaurants) {
			if(restaurant.isWorking() && !restaurant.isDeleted() && restaurant.getRestaurantType() == type)
				workingRestaurants.add(restaurant);
		}
		return workingRestaurants;
	}
	
}
