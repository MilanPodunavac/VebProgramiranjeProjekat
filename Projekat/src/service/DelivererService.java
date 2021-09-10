package service;

import java.util.ArrayList;
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

import beans.Administrator;
import beans.Article;
import beans.Comment;
import beans.Customer;
import beans.Deliverer;
import beans.Delivery;
import beans.DeliveryRequest;
import beans.DeliveryStatus;
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
import dto.DeliveryDTO;
import dto.PasswordChangeDTO;
import serialize.AdministratorSerializer;
import serialize.CommentSerializer;
import serialize.CustomerSerializer;
import serialize.DelivererSerializer;
import serialize.DeliveryRequestSerializer;
import serialize.ManagerSerializer;
import serialize.RestaurantSerializer;

@Path("DelivererService")
public class DelivererService extends ServiceTemplate {
	
	public DelivererService() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}
	
	@GET
	@Path("/getDelivererByUsername")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Deliverer getDelivererByUsername(@QueryParam("username") String username) {
		DelivererDao delivererDao = (DelivererDao)context.getAttribute("deliverers");
		return delivererDao.getDelivererByUsername(username);
	}
	
	@GET
	@Path("/getDeliverer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Deliverer getDeliverer(@Context HttpServletRequest request) {
		return (Deliverer) request.getSession().getAttribute("deliverer");
	}
	
	@POST
	@Path("/changeDeliverer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changeDeliverer(Deliverer deliverer, @Context HttpServletRequest request) {
		Deliverer loggedDeliverer = (Deliverer) request.getSession().getAttribute("deliverer");
		if(loggedDeliverer == null)return;
		loggedDeliverer.setUsername(deliverer.getUsername());
		loggedDeliverer.setName(deliverer.getName());
		loggedDeliverer.setSurname(deliverer.getSurname());
		loggedDeliverer.setGender(deliverer.getGender());
		loggedDeliverer.setDateOfBirth(deliverer.getDateOfBirth());
		new DelivererSerializer(context.getRealPath("")).Save((ArrayList<Deliverer>)((DelivererDao)context.getAttribute("deliverers")).getDeliverers());
	}
	
	@POST
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(PasswordChangeDTO dto, @Context HttpServletRequest request) {
		Deliverer loggedDeliverer = (Deliverer) request.getSession().getAttribute("deliverer");
		if(loggedDeliverer == null)return "Error";
		if(!loggedDeliverer.getPassword().equals(dto.getOldPassword())) {
			return "Invalid current password!";
		}
		loggedDeliverer.setPassword(dto.getNewPassword());
		new DelivererSerializer(context.getRealPath("")).Save((ArrayList<Deliverer>)((DelivererDao)context.getAttribute("deliverers")).getDeliverers());
		return "Password changed!";
	}
	
	//getInDelivery ???
	
	@POST
	@Path("/requestDelivery")
	@Consumes(MediaType.APPLICATION_JSON)
	public void requestDelivery(Delivery delivery, @Context HttpServletRequest request) {
		Deliverer deliverer = (Deliverer)request.getSession().getAttribute("deliverer");
		Manager manager = ((ManagerDao)context.getAttribute("managers")).getManagerByRestaurant(delivery.getRestaurant());
		DeliveryRequest deliveryRequest = new DeliveryRequest(manager.getUsername(), deliverer.getUsername(), delivery.getId());
		((DeliveryRequestDao)context.getAttribute("deliveryRequests")).getDeliveryRequests().add(deliveryRequest);
		new DeliveryRequestSerializer(context.getRealPath("")).Add(deliveryRequest);
	}
	
	@POST
	@Path("/deliverDelivery")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deliverDelivery(Delivery delivery, @Context HttpServletRequest request) {
		delivery.setDeliveryStatus(DeliveryStatus.delivered);
		DeliveryDao deliveryDao = (DeliveryDao)context.getAttribute("deliveries");
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		for(Delivery ctxDelivery : deliveryDao.getDeliveries()) {
			if(ctxDelivery.getId().equals(delivery.getId())) {
				ctxDelivery.setDeliveryStatus(DeliveryStatus.delivered);
			}
		}
		//referencama se azuriraju, nadam se
		DelivererSerializer delSer = new DelivererSerializer(context.getRealPath(""));
		CustomerSerializer cusSer = new CustomerSerializer(context.getRealPath(""));
		cusSer.Update(customerDao.getCustomerByUsername(delivery.getCustomer().getUsername()));
		Deliverer deliverer = (Deliverer)request.getSession().getAttribute("deliverer");
		delSer.Update(deliverer);
	}
	
	@GET
	@Path("/getDelivererDeliveries")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeliveryDTO> getDelivererDeliveries(@Context HttpServletRequest request){
		Deliverer deliverer = (Deliverer) request.getSession().getAttribute("deliverer");
		List<DeliveryDTO> deliveriesDTO = new ArrayList<>();
		for(Delivery delivery : deliverer.getDeliveries()) {
			deliveriesDTO.add(new DeliveryDTO(delivery));
		}
		return deliveriesDTO;
	}
	
	@GET
	@Path("/getWaitingDeliveries")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeliveryDTO> getWaitingDeliveries(@Context HttpServletRequest request){
		DeliveryDao dao = (DeliveryDao)context.getAttribute("deliveries");
		DeliveryRequestDao deliveryRequestDao = (DeliveryRequestDao)context.getAttribute("deliveryRequests");
		List<Delivery> allWaitingDeliveries = dao.getWaitingDeliveries();
		Deliverer deliverer = (Deliverer)request.getSession().getAttribute("deliverer");
		List<DeliveryDTO> notAlreadyRequestedWaitingDeliveries = new ArrayList<>();
		for(Delivery delivery : allWaitingDeliveries) {
			boolean alreadyRequested = false;
			for(DeliveryRequest deliveryRequest : deliveryRequestDao.getDeliveryRequests()) {
				if(delivery.getId().equals(deliveryRequest.getDeliveryId()) 
						&& deliveryRequest.getDelivererUsername().equals(deliverer.getUsername())) {
					alreadyRequested = true;
				}
			}
			if(!alreadyRequested) {
				notAlreadyRequestedWaitingDeliveries.add(new DeliveryDTO(delivery));
			}
		}
		return notAlreadyRequestedWaitingDeliveries;
	}
}
