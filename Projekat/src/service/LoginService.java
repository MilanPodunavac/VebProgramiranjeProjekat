package service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Administrator;
import beans.Article;
import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.Manager;
import beans.Restaurant;
import beans.ShoppingCart;
import beans.User;
import beans.CustomerType;
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
import serialize.UsernameChecker;

@Path("")
public class LoginService {
	@Context
	private ServletContext context;
	
	public LoginService() {
		
	}
	
	@PostConstruct
	public void init() {
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
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request) {
		CustomerDao customers = (CustomerDao)context.getAttribute("customers");
		AdministratorDao administrators = (AdministratorDao)context.getAttribute("administrators");
		DelivererDao deliverers = (DelivererDao)context.getAttribute("deliverers");
		ManagerDao managers = (ManagerDao)context.getAttribute("managers");
		Customer customer = customers.find(user.getUsername(), user.getPassword());
		Manager manager = managers.find(user.getUsername(), user.getPassword());
		Administrator administrator = administrators.find(user.getUsername(), user.getPassword());
		Deliverer deliverer = deliverers.find(user.getUsername(), user.getPassword());
		if(customer != null) {
			request.getSession().setAttribute("customer", customer);
			return Response.status(200).entity("customer").build();
		}
		else if(manager != null) {
			request.getSession().setAttribute("manager", manager);
			return Response.ok("manager").build();
		}
		else if(deliverer != null) {
			request.getSession().setAttribute("deliverer", deliverer);
			return Response.status(200).entity("deliverer").build();
		}
		else if(administrator != null) {
			request.getSession().setAttribute("administrator", administrator);
			return Response.status(200).entity("administrator").build();
		}
		return Response.status(400).entity("Invalid username and/or password").build();
	}
	
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User getCurrentUser(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
		//doraditi?
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user, @Context HttpServletRequest request) {
		UsernameChecker checker = new UsernameChecker();
		if(checker.Check(user.getUsername(), context.getRealPath(""))){
			Customer newCustomer = new Customer(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getDateOfBirth(),
					0, new ArrayList<Delivery>(), null, new ShoppingCart());
			newCustomer.setCustomerType(new CustomerType("Bronze", 0, 1000));
			newCustomer.getShoppingCart().setCustomer(newCustomer);
			new CustomerSerializer(context.getRealPath("")).Add(newCustomer, context.getRealPath(""));
			CustomerDao customers = (CustomerDao)context.getAttribute("customers");
			customers.getCustomers().add(newCustomer);
			return Response.status(200).entity("You have been successfully registered").build();
		}
		else {
			return Response.status(400).entity("Username already exists").build();
		}
	}
	
}
