package dto;

import beans.Manager;
import beans.Restaurant;

public class RestaurantCreationDTO {
	private Restaurant restaurant;
	private Manager manager;
	
	public RestaurantCreationDTO() {
		
	}
	
	public RestaurantCreationDTO(Restaurant restaurant, Manager manager) {
		super();
		this.restaurant = restaurant;
		this.manager = manager;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	
}
