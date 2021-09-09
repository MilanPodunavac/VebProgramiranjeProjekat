package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import beans.Article;
import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.DeliveryRequest;
import beans.Manager;
import beans.Restaurant;
import beans.ShoppingCartItem;
import dao.AdministratorDao;
import dao.CommentDao;
import dao.CustomerDao;
import dao.DelivererDao;
import dao.DeliveryDao;
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

public class ServiceTemplate {
	@Context
	protected ServletContext context;
	
	protected void initializeData() {
		if(context.getAttribute("restaurants") == null) {
			List<Restaurant> restaurants = new RestaurantSerializer(context.getRealPath("")).Load();
			for(Restaurant restaurant : restaurants) {
				for(Article article : restaurant.getArticles()) {
					article.setRestaurant(restaurant);
				}
			}
			context.setAttribute("restaurants", new RestaurantDao((ArrayList<Restaurant>)restaurants));
		}
		if(context.getAttribute("customers") == null) {
		//	context.setAttribute("customers", new CustomerDao(new CustomerSerializer(context.getRealPath("")).Load()));
			List<Customer> customers = new CustomerSerializer(context.getRealPath("")).Load();
			RestaurantDao restaurantDao = (RestaurantDao)(context.getAttribute("restaurants"));
			for(Customer customer : customers) {
				for(Delivery delivery : customer.getDeliveries()) {
					delivery.setCustomer(customer);
					delivery.setRestaurant(restaurantDao.find(delivery.getRestaurant()));
				}
				customer.getShoppingCart().setCustomer(customer);
			}
			context.setAttribute("customers", new CustomerDao((ArrayList<Customer>)customers));
			
		}
		if(context.getAttribute("administrators") == null) {
			context.setAttribute("administrators", new AdministratorDao(new AdministratorSerializer(context.getRealPath("")).Load()));
		}
		if(context.getAttribute("deliverers") == null) {
			context.setAttribute("deliverers", new DelivererDao(new DelivererSerializer(context.getRealPath("")).Load()));
			List<Deliverer> deliverers = new DelivererSerializer(context.getRealPath("")).Load();
			CustomerDao customerDao = (CustomerDao)(context.getAttribute("customers"));
			for(Deliverer deliverer : deliverers) {
				for(Delivery delivery : deliverer.getDeliveries()) {
					Delivery ctxDelivery = customerDao.findDelivery(delivery.getId());
					deliverer.getDeliveries().remove(delivery);
					deliverer.getDeliveries().add(ctxDelivery);
				}
			}
			context.setAttribute("deliverers", new DelivererDao((ArrayList<Deliverer>)deliverers));
		}
		if(context.getAttribute("deliveries") == null) {
			CustomerDao customerDao = (CustomerDao)(context.getAttribute("customers"));
			RestaurantDao restaurantDao = (RestaurantDao)(context.getAttribute("restaurants"));
			ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
			for(Customer customer : customerDao.getCustomers()) {
				for(Delivery delivery : customer.getDeliveries()) {
					deliveries.add(delivery);
					for(ShoppingCartItem shoppingCartItem : delivery.getItems()) {
						shoppingCartItem.setArticle(restaurantDao.find(delivery.getRestaurant()).findArticle(shoppingCartItem.getArticle()));
					}
				}
			}
			context.setAttribute("deliveries", new DeliveryDao(deliveries));
		}
		if(context.getAttribute("managers") == null) {
			List<Manager> managers = new ManagerSerializer(context.getRealPath("")).Load();
			for(Manager manager : managers) {
				if(manager.getRestaurant() != null) {
					Restaurant savedRestaurant = ((RestaurantDao)context.getAttribute("restaurants")).find(manager.getRestaurant());
					if(savedRestaurant != null) {
						manager.setRestaurant(savedRestaurant);
					}
				}
			}
			context.setAttribute("managers", new ManagerDao((ArrayList<Manager>)managers));			
		}
		if(context.getAttribute("comments") == null) {
			List<Comment> comments = new CommentSerializer(context.getRealPath("")).Load();
			
			for(Comment comment : comments) {
				comment.setCustomer(((CustomerDao)context.getAttribute("customers")).find(comment.getCustomer().getUsername(), comment.getCustomer().getPassword()));
				comment.setRestaurant(((RestaurantDao)context.getAttribute("restaurants")).find(comment.getRestaurant()));
			}
			context.setAttribute("comments", new CommentDao((ArrayList<Comment>)comments));
		}
		if(context.getAttribute("deliveryRequests") == null) {
			List<DeliveryRequest> deliveryRequests = new DeliveryRequestSerializer(context.getRealPath("")).Load();
			context.setAttribute("deliveryRequests", new DeliveryRequestDao((ArrayList<DeliveryRequest>)deliveryRequests));
		}
	}
}
