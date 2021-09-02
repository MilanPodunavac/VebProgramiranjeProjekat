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

@Path("ManagerService")
public class ManagerService extends ServiceTemplate {
	
	public ManagerService() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}

	//getManager
	@GET
	@Path("/getManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manager getManager(@Context HttpServletRequest request) {
		return (Manager) request.getSession().getAttribute("manager");
	}
	
	//getRestaurantDeliveries
	@GET
	@Path("/getRestaurantDeliveries")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Delivery> getRestaurantDeliveries(Restaurant restaurant){
		DeliveryDao deliveryDao = (DeliveryDao)context.getAttribute("deliveries");
		return deliveryDao.getRestaurantDeliveries(restaurant);
	}
	
	//setPreparationDeliveryStatus
	@POST
	@Path("/setDeliveryToPreparation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void setDeliveryToPreparation(Delivery delivery) {
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		Delivery ctxDelivery = customerDao.findDelivery(delivery.getId());
		ctxDelivery.setDeliveryStatus(DeliveryStatus.preparation);
		CustomerSerializer customerSerializer = new CustomerSerializer(context.getRealPath(""));
		customerSerializer.Update(ctxDelivery.getCustomer());
	}
	
	//setWaitingDeliveryStatus
	@POST
	@Path("/setDeliveryToWaitingDelivery")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void setDeliveryToWaitingDelivery(Delivery delivery) {
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		Delivery ctxDelivery = customerDao.findDelivery(delivery.getId());
		ctxDelivery.setDeliveryStatus(DeliveryStatus.waitingDelivery);
		CustomerSerializer customerSerializer = new CustomerSerializer(context.getRealPath(""));
		customerSerializer.Update(ctxDelivery.getCustomer());
	}
	
	//setInDelivery (accept Request)
	
	//approveRestaurantComment
	@POST
	@Path("/approveComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void approveComment(Comment comment) {
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		for(Comment ctxComment : commentDao.getComments()) {
			if(ctxComment.getText().equals(comment.getText()) 
					&& comment.getGrade() == ctxComment.getGrade() 
					&& comment.getCustomer().getUsername().equals(ctxComment.getCustomer().getUsername())) {
				ctxComment.setApproved(true);
				CommentSerializer commentSerializer = new CommentSerializer(context.getRealPath(""));
				commentSerializer.Update(ctxComment);
			}
		}
	}
	//addArticle
	
	//updateArticle
}
