package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import serialize.AdministratorSerializer;
import serialize.CommentSerializer;
import serialize.CustomerSerializer;
import serialize.DelivererSerializer;
import serialize.ManagerSerializer;
import serialize.RestaurantSerializer;

@Path("")
public class LoginService {
	@Context
	private HttpServletRequest request;
	
	public LoginService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(request.getServletContext().getAttribute("customers") == null) {
			request.getServletContext().setAttribute("customers", new CustomerSerializer().Load());
		}
		if(request.getServletContext().getAttribute("administrators") == null) {
			request.getServletContext().setAttribute("administrators", new AdministratorSerializer().Load());
		}
		if(request.getServletContext().getAttribute("comments") == null) {
			request.getServletContext().setAttribute("comments", new CommentSerializer().Load());
		}
		if(request.getServletContext().getAttribute("deliverers") == null) {
			request.getServletContext().setAttribute("deliverers", new DelivererSerializer().Load());
		}
		if(request.getServletContext().getAttribute("managers") == null) {
			request.getServletContext().setAttribute("managers", new ManagerSerializer().Load());
		}
		if(request.getServletContext().getAttribute("restaurants") == null) {
			request.getServletContext().setAttribute("restaurants", new RestaurantSerializer().Load());
		}
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@QueryParam("username")String username) {
		System.out.println(username);
		return null;
	}
}
