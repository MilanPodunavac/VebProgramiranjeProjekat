package dao;

import java.util.ArrayList;

import beans.User;

public class UserDao {
	private ArrayList<User> users;

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public UserDao(ArrayList<User> users) {
		super();
		this.users = users;
	}
	public UserDao() {
		
	}
	
}
