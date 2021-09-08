package service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Article;
import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.DeliveryRequest;
import beans.Location;
import beans.Manager;
import beans.Restaurant;
import beans.RestaurantType;
import dao.AdministratorDao;
import dao.CommentDao;
import dao.CustomerDao;
import dao.DelivererDao;
import dao.DeliveryRequestDao;
import dao.ManagerDao;
import dao.RestaurantDao;
import serialize.AdministratorSerializer;
import serialize.CommentSerializer;
import serialize.CustomerSerializer;
import serialize.DelivererSerializer;
import serialize.DeliveryRequestSerializer;
import serialize.ManagerSerializer;
import serialize.RestaurantSerializer;

@Path("RestaurantService")
public class RestaurantService extends ServiceTemplate {
	
	public RestaurantService() {
		
	}
	
	@PostConstruct
	public void init() {
		initializeData();
	}
	
	@GET
	@Path("WorkingRestaurants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getWorkingRestaurants(){
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		return restaurantDao.getWorkingRestaurants();
	}
	
	@GET
	@Path("WorkingRestaurantsByType")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getWorkingRestaurantsByType(@QueryParam("type") RestaurantType type){
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		return restaurantDao.getWorkingRestaurantsByType(type);
	}
	
	@GET
	@Path("/getApprovedRestaurantComments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getApprovedRestaurantComments(@QueryParam("name") String name, @QueryParam("cityName") String cityName, @QueryParam("streetName") String streetName, @QueryParam("streetNumber") String streetNumber){
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		Restaurant restaurant = new Restaurant();
		restaurant.setName(name);
		Location location = new Location();
		location.setStreetName(streetName);
		location.setCityName(cityName);
		location.setStreetNumber(Integer.parseInt(streetNumber));
		restaurant.setLocation(location);
		return commentDao.getApprovedRestaurantComments(restaurant);
	}
	
	//MANAGER SERVICE
	//getRestaurantComments
	/*@GET
	@Path("/getRestaurantComments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getRestaurantComments(Restaurant restaurant){
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		return commentDao.getRestaurantComments(restaurant);
	}*/
	
/*	@GET
	@Path("/getNotApprovedRestaurantComments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getNotApprovedRestaurantComments(Restaurant restaurant){
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		return commentDao.getNotApprovedRestaurantComments(restaurant);
	}*/
	
	@GET
	@Path("RestaurantsSortedByWorking")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Restaurant> getRestaurantsSortedByWorking(){
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		return restaurantDao.getRestaurantsSortedByWorking();
	}
	
	@GET
	@Path("getRestaurantByNameAndLocation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant getSelectedRestaurant(@QueryParam("name") String name, @QueryParam("cityName") String cityName, @QueryParam("streetName") String streetName, @QueryParam("streetNumber") String streetNumber){
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		return restaurantDao.getRestaurantByNameAndLocation(name, cityName, streetName, streetNumber);
	}
	
	@GET
	@Path("getAllrestaurantComments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getAllrestaurantComments(@QueryParam("name") String name, @QueryParam("cityName") String cityName, @QueryParam("streetName") String streetName, @QueryParam("streetNumber") String streetNumber){
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		return commentDao.getRestaurantComments(restaurantDao.getRestaurantByNameAndLocation(name, cityName, streetName, streetNumber));
	}
}
