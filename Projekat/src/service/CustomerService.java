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
	@Path("/getCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@Context HttpServletRequest request) {
		return (Customer) request.getSession().getAttribute("customer");
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
	public void postComment(Comment comment) {
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		comment.setApproved(false);
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
	public void checkOut(@Context HttpServletRequest request) {
		Customer customer = (Customer)request.getSession().getAttribute("customer");
		Delivery newDelivery = new Delivery();
		newDelivery.setId(((DeliveryDao)context.getAttribute("deliveries")).generateId());
		newDelivery.setCustomer(customer);
		newDelivery.setDeliveryStatus(DeliveryStatus.processing);
		newDelivery.setRestaurant(customer.getShoppingCart().getItems().get(0).getArticle().getRestaurant());
		newDelivery.setTime(new Date());
		newDelivery.setTotalCost(customer.getDiscountedShoppingCartCost());
		customer.addPoints((int)customer.getDiscountedShoppingCartCost()/1000 * 133);
		customer.addDeliveries(newDelivery);
		customer.getShoppingCart().removeAllItems();
		new CustomerSerializer(context.getRealPath("")).Update(customer);
		((DeliveryDao)context.getAttribute("deliveries")).getDeliveries().add(newDelivery);
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		//Nisam siguran da ce se azurirati u contextu automatski, tj jel u session referenca na customera u contextu,
		//pa za svaki slucaj
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
	
/*	@GET
	@Path("/idGenTest")
	public String idGeneratorTest() {
		DeliveryDao dao = (DeliveryDao)context.getAttribute("deliveries");
		return dao.generateId();
	}*/
}
