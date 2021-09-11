package service;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import beans.ImageBase64;
import dao.ImageBase64Dao;
import serialize.ImageBase64Serializer;

@Path("Image64Service")
public class Image64Service extends ServiceTemplate {

	public Image64Service() {
		
	}

	@PostConstruct
	public void init() {
		initializeData();
	}
	
	@GET
	@Path("/getImageData")
	public String getImageData(@QueryParam("id") String id) {
		ImageBase64Dao imageBase64Dao = (ImageBase64Dao)context.getAttribute("images64");
		return imageBase64Dao.getImageData(id);
	}
	
	@POST
	@Path("/addImage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addImage(ImageBase64 image) {
		ImageBase64Dao imageBase64Dao = (ImageBase64Dao)context.getAttribute("images64");
		image.setId(imageBase64Dao.generateId());
		imageBase64Dao.addImage(image);
		new ImageBase64Serializer(context.getRealPath("")).Save(imageBase64Dao.getImages());
		return image.getId();
	}
	
	
}
