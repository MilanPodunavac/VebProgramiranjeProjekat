package service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Administrator;
import beans.Article;
import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.DeliveryRequest;
import beans.Manager;
import beans.Restaurant;
import dao.AdministratorDao;
import dao.CommentDao;
import dao.CustomerDao;
import dao.DelivererDao;
import dao.DeliveryDao;
import dao.DeliveryRequestDao;
import dao.ManagerDao;
import dao.RestaurantDao;
import dto.PasswordChangeDTO;
import dto.RestaurantCreationDTO;
import serialize.AdministratorSerializer;
import serialize.CommentSerializer;
import serialize.CustomerSerializer;
import serialize.DelivererSerializer;
import serialize.DeliveryRequestSerializer;
import serialize.ManagerSerializer;
import serialize.RestaurantSerializer;

@Path("AdministratorService")
public class AdministratorService extends ServiceTemplate {
	
	public AdministratorService() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}
	
	@GET
	@Path("/getAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator getAdministrator(@Context HttpServletRequest request) {
		return (Administrator) request.getSession().getAttribute("administrator");
	}
	
	@POST
	@Path("/changeAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changeAdmin(Administrator admin, @Context HttpServletRequest request) {
		Administrator loggedAdmin = (Administrator) request.getSession().getAttribute("administrator");
		if(loggedAdmin == null)return;
		loggedAdmin.setUsername(admin.getUsername());
		loggedAdmin.setName(admin.getName());
		loggedAdmin.setSurname(admin.getSurname());
		loggedAdmin.setGender(admin.getGender());
		loggedAdmin.setDateOfBirth(admin.getDateOfBirth());
		new AdministratorSerializer(context.getRealPath("")).Save((ArrayList<Administrator>)((AdministratorDao)context.getAttribute("administrators")).getAdministrators());
	}
	
	@POST
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(PasswordChangeDTO dto, @Context HttpServletRequest request) {
		Administrator loggedAdmin = (Administrator) request.getSession().getAttribute("administrator");
		if(loggedAdmin == null)return "Error";
		if(!loggedAdmin.getPassword().equals(dto.getOldPassword())) {
			return "Invalid current password!";
		}
		loggedAdmin.setPassword(dto.getNewPassword());
		new AdministratorSerializer(context.getRealPath("")).Save((ArrayList<Administrator>)((AdministratorDao)context.getAttribute("administrators")).getAdministrators());
		return "Password changed!";
	}
	
	@PUT
	@Path("/createNewRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createNewRestaurant(RestaurantCreationDTO dto) {
		Restaurant restaurant = dto.getRestaurant();
		Manager manager = dto.getManager();
		boolean success = false;
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		if(restaurantDao.find(restaurant) == null) {
			List<Article> articles = new ArrayList<>();
			restaurant.setArticles(articles);
			restaurant.setWorking(true);
			Manager realManager = managerDao.getManagerByUsername(manager.getUsername());
			realManager.setRestaurant(restaurant);
			restaurantDao.getRestaurants().add(restaurant);
			RestaurantSerializer restaurantSerializer = new RestaurantSerializer(context.getRealPath(""));
			restaurantSerializer.Add(restaurant);
			ManagerSerializer managerSerializer = new ManagerSerializer(context.getRealPath(""));
			managerSerializer.Update(realManager);
			success = true;
		}
		return success;
	}
	
	@PUT
	@Path("/createNewManager")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createNewManager(Manager manager) {
		boolean success = false;
		DelivererDao delivererDao = (DelivererDao)context.getAttribute("deliverers");
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		if(delivererDao.getDelivererByUsername(manager.getUsername()) == null &&
				managerDao.getManagerByUsername(manager.getUsername()) == null
				&& customerDao.getCustomerByUsername(manager.getUsername()) == null) {
			managerDao.getManagers().add(manager);
			ManagerSerializer managerSerializer = new ManagerSerializer(context.getRealPath(""));
			success = managerSerializer.Add(manager, context.getRealPath(""));
		}
		return success;
	}
	
	@PUT
	@Path("/createNewDeliverer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createNewDeliverer(Deliverer deliverer) {
		boolean success = false;
		DelivererDao delivererDao = (DelivererDao)context.getAttribute("deliverers");
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		if(delivererDao.getDelivererByUsername(deliverer.getUsername()) == null &&
				managerDao.getManagerByUsername(deliverer.getUsername()) == null
				&& customerDao.getCustomerByUsername(deliverer.getUsername()) == null) {
			delivererDao.getDeliverers().add(deliverer);
			DelivererSerializer delivererSerializer = new DelivererSerializer(context.getRealPath(""));
			success = delivererSerializer.Add(deliverer, context.getRealPath(""));
		}
		return success;
	}
	
	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getAllCustomers(){
		List<Customer> allCustomers = ((CustomerDao)context.getAttribute("customers")).getCustomers();
		List<Customer> customers = new ArrayList<>();
		for(Customer customer : allCustomers) {
			if(!customer.isDeleted()) {
				customers.add(customer);
			}
		}
		return customers;
	}
	
	@GET
	@Path("/getAllManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manager> getAllManagers(){
		List<Manager> allManagers = ((ManagerDao)context.getAttribute("managers")).getManagers();
		List<Manager> managers = new ArrayList<>();
		for(Manager manager : allManagers) {
			if(!manager.isDeleted()) {
				managers.add(manager);
			}
		}
		return managers;
	}
	
	@GET
	@Path("/getAllDeliverers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deliverer> getAllDeliverers(){
		List<Deliverer> allDeliverers = ((DelivererDao)context.getAttribute("deliverers")).getDeliverers();
		List<Deliverer> deliverers = new ArrayList<>();
		for(Deliverer deliverer : allDeliverers) {
			if(!deliverer.isDeleted()) {
				deliverers.add(deliverer);
			}
		}
		return deliverers;
	}
	
	
	@GET
	@Path("/getManagersWithoutRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manager> getManagersWithoutRestaurant(){
		return ((ManagerDao)context.getAttribute("managers")).getManagersWithoutRestaurant();
	}
	@DELETE
	@Path("/deleteCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteCustomer(Customer customer) {
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		customerDao.deleteCustomer(customer);
		(new CustomerSerializer(context.getRealPath(""))).Save(customerDao.getCustomers());
	}
	
	@DELETE
	@Path("/deleteDeliverer")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteDeliverer(Deliverer deliverer) {
		DelivererDao delivererDao = (DelivererDao)context.getAttribute("deliverers");
		delivererDao.deleteDeliverer(deliverer);
		(new DelivererSerializer(context.getRealPath(""))).Save(delivererDao.getDeliverers());
	}
	
	@DELETE
	@Path("/deleteManager")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteManager(Manager manager) {
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		managerDao.deleteManager(manager);
		(new ManagerSerializer(context.getRealPath(""))).Save(managerDao.getManagers());
	}
	
	@DELETE
	@Path("deleteRestaurant")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteRestaurant(Restaurant restaurant) {
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		managerDao.removeRestaurantFromManager(restaurant);
		restaurantDao.deleteRestaurant(restaurant);
		(new ManagerSerializer(context.getRealPath(""))).Save(managerDao.getManagers());
		(new RestaurantSerializer(context.getRealPath(""))).Save(restaurantDao.getRestaurants());
		
	}
}
