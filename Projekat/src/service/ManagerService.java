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
	
	@GET
	@Path("/getManagerByUsername")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manager getManagerByUsername(@QueryParam("username") String username) {
		ManagerDao managerDao = (ManagerDao)context.getAttribute("managers");
		return managerDao.getManagerByUsername(username);
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
	public List<Delivery> getRestaurantDeliveries(@Context HttpServletRequest request){
		Restaurant restaurant = ((Manager)request.getSession().getAttribute("manager")).getRestaurant();
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
	@POST
	@Path("/setDeliveryToInDelivery")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void setDeliveryToInDelivery(DeliveryRequest deliveryRequest) {
		DeliveryDao deliveryDao = (DeliveryDao)context.getAttribute("deliveries");
		DelivererDao delivererDao = (DelivererDao)context.getAttribute("deliverers");
		for(Delivery ctxDelivery : deliveryDao.getWaitingDeliveries()) {
			if(ctxDelivery.getId().equals(deliveryRequest.getDeliveryId())) {
				for(Deliverer ctxDeliverer : delivererDao.getDeliverers()) {
					if(ctxDeliverer.getUsername().equals(deliveryRequest.getDelivererUsername())) {
						ctxDelivery.setDeliveryStatus(DeliveryStatus.inDelivery);
						ctxDeliverer.addDelivery(ctxDelivery);
					}
				}
			}
		}
		DeliveryRequestDao deliveryRequestDao = (DeliveryRequestDao)context.getAttribute("deliveryRequests");
		deliveryRequestDao.declineAllDeliveryRequests(deliveryRequest);
		
		CustomerDao customerDao = (CustomerDao)context.getAttribute("customers");
		
		CustomerSerializer customerSerializer = new CustomerSerializer(context.getRealPath(""));
		DelivererSerializer delivererSerializer = new DelivererSerializer(context.getRealPath(""));
		DeliveryRequestSerializer deliveryRequestSerializer = new DeliveryRequestSerializer(context.getRealPath(""));
		
		customerSerializer.Save(customerDao.getCustomers());
		delivererSerializer.Save(delivererDao.getDeliverers());
		deliveryRequestSerializer.Save(deliveryRequestDao.getDeliveryRequests());
	}
	
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
	@PUT
	@Path("/addArticle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addArticle(Article article, @Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		Restaurant restaurant = manager.getRestaurant();
		boolean success = false;
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		for(Restaurant ctxRestaurant : restaurantDao.getRestaurants()) {
			if(restaurant.getName().equals(ctxRestaurant.getName()) && restaurant.getLocation().equals(ctxRestaurant.getLocation())) {
				boolean nameAvailable = true;
				for(Article existingArticle : ctxRestaurant.getArticles()) {
					if(existingArticle.getName().equals(article.getName())) {
						nameAvailable = false;
					}
				}
				if(nameAvailable) {
					ctxRestaurant.addArticles(article);
					(new RestaurantSerializer(context.getRealPath(""))).Update(ctxRestaurant);
					success = true;
					break;
				}
			}
		}
		return success;
	}
	
	
	//updateArticle
	@POST
	@Path("/updateArticle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateArticle(Article article, @Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		Restaurant restaurant = manager.getRestaurant();
		boolean success = false;
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		for(Restaurant ctxRestaurant : restaurantDao.getRestaurants()) {
			if(restaurant.getName().equals(ctxRestaurant.getName()) && restaurant.getLocation().equals(ctxRestaurant.getLocation())) {
				for(Article existingArticle : restaurant.getArticles()) {
					if(article.getName().equals(existingArticle.getName())) {
						article.setRestaurant(ctxRestaurant);
						existingArticle = article;
						(new RestaurantSerializer(context.getRealPath(""))).Update(ctxRestaurant);
						success = true;
						break;
					}
				}
			}
		}
		return success;
	}
	
	@GET
	@Path("/getPendingDeliveryRequestsForManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeliveryRequest> getPendingDeliveryRequestsForManager(@Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		DeliveryRequestDao deliveryRequestDao = (DeliveryRequestDao)context.getAttribute("deliveryRequests");
		return deliveryRequestDao.getPendingDeliveryRequestsForManager(manager);
	}
	
	@GET
	@Path("/getNotApprovedRestaurantComments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getNotApprovedRestaurantComments(@Context HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		return commentDao.getNotApprovedRestaurantComments(manager.getRestaurant());
	}
	
	@GET
	@Path("/getRestaurantComments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getRestaurantComments(@Context HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		return commentDao.getRestaurantComments(manager.getRestaurant());
	}
}
