package dao;

import java.util.ArrayList;

import beans.Deliverer;
import beans.Manager;
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
		Manager deliverer = null;
		for(Manager m : managers) {
			if(m.getUsername().equals(username) && m.getPassword().equals(password)) {
				deliverer = m;
			}
		}
		return deliverer;
	}
	
	public boolean IsUniqueUsername(String username) {
		for(Manager manager : managers) {
			if(manager.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
}
