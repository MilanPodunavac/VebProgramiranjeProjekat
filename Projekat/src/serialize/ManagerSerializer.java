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

	private String imeFajla = "database" + File.separator + "managers.txt";
	
	public ManagerSerializer(String contextPath){
		imeFajla = contextPath + File.separator + "database" + File.separator + "managers.txt";
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
	
	public boolean Add(Manager manager, String contextPath) {
		ArrayList<Manager> managers = Load();
		boolean unique = CheckUnique(manager.getUsername(), contextPath);
		if(unique) {
			managers.add(manager);
			Save(managers);
		}
		return unique;
	}
	
	public boolean CheckUnique(String username, String contextPath) {
		return new UsernameChecker().Check(username, contextPath);
	}
	
	public boolean Update(Manager manager) {
		ArrayList<Manager> managers = Load();
		for(Manager m : managers) {
			if(m.getUsername().equals(manager.getUsername())) {
				managers.remove(m);
				managers.add(manager);
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
