package serialize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Manager;

public class ManagerSerializer {

	private final String imeFajla = "database" + File.separator + "managers.txt";
	
	public ManagerSerializer(){
		File file = new File(imeFajla);
		if(!file.exists()) {
			try {
				PrintWriter writer;
				writer = new PrintWriter(imeFajla, "UTF-8");
				writer.println("[]");
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void Save(ArrayList<Manager> managers) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(managers));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Manager> Load(){
		ArrayList<Manager> managers = new ArrayList<Manager>();
		try {
			managers = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<Manager>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return managers;
	}
	
	public boolean Add(Manager managers) {
		ArrayList<Manager> customers = Load();
		boolean unique = CheckUnique(managers.getUsername());
		if(unique) {
			customers.add(managers);
			Save(customers);
		}
		return unique;
	}
	
	public boolean CheckUnique(String username) {
		return new UsernameChecker().Check(username);
	}
	
	public boolean Update(Manager manager) {
		ArrayList<Manager> managers = Load();
		for(Manager m : managers) {
			if(m.getUsername().equals(manager.getUsername())) {
				m = manager;
				Save(managers);
				return true;
			}
		}
		return false;
	}
	
	public boolean Delete(Manager manager) {
		ArrayList<Manager> managers = Load();
		for(Manager m : managers) {
			if(m.getUsername().equals(manager.getUsername())) {
				m.setDeleted(true);
				Save(managers);
				return true;
			}
		}
		return false;
	}

}
