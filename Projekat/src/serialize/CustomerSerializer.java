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

import beans.Customer;

public class CustomerSerializer {

	private final String imeFajla = "database" + File.separator + "customers.txt";
	
	public CustomerSerializer(){
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
	
	public void Save(ArrayList<Customer> customers) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(customers));
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
	
	public ArrayList<Customer> Load(){
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			customers = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<Customer>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public boolean Add(Customer customer) {
		ArrayList<Customer> customers = Load();
		boolean unique = CheckUnique(customer.getUsername());
		if(unique) {
			customers.add(customer);
			Save(customers);
		}
		return unique;
	}
	
	public boolean CheckUnique(String username) {
		return new UsernameChecker().Check(username);
	}
	
	public boolean Update(Customer customer) {
		ArrayList<Customer> customers = Load();
		for(Customer c : customers) {
			if(c.getUsername().equals(customer.getUsername())) {
				c = customer;
				Save(customers);
				return true;
			}
		}
		return false;
	}
	
	public boolean Delete(Customer customer) {
		ArrayList<Customer> customers = Load();
		for(Customer c : customers) {
			if(c.getUsername().equals(customer.getUsername())) {
				c.setDeleted(true);
				Save(customers);
				return true;
			}
		}
		return false;
	}
	
}
