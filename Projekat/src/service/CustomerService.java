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
import dao.ManagerDao;
import dao.RestaurantDao;
import serialize.AdministratorSerializer;
import serialize.CommentSerializer;
import serialize.CustomerSerializer;
import serialize.DelivererSerializer;
import serialize.ManagerSerializer;
import serialize.RestaurantSerializer;

@Path("CustomerService")
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
		if(context.getAttribute("deliveries") == null) {
			CustomerDao customerDao = (CustomerDao)(context.getAttribute("customers"));
			ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
			for(Customer customer : customerDao.getCustomers()) {
				for(Delivery delivery : customer.getDeliveries()) {
					deliveries.add(delivery);
				}
			}
			context.setAttribute("deliveries", new DeliveryDao(deliveries));
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
