package service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Article;
import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.Manager;
import beans.Restaurant;
import beans.User;
import dao.AdministratorDao;
import dao.CommentDao;
import dao.CustomerDao;
import dao.DelivererDao;
import dao.ManagerDao;
import dao.RestaurantDao;
import serialize.AdministratorSerializer;
import serialize.CommentSerializer;
import serialize.CustomerSerializer;
import serialize.DelivererSerializer;
import serialize.ManagerSerializer;
import serialize.RestaurantSerializer;

@Path("Customer")
public class CustomerService {

	@Context
	private ServletContext context;
	
	public CustomerService() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}

	private void initializeData() {
		if(context.getAttribute("customers") == null) {
		//	context.setAttribute("customers", new CustomerDao(new CustomerSerializer(context.getRealPath("")).Load()));
			List<Customer> customers = new CustomerSerializer(context.getRealPath("")).Load();
			for(Customer customer : customers) {
				for(Delivery delivery : customer.getDeliveries()) {
					delivery.setCustomer(customer);
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
					delivery = customerDao.findDelivery(delivery.getId());
				}
			}
			context.setAttribute("deliverers", new DelivererDao((ArrayList<Deliverer>)deliverers));
		}
		if(context.getAttribute("restaurants") == null) {
			List<Restaurant> restaurants = new RestaurantSerializer(context.getRealPath("")).Load();
			for(Restaurant restaurant : restaurants) {
				for(Article article : restaurant.getArticles()) {
					article.setRestaurant(restaurant);
				}
			}
			context.setAttribute("restaurants", new RestaurantDao((ArrayList<Restaurant>)restaurants));
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
			context.setAttribute("comments", new CommentDao(new CommentSerializer(context.getRealPath("")).Load()));
		}
	}
	
	@GET
	@Path("/getCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@Context HttpServletRequest request) {
		return (Customer) request.getSession().getAttribute("customer");
	}
}
