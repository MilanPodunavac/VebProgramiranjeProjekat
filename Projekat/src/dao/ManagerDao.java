package dao;

import java.util.ArrayList;

import beans.Deliverer;
import beans.Manager;
import beans.Restaurant;
import serialize.ManagerSerializer;

public class ManagerDao {
	private ArrayList<Manager> managers;

	public ManagerDao(ArrayList<Manager> managers) {
		super();
		this.managers = managers;
	}

	public ArrayList<Manager> getManagers() {
		return managers;
	}

	public void setManagers(ArrayList<Manager> managers) {
		this.managers = managers;
	}

	public Manager find(String username, String password) {
		Manager manager = null;
		for(Manager m : managers) {
			if(m.getUsername().equals(username) && m.getPassword().equals(password)) {
				manager = m;
			}
		}
		return manager;
	}
	
	public Manager getManagerByUsername(String username) {
		Manager manager = null;
		for(Manager m : managers) {
			if(m.getUsername().equals(username)) {
				manager = m;
			}
		}
		return manager;
	}
	
	public boolean IsUniqueUsername(String username) {
		for(Manager manager : managers) {
			if(manager.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	public Manager getManagerByRestaurant(Restaurant restaurant) {
		Manager retVal = null;
		for(Manager manager : managers) {
			if(manager.getRestaurant() == null)continue;
			if(manager.getRestaurant().getName().equals(restaurant.getName()) 
					&& manager.getRestaurant().getLocation().equals(restaurant.getLocation())) {
				retVal = manager;
			}
		}
		return retVal;
	}
}
