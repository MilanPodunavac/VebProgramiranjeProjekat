package service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	private ServletContext context;
	
	public LoginService() {
		
	}
	
	@PostConstruct
	public void init() {
		if(context.getAttribute("customers") == null) {
			context.setAttribute("customers", new CustomerSerializer().Load());
		}
		if(context.getAttribute("administrators") == null) {
			context.setAttribute("administrators", new AdministratorSerializer().Load());
		}
		if(context.getAttribute("comments") == null) {
			context.setAttribute("comments", new CommentSerializer().Load());
		}
		if(context.getAttribute("deliverers") == null) {
			context.setAttribute("deliverers", new DelivererSerializer().Load());
		}
		if(context.getAttribute("managers") == null) {
			context.setAttribute("managers", new ManagerSerializer().Load());
		}
		if(context.getAttribute("restaurants") == null) {
			context.setAttribute("restaurants", new RestaurantSerializer().Load());
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request) {
/*		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User loggedUser = userDao.find(user.getUsername(), user.getPassword());
		if (loggedUser != null) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("user", loggedUser);
		return Response.status(200).build();*/
		return Response.status(200).build();
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
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
}
