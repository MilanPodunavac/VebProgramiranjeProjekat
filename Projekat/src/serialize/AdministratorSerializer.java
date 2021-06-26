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

import beans.Administrator;

public class AdministratorSerializer {

	private final String imeFajla = "database" + File.separator + "administrators.txt";
	
	public AdministratorSerializer(){
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
	
	public void Save(ArrayList<Administrator> administrators) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(administrators));
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
	
	public ArrayList<Administrator> Load(){
		ArrayList<Administrator> managers = new ArrayList<Administrator>();
		try {
			managers = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<Administrator>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return managers;
	}
	
	public boolean Add(Administrator administrators) {
		ArrayList<Administrator> customers = Load();
		boolean unique = CheckUnique(administrators.getUsername());
		if(unique) {
			customers.add(administrators);
			Save(customers);
		}
		return unique;
	}
	
	public boolean CheckUnique(String username) {
		return new UsernameChecker().Check(username);
	}
	
	public boolean Update(Administrator administrator) {
		ArrayList<Administrator> administrators = Load();
		for(Administrator a : administrators) {
			if(a.getUsername().equals(administrator.getUsername())) {
				a = administrator;
				Save(administrators);
				return true;
			}
		}
		return false;
	}
	
	public boolean Delete(Administrator administrator) {
		ArrayList<Administrator> administrators = Load();
		for(Administrator a : administrators) {
			if(a.getUsername().equals(administrator.getUsername())) {
				a.setDeleted(true);
				Save(administrators);
				return true;
			}
		}
		return false;
	}
}
