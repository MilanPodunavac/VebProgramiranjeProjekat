package service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

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

@Path("ManagerService")
public class ManagerService extends ServiceTemplate {

	@Context
	private ServletContext context;
	
	public ManagerService() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}

}
