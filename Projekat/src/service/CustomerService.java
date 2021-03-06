package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import beans.DeliveryStatus;
import beans.Manager;
import beans.Restaurant;
import beans.ShoppingCart;
import beans.ShoppingCartItem;
import beans.User;
import dao.AdministratorDao;
import dao.CommentDao;
import dao.CustomerDao;
import dao.DelivererDao;
import dao.DeliveryDao;
import dao.DeliveryRequestDao;
import dao.ManagerDao;
import dao.RestaurantDao;
import dto.PasswordChangeDTO;
import serialize.AdministratorSerializer;
import serialize.CommentSerializer;
import serialize.CustomerSerializer;
import serialize.DelivererSerializer;
import serialize.DeliveryRequestSerializer;
import serialize.ManagerSerializer;
import serialize.RestaurantSerializer;

@Path("CustomerService")
public class CustomerService extends ServiceTemplate {
	
	public CustomerService() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}
	
	@GET
	@Path("/getCustomerByUsername")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomerByUsername(@QueryParam("username") String username) {
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		return customerDao.getCustomerByUsername(username);
	}
	
	@GET
	@Path("/getCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@Context HttpServletRequest request) {
		return (Customer) request.getSession().getAttribute("customer");
	}
	
	@POST
	@Path("/changeCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changeCustomer(Customer customer, @Context HttpServletRequest request) {
		Customer loggedCustomer = (Customer) request.getSession().getAttribute("customer");
		if(loggedCustomer == null)return;
		loggedCustomer.setUsername(customer.getUsername());
		loggedCustomer.setName(customer.getName());
		loggedCustomer.setSurname(customer.getSurname());
		loggedCustomer.setGender(customer.getGender());
		loggedCustomer.setDateOfBirth(customer.getDateOfBirth());
		new CustomerSerializer(context.getRealPath("")).Save((ArrayList<Customer>)((CustomerDao)context.getAttribute("customers")).getCustomers());
	}
	
	@POST
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(PasswordChangeDTO dto, @Context HttpServletRequest request) {
		Customer loggedCustomer = (Customer) request.getSession().getAttribute("customer");
		if(loggedCustomer == null)return "Error";
		if(!loggedCustomer.getPassword().equals(dto.getOldPassword())) {
			return "Invalid current password!";
		}
		loggedCustomer.setPassword(dto.getNewPassword());
		new CustomerSerializer(context.getRealPath("")).Save((ArrayList<Customer>)((CustomerDao)context.getAttribute("customers")).getCustomers());
		return "Password changed!";
	}
	
	@POST
	@Path("/addItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart addItem(ShoppingCartItem item, @Context HttpServletRequest request) {
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		if(customer == null)return null;
		customer.getShoppingCart().addItem(item);
		updateCustomer(customer);
		return customer.getShoppingCart();
	}
	
	@POST
	@Path("/removeItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShoppingCart removeItem(ShoppingCartItem item, @Context HttpServletRequest request) {
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		if(customer == null)return null;
		customer.getShoppingCart().removeItem(item);
		updateCustomer(customer);
		return customer.getShoppingCart();
	}
	
	@POST
	@Path("/postComment")
	@Consumes(MediaType.APPLICATION_JSON)
	public void postComment(@QueryParam("name") String name, @QueryParam("cityName") String cityName, @QueryParam("streetName") String streetName, @QueryParam("streetNumber") String streetNumber, Comment comment, @Context HttpServletRequest request) {
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		Customer customer = (Customer)request.getSession().getAttribute("customer");
		comment.setCustomer(customer);
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		comment.setRestaurant(restaurantDao.getRestaurantByNameAndLocation(name, cityName, streetName, streetNumber));
		commentDao.addComment(comment);
		CommentSerializer commentSerializer = new CommentSerializer(context.getRealPath(""));
		commentSerializer.Add(comment);
	}

	private void updateCustomer(Customer customer) {
		CustomerSerializer ser = new CustomerSerializer(context.getRealPath(""));
		ser.Update(customer);
	}
	
	@POST
	@Path("/checkOut")
	@Consumes(MediaType.APPLICATION_JSON)
	public void checkOut(Restaurant restaurant, @Context HttpServletRequest request) {
		Customer customer = (Customer)request.getSession().getAttribute("customer");
		Delivery newDelivery = new Delivery();
		newDelivery.setId(((DeliveryDao)context.getAttribute("deliveries")).generateId());
		newDelivery.setCustomer(customer);
		newDelivery.setDeliveryStatus(DeliveryStatus.processing);
		newDelivery.setRestaurant(restaurant);
		newDelivery.setTime(new Date());
		newDelivery.setTotalCost(customer.calculateDiscountedShoppingCartCost());
		newDelivery.setItems(new ArrayList<>(customer.getShoppingCart().getItems()));
		customer.addPoints((int)customer.calculateDiscountedShoppingCartCost()/1000 * 133);
		customer.addDeliveries(newDelivery);
		customer.getShoppingCart().removeAllItems();
		new CustomerSerializer(context.getRealPath("")).Update(customer);
		((DeliveryDao)context.getAttribute("deliveries")).getDeliveries().add(newDelivery);
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		for(Customer ctxCustomer : customerDao.getCustomers()) {
			if(customer.getUsername().equals(ctxCustomer.getUsername())) {
				ctxCustomer = customer;
			}
		}
	}
	
	@POST
	@Path("/cancelDelivery")
	@Consumes(MediaType.APPLICATION_JSON)
	public void CancelDelivery(Delivery delivery, @Context HttpServletRequest request) {
		DeliveryDao deliveryDao = (DeliveryDao)context.getAttribute("deliveries");
		for(Delivery ctxDelivery : deliveryDao.getDeliveries()) {
			if(delivery.getId().equals(ctxDelivery.getId())) {
				ctxDelivery.setDeliveryStatus(DeliveryStatus.cancelled);
				ctxDelivery.getCustomer().removePoints((int)ctxDelivery.getTotalCost()/1000 * 133 * 4);
				new CustomerSerializer(context.getRealPath("")).Update(ctxDelivery.getCustomer());
			}
		}
	}
	
	@GET
	@Path("/getReviewableRestaurants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Restaurant> getReviewableRestaurants(@Context HttpServletRequest request){
		List<Restaurant> reviewableRestaurants = new ArrayList<Restaurant>();
		Customer customer = (Customer)request.getSession().getAttribute("customer");
		for(Delivery delivery : customer.getDeliveries()) {
			if(delivery.getDeliveryStatus() == DeliveryStatus.delivered) {
				boolean exists = false;
				for(Restaurant restaurant : reviewableRestaurants) {
					if(restaurant.getName().equals(delivery.getRestaurant().getName())
							&& restaurant.checkLocation(delivery.getRestaurant()) && !restaurant.isDeleted()) {
						exists = true;
						break;
					}
				}
				if(!exists) {
					reviewableRestaurants.add(delivery.getRestaurant());
				}
			}
		}
		return reviewableRestaurants;
	}
	
	@GET
	@Path("/getInProcessingDeliveries")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Delivery> getInProcessingDeliveries(@Context HttpServletRequest request) {
		List<Delivery> deliveries = new ArrayList<Delivery>();
		for(Delivery delivery : getCustomer(request).getDeliveries()) {
			if(delivery.getDeliveryStatus() == DeliveryStatus.processing)deliveries.add(delivery);
		}
		return deliveries;
	}
	
	@POST
	@Path("/clearShoppingCart")
	@Consumes(MediaType.APPLICATION_JSON)
	public void clearShoppingCart(@Context HttpServletRequest request) {
		Customer customer = (Customer)request.getSession().getAttribute("customer");
		customer.getShoppingCart().removeAllItems();
		new CustomerSerializer(context.getRealPath("")).Update(customer);
	}
	
}
