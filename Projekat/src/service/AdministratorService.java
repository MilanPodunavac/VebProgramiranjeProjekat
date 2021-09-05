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
	@Path("/getAdming")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator getAdministrator(@Context HttpServletRequest request) {
		return (Administrator) request.getSession().getAttribute("administrator");
	}
	
	//createNewRestaurant
	@PUT
	@Path("/createNewRestaurant")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createNewRestaurant(Restaurant restaurant, Manager manager) {
		boolean success = false;
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		if(restaurantDao.find(restaurant) == null) {
			(managerDao.getManagerByUsername(manager.getUsername())).setRestaurant(restaurant);
			restaurantDao.getRestaurants().add(restaurant);
			RestaurantSerializer restaurantSerializer = new RestaurantSerializer(context.getRealPath(""));
			restaurantSerializer.Add(restaurant);
			ManagerSerializer managerSerializer = new ManagerSerializer(context.getRealPath(""));
			managerSerializer.Update(manager);
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
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		if(managerDao.getManagerByUsername(manager.getUsername()) == null) {
			managerDao.getManagers().add(manager);
			ManagerSerializer managerSerializer = new ManagerSerializer(context.getRealPath(""));
			success = managerSerializer.Add(manager, context.getRealPath(""));//Vraca false ako username vec postoji, zato salje getRealPath, promeniti?
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
		if(delivererDao.getDelivererByUsername(deliverer.getUsername()) == null) {
			delivererDao.getDeliverers().add(deliverer);
			DelivererSerializer delivererSerializer = new DelivererSerializer(context.getRealPath(""));
			success = delivererSerializer.Add(deliverer, context.getRealPath(""));//Vraca false ako username vec postoji, zato salje getRealPath, promeniti?
		}
		return success;
	}
	
	
	//pregledSvihKorisnika
	@GET
	@Path("/getAllCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getAllCustomers(){
		return ((CustomerDao)context.getAttribute("customers")).getCustomers();
	}
	
	@GET
	@Path("/getAllManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manager> getAllManagers(){
		return ((ManagerDao)context.getAttribute("managers")).getManagers();
	}
	
	@GET
	@Path("/getAllDeliverers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deliverer> getAllDeliverers(){
		return ((DelivererDao)context.getAttribute("deliverers")).getDeliverers();
	}
	
	@GET
	@Path("/getAllAdmins")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Administrator> getAllAdmins(){
		return ((AdministratorDao)context.getAttribute("customers")).getAdministrators();
	}
	
	
	//deleteEntities
	
	//za 9-10 -- blokiranje korisnika
	
	//za 9-10 -- prikaz sumnjivih korisnika
}
