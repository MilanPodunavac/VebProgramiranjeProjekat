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
	
	public Restaurant find(Restaurant restaurant) {
		for(Restaurant iterRestaurant : restaurants) {
			if(restaurant.getName().equals(iterRestaurant.getName()) 
					&& restaurant.checkLocation(iterRestaurant) && !iterRestaurant.isDeleted()){
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
	
	public ArrayList<Restaurant> getClosedRestaurants(){
		ArrayList<Restaurant> closedRestaurants = new ArrayList<Restaurant>();
		for(Restaurant restaurant : restaurants) {
			if(!restaurant.isWorking() && !restaurant.isDeleted())
				closedRestaurants.add(restaurant);
		}
		return closedRestaurants;
	}
	
	public ArrayList<Restaurant> getWorkingRestaurantsByType(RestaurantType type){
		ArrayList<Restaurant> workingRestaurants = new ArrayList<Restaurant>();
		for(Restaurant restaurant : restaurants) {
			if(restaurant.isWorking() && !restaurant.isDeleted() && restaurant.getRestaurantType() == type)
				workingRestaurants.add(restaurant);
		}
		return workingRestaurants;
	}
	
	public ArrayList<Restaurant> getRestaurantsSortedByWorking(){
		ArrayList<Restaurant> restaurants = getWorkingRestaurants();
		restaurants.addAll(getClosedRestaurants());
		return restaurants;
	}
	
	public Restaurant getRestaurantByNameAndLocation(String name, String cityName, String streetName, String streetNumber) {
		for(Restaurant restaurant : restaurants) {
			if(restaurant.getName().equals(name) && 
					restaurant.getLocation().getCityName().equals(cityName) &&
					restaurant.getLocation().getStreetName().equals(streetName) &&
					String.valueOf(restaurant.getLocation().getStreetNumber()).equals(streetNumber) &&
					!restaurant.isDeleted()) {
				return restaurant;
			}
		}
		return null;
	}

	public void deleteRestaurant(Restaurant restaurant) {
		for(Restaurant ctxRestaurant : restaurants) {
			if(restaurant.getName().equals(ctxRestaurant.getName()) && ctxRestaurant.checkLocation(restaurant)) {
				ctxRestaurant.setDeleted(true);
			}
		}
		
	}
	
}
