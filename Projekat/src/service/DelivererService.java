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

@Path("DelivererService")
public class DelivererService extends ServiceTemplate {
	
	public DelivererService() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}
	
	@GET
	@Path("/getDeliverer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Deliverer getDeliverer(@Context HttpServletRequest request) {
		return (Deliverer) request.getSession().getAttribute("deliverer");
	}
	
	//getInDelivery
	
	
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
		for(Delivery ctxDelivery : deliveryDao.getDeliveries()) {
			if(ctxDelivery.getId().equals(delivery.getId())) {
				ctxDelivery = delivery;
			}
		}
		//referencama se azuriraju, nadam se
		DelivererSerializer delSer = new DelivererSerializer(context.getRealPath(""));
		CustomerSerializer cusSer = new CustomerSerializer(context.getRealPath(""));
		cusSer.Update(delivery.getCustomer());
		Deliverer deliverer = (Deliverer)request.getSession().getAttribute("deliverer");
		delSer.Update(deliverer);
	}
	
	@GET
	@Path("/getWaitingDeliveries")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Delivery> getWaitingDeliveries(){
		DeliveryDao dao = (DeliveryDao)context.getAttribute("deliveries");
		return dao.getWaitingDeliveries();
	}
}
