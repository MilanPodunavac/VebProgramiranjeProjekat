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

import beans.Location;
import beans.Restaurant;

public class RestaurantSerializer {

private final String imeFajla = "database" + File.separator + "restaurants.txt";
	
	public RestaurantSerializer(){
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
	
	public void Save(ArrayList<Restaurant> restaurants) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(restaurants));
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
	
	public ArrayList<Restaurant> Load(){
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			restaurants = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<Restaurant>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return restaurants;
	}
	
	public boolean Add(Restaurant restaurant) {
		ArrayList<Restaurant> restaurants = Load();
		boolean unique = CheckUnique(restaurant.getLocation(), restaurants);
		if(unique) {
			restaurants.add(restaurant);
			Save(restaurants);
		}
		return unique;
	}
	
	public boolean CheckUnique(Location location, ArrayList<Restaurant> restaurants) {
		for(Restaurant r : restaurants) {
			if(r.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean Update(Restaurant restaurant) {
		ArrayList<Restaurant> restaurants = Load();
		for(Restaurant r : restaurants) {
			if(r.getLocation().equals(restaurant.getLocation())) {
				restaurants.remove(r);
				restaurants.add(restaurant);
				Save(restaurants);
				return true;
			}
		}
		return false;
	}
	
	public boolean Delete(Restaurant restaurant) {
		ArrayList<Restaurant> restaurants = Load();
		for(Restaurant r : restaurants) {
			if(r.getLocation().equals(restaurant.getLocation())) {
				r.setDeleted(true);
				Save(restaurants);
				return true;
			}
		}
		return false;
	}
}
