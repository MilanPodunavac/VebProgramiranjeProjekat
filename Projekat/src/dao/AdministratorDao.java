package dao;

import java.util.ArrayList;

import beans.Administrator;
import serialize.AdministratorSerializer;

public class AdministratorDao {
	private ArrayList<Administrator> administrators;
	
	public AdministratorDao(ArrayList<Administrator> administrators) {
		super();
		this.administrators = administrators;
	}

	public ArrayList<Administrator> getAdministrators() {
		return administrators;
	}

	public void setAdministrators(ArrayList<Administrator> administrators) {
		this.administrators = administrators;
	}
	
	public Administrator find(String username, String password) {
		Administrator administrator = null;
		for(Administrator a : administrators) {
			if(a.getUsername().equals(username) && a.getPassword().equals(password)) {
				administrator = a;
			}
		}
		return administrator;
	}
	
	public boolean IsUniqueUsername(String username) {
		for(Administrator administrator : administrators) {
			if(administrator.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	
}
