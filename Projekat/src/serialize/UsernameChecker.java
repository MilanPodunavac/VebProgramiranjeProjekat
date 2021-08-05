package serialize;

import java.util.ArrayList;

import beans.*;

public class UsernameChecker {

	public UsernameChecker() {
		
	}
	
	public boolean Check(String username, String contextPath) {
		boolean checkCustomersUsername = this.CheckCustomers(username, contextPath);
		boolean checkDeliverersUsername = this.CheckDeliverers(username, contextPath);
		boolean checkManagersUsername = this.CheckManagers(username, contextPath);
		boolean checkAdministratorsUsername = this.CheckAdministrators(username, contextPath);
		return checkCustomersUsername && checkDeliverersUsername && checkManagersUsername && checkAdministratorsUsername;
	}
	
	public boolean CheckCustomers(String username, String contextPath) {
		ArrayList<Customer> customers = new CustomerSerializer(contextPath).Load();
		for(Customer customer : customers) {
			if(customer.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckDeliverers(String username, String contextPath) {
		ArrayList<Deliverer> deliverers = new DelivererSerializer(contextPath).Load();
		for(Deliverer deliverer : deliverers) {
			if(deliverer.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckManagers(String username, String contextPath) {
		ArrayList<Manager> managers = new ManagerSerializer(contextPath).Load();
		for(Manager manager : managers) {
			if(manager.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckAdministrators(String username, String contextPath) {
		ArrayList<Administrator> administrators = new AdministratorSerializer(contextPath).Load();
		for(Administrator administrator : administrators) {
			if(administrator.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	
	
}
