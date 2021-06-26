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

import beans.Deliverer;

public class DelivererSerializer {

	private final String imeFajla = "database" + File.separator + "deliverers.txt";
	
	public DelivererSerializer(){
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
	
	public void Save(ArrayList<Deliverer> deliverers) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(deliverers));
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
	
	public ArrayList<Deliverer> Load(){
		ArrayList<Deliverer> deliverers = new ArrayList<Deliverer>();
		try {
			deliverers = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<Deliverer>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deliverers;
	}
	
	public boolean Add(Deliverer deliverers) {
		ArrayList<Deliverer> customers = Load();
		boolean unique = CheckUnique(deliverers.getUsername());
		if(unique) {
			customers.add(deliverers);
			Save(customers);
		}
		return unique;
	}
	
	public boolean CheckUnique(String username) {
		return new UsernameChecker().Check(username);
	}
	
	public boolean Update(Deliverer deliverer) {
		ArrayList<Deliverer> deliverers = Load();
		for(Deliverer d : deliverers) {
			if(d.getUsername().equals(deliverer.getUsername())) {
				d = deliverer;
				Save(deliverers);
				return true;
			}
		}
		return false;
	}
	
	public boolean Delete(Deliverer deliverer) {
		ArrayList<Deliverer> deliverers = Load();
		for(Deliverer d : deliverers) {
			if(d.getUsername().equals(deliverer.getUsername())) {
				d.setDeleted(true);
				Save(deliverers);
				return true;
			}
		}
		return false;
	}
	
}
