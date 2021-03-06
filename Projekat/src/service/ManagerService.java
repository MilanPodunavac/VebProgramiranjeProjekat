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
import dto.ArticleEditDTO;
import dto.DeliveryDTO;
import dto.DeliveryRequestDTO;
import dto.PasswordChangeDTO;
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

	@GET
	@Path("/getManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manager getManager(@Context HttpServletRequest request) {
		return (Manager) request.getSession().getAttribute("manager");
	}
	
	@POST
	@Path("/changeManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changeManager(Manager manager, @Context HttpServletRequest request) {
		Manager loggedManager = (Manager) request.getSession().getAttribute("manager");
		if(loggedManager == null)return;
		loggedManager.setUsername(manager.getUsername());
		loggedManager.setName(manager.getName());
		loggedManager.setSurname(manager.getSurname());
		loggedManager.setGender(manager.getGender());
		loggedManager.setDateOfBirth(manager.getDateOfBirth());
		new ManagerSerializer(context.getRealPath("")).Save((ArrayList<Manager>)((ManagerDao)context.getAttribute("managers")).getManagers());
	}
	
	@POST
	@Path("/changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String changePassword(PasswordChangeDTO dto, @Context HttpServletRequest request) {
		Manager loggedManager = (Manager) request.getSession().getAttribute("manager");
		if(loggedManager == null)return "Error";
		if(!loggedManager.getPassword().equals(dto.getOldPassword())) {
			return "Invalid current password!";
		}
		loggedManager.setPassword(dto.getNewPassword());
		new ManagerSerializer(context.getRealPath("")).Save((ArrayList<Manager>)((ManagerDao)context.getAttribute("managers")).getManagers());
		return "Password changed!";
	}
	
	@GET
	@Path("/getRestaurantDeliveries")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeliveryDTO> getRestaurantDeliveries(@Context HttpServletRequest request){
		Restaurant restaurant = ((Manager)request.getSession().getAttribute("manager")).getRestaurant();
		DeliveryDao deliveryDao = (DeliveryDao)context.getAttribute("deliveries");
		List<Delivery> deliveries = deliveryDao.getRestaurantDeliveries(restaurant);
		List<DeliveryDTO> dto = new ArrayList<>();
		for(Delivery delivery : deliveries) {
			dto.add(new DeliveryDTO(delivery));
		}
		return dto;
	}
	
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
	
	@POST
	@Path("/rejectComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void rejectComment(Comment comment) {
		CommentDao commentDao = (CommentDao)context.getAttribute("comments");
		for(Comment ctxComment : commentDao.getComments()) {
			if(ctxComment.getText().equals(comment.getText()) 
					&& comment.getGrade() == ctxComment.getGrade() 
					&& comment.getCustomer().getUsername().equals(ctxComment.getCustomer().getUsername())) {
				ctxComment.setRejected(true);
				CommentSerializer commentSerializer = new CommentSerializer(context.getRealPath(""));
				commentSerializer.Update(ctxComment);
			}
		}
	}
	
	@PUT
	@Path("/addArticle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addArticle(Article article, @Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		Restaurant restaurant = manager.getRestaurant();
		article.setImageId("");
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
	
	@GET
	@Path("/getArticle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Article getArticle(@QueryParam("name") String name, @Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		Restaurant restaurant = manager.getRestaurant();
		for(Article article : restaurant.getArticles()) {
			if(article.getName().equals(name)) {
				return article;
			}
		}
		return null;
	}
	
	@POST
	@Path("/updateArticle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateArticle(ArticleEditDTO dto, @Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		Restaurant restaurant = manager.getRestaurant();
		boolean success = false;
		RestaurantDao restaurantDao = (RestaurantDao)context.getAttribute("restaurants");
		Article article = dto.getArticle();
		for(Article existingArticle : restaurant.getArticles()) {
			if(dto.getOldArticleName().equals(existingArticle.getName())) {
				existingArticle.setArticleType(article.getArticleType());
				existingArticle.setName(article.getName());
				existingArticle.setPrice(article.getPrice());
				existingArticle.setSize(article.getSize());
				existingArticle.setImageId(article.getImageId());
				existingArticle.setDescription(article.getDescription());
				RestaurantDao dao = (RestaurantDao)context.getAttribute("restaurants");
				(new RestaurantSerializer(context.getRealPath(""))).Save(dao.getRestaurants());
				success = true;
				break;
			}
		}
		return success;
	}
	
	@GET
	@Path("/getPendingDeliveryRequestsForManager")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeliveryRequestDTO> getPendingDeliveryRequestsForManager(@Context HttpServletRequest request) {
		Manager manager = (Manager) request.getSession().getAttribute("manager");
		DeliveryRequestDao deliveryRequestDao = (DeliveryRequestDao)context.getAttribute("deliveryRequests");
		DelivererDao delivererDao = (DelivererDao)context.getAttribute("deliverers");
		DeliveryDao deliveryDao = (DeliveryDao)context.getAttribute("deliveries");
		List<DeliveryRequest> requests = deliveryRequestDao.getPendingDeliveryRequestsForManager(manager);
		List<DeliveryRequestDTO> dto = new ArrayList<>();
		for(DeliveryRequest deliveryRequest : requests) {
			dto.add(new DeliveryRequestDTO(delivererDao.getDelivererByUsername(deliveryRequest.getDelivererUsername()),
					deliveryDao.findDeliveryById(deliveryRequest.getDeliveryId()), deliveryDao.findDeliveryById(deliveryRequest.getDeliveryId()).getCustomer()));
		}
		return dto;
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
