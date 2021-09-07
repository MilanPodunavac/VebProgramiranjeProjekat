import java.awt.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import beans.*;
import serialize.*;

public class Test {

	public static void main(String[] args) {
		AdministratorSerializer admSer = new AdministratorSerializer();
		CommentSerializer comSer = new CommentSerializer();
		CustomerSerializer cusSer = new CustomerSerializer();
		DelivererSerializer delSer = new DelivererSerializer();
		ManagerSerializer manSer = new ManagerSerializer();
		RestaurantSerializer resSer = new RestaurantSerializer();
		
		ArrayList<Administrator> admins = new ArrayList<Administrator>();
		ArrayList<Comment> comments = new ArrayList<Comment>();
		ArrayList<Customer> customers = new ArrayList<Customer>();
		ArrayList<Deliverer> deliverers = new ArrayList<Deliverer>();
		ArrayList<Manager> managers = new ArrayList<Manager>();
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		/*Administrator admin1 = new Administrator("admin1", "password1", "Admin", "Adminovic", Gender.male, new Date(100,1,1));
		Administrator admin2 = new Administrator("admin2", "password2", "Admina", "Adminovski", Gender.female, new Date(106,1,1));
		Administrator admin3 = new Administrator("admin3", "password3", "Admino", "Adminovani", Gender.other, new Date(60,1,1));
		
		System.out.println(admSer.Add(admin1));
		System.out.println(admSer.Add(admin2));
		System.out.println(admSer.Add(admin3));

		admins = admSer.Load();
		for(Administrator a : admins) {
			System.out.println(a.getName());
			System.out.println(a.getPassword());
			System.out.println(a.getSurname());
			System.out.println(a.getUsername());
			System.out.println(a.isDeleted());
			System.out.println(a.getGender());
			System.out.println(a.getDateOfBirth());
		}
		
		Administrator admin11 = new Administrator("admin1", "password1", "Adminnnnnnn", "Adminovic", Gender.male, new Date(70,1,1));
		System.out.println(admSer.Update(admin11));
		admSer.Delete(admin3);
		
		admins = admSer.Load();
		for(Administrator a : admins) {
			System.out.println(a.getName());
			System.out.println(a.getPassword());
			System.out.println(a.getSurname());
			System.out.println(a.getUsername());
			System.out.println(a.isDeleted());
			System.out.println(a.getGender());
			System.out.println(a.getDateOfBirth());
		}*/
		
		
		
		Customer customer1 = new Customer("customer1", "password1", "Kustomer", "Kustomerovic", Gender.male, new Date(106,1,1), 1000, new ArrayList<Delivery>(), new CustomerType("Gold", 12, 700), null);
		Restaurant restaurant1 = new Restaurant("Restaurant1", true, null, RestaurantType.chinese, new ArrayList<Article>(), new Location(100, 100.5, "Street1", 10, "City1", 1000));
		
		Delivery delivery1 = new Delivery("0000000001", new Date(121,9,9), 100, DeliveryStatus.delivered, null, restaurant1, null);
		Delivery delivery2 = new Delivery("0000000002", new Date(121,9,9), 10000, DeliveryStatus.cancelled, null, restaurant1, null);
		
		customer1.getDeliveries().add(delivery1);
		delivery1.setCustomer(customer1);
		customer1.getDeliveries().add(delivery2);
		delivery2.setCustomer(customer1);
		
		Comment comment1 = new Comment("text1", 5, true, customer1, restaurant1);
		Comment comment2 = new Comment("text2", 3, true, customer1, restaurant1);
		Comment comment3 = new Comment("text3", 1, false, customer1, restaurant1);
		
		System.out.println(comSer.Add(comment1));
		System.out.println(comSer.Add(comment2));
		System.out.println(comSer.Add(comment3));

		comments = comSer.Load();
		for(Comment c : comments) {
			System.out.println(c.getText());
			System.out.println(c.getGrade());
			System.out.println(c.isApproved());
			System.out.println(c.getCustomer().getName());
			System.out.println(c.getRestaurant().getName());
		}
		
//		System.out.println(resSer.Add(restaurant1));
//		System.out.println(cusSer.Add(customer1));
		
		//Administrator admin11 = new Administrator("admin1", "password1", "Adminnnnnnn", "Adminovic", Gender.male, new Date(70,1,1));
		//System.out.println(admSer.Update(admin11));
		//comSer.Delete(admin3);
		
		/*admins = admSer.Load();
		for(Administrator a : admins) {
			System.out.println(a.getName());
			System.out.println(a.getPassword());
			System.out.println(a.getSurname());
			System.out.println(a.getUsername());
			System.out.println(a.isDeleted());
			System.out.println(a.getGender());
			System.out.println(a.getDateOfBirth());
		}*/
	}

}
