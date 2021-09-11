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
import beans.ArticleType;
import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.DeliveryRequest;
import beans.DeliveryStatus;
import beans.Gender;
import beans.Location;
import beans.Manager;
import beans.Restaurant;
import beans.RestaurantType;
import beans.ShoppingCart;
import beans.ShoppingCartItem;
import beans.User;
import beans.CustomerType;
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
import serialize.UsernameChecker;

@Path("")
public class LoginService extends ServiceTemplate {
	
	public LoginService() {
		
	}
	
	@PostConstruct
	public void init() {
		initializeData();
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
		User user = null;
		if(request.getSession().getAttribute("customer") != null) {
			user = (User)request.getSession().getAttribute("customer");
		}
		else if(request.getSession().getAttribute("deliverer") != null) {
			user = (User)request.getSession().getAttribute("deliverer");
		}
		else if(request.getSession().getAttribute("manager") != null) {
			user = (User)request.getSession().getAttribute("manager");
		}
		else if(request.getSession().getAttribute("administrator") != null) {
			user = (User)request.getSession().getAttribute("administrator");
		}
		return user;
	}
	
	@PUT
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user, @Context HttpServletRequest request) {
		String username = user.getUsername();
		CustomerDao customers = (CustomerDao)context.getAttribute("customers");
		AdministratorDao administrators = (AdministratorDao)context.getAttribute("administrators");
		DelivererDao deliverers = (DelivererDao)context.getAttribute("deliverers");
		ManagerDao managers = (ManagerDao)context.getAttribute("managers");
		boolean uniqueUsername = customers.IsUniqueUsername(username) && administrators.IsUniqueUsername(username) && deliverers.IsUniqueUsername(username) && managers.IsUniqueUsername(username);
		if(uniqueUsername){
			Customer newCustomer = new Customer(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getGender(), user.getDateOfBirth(),
					0, new ArrayList<Delivery>(), null, new ShoppingCart());
			newCustomer.setCustomerType(new CustomerType("Bronze", 0, 1000));
			newCustomer.getShoppingCart().setCustomer(newCustomer);
			new CustomerSerializer(context.getRealPath("")).Add(newCustomer, context.getRealPath(""));
			customers.getCustomers().add(newCustomer);
			return Response.status(200).entity("You have been successfully registered").build();
		}
		else {
			return Response.status(400).entity("Username already exists").build();
		}
	}
	
	@POST
	@Path("/initData")
	public void initData() {
		AdministratorSerializer admSer = new AdministratorSerializer(context.getRealPath(""));
		CommentSerializer comSer = new CommentSerializer(context.getRealPath(""));
		CustomerSerializer cusSer = new CustomerSerializer(context.getRealPath(""));
		DelivererSerializer delSer = new DelivererSerializer(context.getRealPath(""));
		ManagerSerializer manSer = new ManagerSerializer(context.getRealPath(""));
		RestaurantSerializer resSer = new RestaurantSerializer(context.getRealPath(""));
		
		ArrayList<Administrator> admins = new ArrayList<Administrator>();
		ArrayList<Comment> comments = new ArrayList<Comment>();
		ArrayList<Customer> customers = new ArrayList<Customer>();
		ArrayList<Deliverer> deliverers = new ArrayList<Deliverer>();
		ArrayList<Manager> managers = new ArrayList<Manager>();
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		Administrator admin1 = new Administrator("admin1", "password1", "Admin", "Adminovic", Gender.male, new Date(100,1,1));
		Administrator admin2 = new Administrator("admin2", "password2", "Admina", "Adminovski", Gender.female, new Date(106,1,1));
		Administrator admin3 = new Administrator("admin3", "password3", "Admino", "Adminovani", Gender.other, new Date(60,1,1));
		
		admins.add(admin1);
		admins.add(admin2);
		admins.add(admin3);
		
		admSer.Save(admins);
		
		Restaurant restaurant1 = new Restaurant("Chinese Restaurant", true, null, RestaurantType.chinese, new ArrayList<Article>(), new Location(100, 100.5, "Street1", 10, "City1", 1000));
		Restaurant restaurant2 = new Restaurant("Krusty Krab", true, null, RestaurantType.barbecue, new ArrayList<Article>(), new Location(100, 100.5, "Street1", 10, "City1", 1000));
		
		Article article11 = new Article("Fried Rice (Chaofan)", 1000.0, ArticleType.food, 200, "Fried rice from China", null, restaurant1);
		Article article12 = new Article("Peking Duck (Beijing Kaoya)", 1300.0, ArticleType.food, 340, "What does a duck say", null, restaurant1);
		Article article13 = new Article("Stinky Tofu (Choudoufu)", 600.0, ArticleType.food, 220, "Why is it stinky, why cant it just smell nice", null, restaurant1);
		Article article14 = new Article("Chow Mein", 800.0, ArticleType.food, 200, "Something something something something something something something something something something something something something something something something something", null, restaurant1);
		Article article15 = new Article("Congee (Baizhou)", 400.0, ArticleType.food, 120, "Small portion", null, restaurant1);
		Article article16 = new Article("Fresh chinese water", 50.0, ArticleType.drink, 200, "Very fresh much wow", null, restaurant1);
		
		Article article21 = new Article("Krabby patty", 1100.0, ArticleType.food, 350, "Krabby patty is the best patty", null, restaurant2);
		Article article22 = new Article("Krabby patty deluxe", 1400.0, ArticleType.food, 380, "Krabby patty is the best patty", null, restaurant2);
		Article article23 = new Article("Krabby patty kids", 700.0, ArticleType.food, 200, "Krabby patty is the best patty", null, restaurant2);
		Article article24 = new Article("Kelp shake", 200.0, ArticleType.drink, 500, "Drink that makes grass grow on you", null, restaurant2);
		
		restaurant1.addArticles(article11);
		restaurant1.addArticles(article12);
		restaurant1.addArticles(article13);
		restaurant1.addArticles(article14);
		restaurant1.addArticles(article15);
		restaurant1.addArticles(article16);
		
		restaurant2.addArticles(article21);
		restaurant2.addArticles(article22);
		restaurant2.addArticles(article23);
		restaurant2.addArticles(article24);
		
		
		ArrayList<ShoppingCartItem> items1 = new ArrayList<>();
		items1.add(new ShoppingCartItem(article21, 1));
		items1.add(new ShoppingCartItem(article24, 1));
		ArrayList<ShoppingCartItem> items2 = new ArrayList<>();
		items2.add(new ShoppingCartItem(article12, 2));
		items2.add(new ShoppingCartItem(article14, 1));
		ArrayList<ShoppingCartItem> items3 = new ArrayList<>();
		items3.add(new ShoppingCartItem(article22, 1));
		items3.add(new ShoppingCartItem(article23, 3));
		items3.add(new ShoppingCartItem(article24, 4));
		
		Delivery delivery1 = new Delivery("0000000001", new Date(121,9,9), 1170, DeliveryStatus.delivered, null, null, items1);
		Delivery delivery2 = new Delivery("0000000002", new Date(121,9,9), 3060, DeliveryStatus.cancelled, null, null, items2);
		Delivery delivery3 = new Delivery("0000000003", new Date(121,9,9), 3690, DeliveryStatus.inDelivery, null, null, items3);
		
		delivery1.setRestaurant(restaurant2);
		delivery2.setRestaurant(restaurant1);
		delivery3.setRestaurant(restaurant2);
		
		Customer customer1 = new Customer("customer1", "password1", "Kustomer", "Kustomerovic", Gender.male, new Date(106,1,1), 1000, new ArrayList<Delivery>(), new CustomerType("Gold", 10, 1500), new ShoppingCart());
		customer1.getShoppingCart().setCustomer(customer1);
		customer1.addDeliveries(delivery1);
		customer1.addDeliveries(delivery2);
		Customer customer2 = new Customer("Beli", "password1", "Mirko", "Beli", Gender.male, new Date(106,1,1), 1000, new ArrayList<Delivery>(), new CustomerType("Gold", 10, 1500), new ShoppingCart());
		customer2.addDeliveries(delivery3);
		
		customers.add(customer1);
		customers.add(customer2);
		
		cusSer.Save(customers);
		
		restaurants.add(restaurant1);
		restaurants.add(restaurant2);
		
		resSer.Save(restaurants);
		
		Comment comment1 = new Comment("text1", 5, true, customer1, restaurant1);
		Comment comment2 = new Comment("text2", 3, true, customer1, restaurant1);
		Comment comment3 = new Comment("text3", 1, false, customer1, restaurant1);
		
		comments.add(comment1);
		comments.add(comment2);
		comments.add(comment3);
		
		comSer.Save(comments);
		
		Manager manager1 = new Manager("ChineseManager", "passwordcajna", "cin", "cong", Gender.male, new Date(106,1,1), restaurant1);
		Manager manager2 = new Manager("KrustyKrabManager", "spatula", "Spongebob", "Squarepants", Gender.male, new Date(99,5,1), restaurant2);
		
		managers.add(manager1);
		managers.add(manager2);
		
		manSer.Save(managers);
		
		Deliverer deliverer1 = new Deliverer("LukaDostavljac", "lukaPassword", "Luka", "Novak", Gender.male, new Date(106,1,1), new ArrayList<Delivery>());
		Deliverer deliverer2 = new Deliverer("MilojkaBajs", "MilojkaPassword", "Milojka", "Izbosnu", Gender.female, new Date(106,1,1), new ArrayList<Delivery>());
		deliverer2.addDelivery(delivery3);
		
		deliverers.add(deliverer1);
		deliverers.add(deliverer2);
		
		delSer.Save(deliverers);
		
		initializeData();
		
	}
	
}
