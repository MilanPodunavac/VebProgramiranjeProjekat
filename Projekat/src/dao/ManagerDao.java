package dao;

import java.util.ArrayList;
import java.util.List;

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
			if(m.getUsername().equals(username) && m.getPassword().equals(password) && !m.isDeleted()) {
				manager = m;
			}
		}
		return manager;
	}
	
	public Manager getManagerByUsername(String username) {
		Manager manager = null;
		for(Manager m : managers) {
			if(m.getUsername().equals(username) && !m.isDeleted()) {
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
	
	public List<Manager> getManagersWithoutRestaurant(){
		List<Manager> retVal = new ArrayList<Manager>();
		for(Manager manager : managers) {
			if(!manager.isDeleted() && manager.getRestaurant() == null) {
				retVal.add(manager);
			}
		}
		return retVal;
	}

	public void deleteManager(Manager manager) {
		for(Manager ctxManager : managers) {
			if(ctxManager.getUsername().equals(manager.getUsername())) {
				ctxManager.setDeleted(true);
			}
		}
		
	}

	public void removeRestaurantFromManager(Restaurant restaurant) {
		for(Manager manager : managers) {
			if(manager.getRestaurant() == null) {
				continue;
			}
			if(manager.getRestaurant().getName().equals(restaurant.getName()) && manager.getRestaurant().checkLocation(restaurant)) {
				manager.setRestaurant(null);
			}
		}
		
	}
}
