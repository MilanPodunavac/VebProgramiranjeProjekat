package serialize;

import java.util.ArrayList;

import beans.*;

public class UsernameChecker {

	public UsernameChecker() {
		
	}
	
	public boolean Check(String username) {
		boolean checkCustomersUsername = this.CheckCustomers(username);
		boolean checkDeliverersUsername = this.CheckDeliverers(username);
		boolean checkManagersUsername = this.CheckManagers(username);
		boolean checkAdministratorsUsername = this.CheckAdministrators(username);
		return checkCustomersUsername && checkDeliverersUsername && checkManagersUsername && checkAdministratorsUsername;
	}
	
	public boolean CheckCustomers(String username) {
		ArrayList<Customer> customers = new CustomerSerializer().Load();
		for(Customer customer : customers) {
			if(customer.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckDeliverers(String username) {
		ArrayList<Deliverer> deliverers = new DelivererSerializer().Load();
		for(Deliverer deliverer : deliverers) {
			if(deliverer.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckManagers(String username) {
		ArrayList<Manager> managers = new ManagerSerializer().Load();
		for(Manager manager : managers) {
			if(manager.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean CheckAdministrators(String username) {
		ArrayList<Administrator> administrators = new AdministratorSerializer().Load();
		for(Administrator administrator : administrators) {
			if(administrator.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	
	
}
