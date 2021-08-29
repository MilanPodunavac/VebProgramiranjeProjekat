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

import beans.DeliveryRequest;

public class DeliveryRequestSerializer {

private String imeFajla = "database" + File.separator + "administrators.txt";
	
	public DeliveryRequestSerializer(String contextPath){
		imeFajla = contextPath + File.separator + "database" + File.separator + "deliveryRequests.txt";
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
	
	public DeliveryRequestSerializer(){
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
	
	public void Save(ArrayList<DeliveryRequest> deliveryRequests) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(deliveryRequests));
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
	
	public ArrayList<DeliveryRequest> Load(){
		ArrayList<DeliveryRequest> managers = new ArrayList<DeliveryRequest>();
		try {
			managers = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<DeliveryRequest>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return managers;
	}
	
	public boolean Add(DeliveryRequest deliveryRequest) {
		ArrayList<DeliveryRequest> allRequests = Load();
		ArrayList<DeliveryRequest> activeRequests = new ArrayList<DeliveryRequest>();
		for(DeliveryRequest request : allRequests) {
			if(!request.isDeleted() && request.isPending()) {
				activeRequests.add(request);
			}
		}
		boolean unique = true;
		for(DeliveryRequest request : activeRequests) {
			if(!(request.getDelivererUsername().equals(deliveryRequest.getDelivererUsername())
					|| request.getDeliveryId().equals(deliveryRequest.getDeliveryId())
					|| request.getManagerUsername().equals(deliveryRequest.getManagerUsername())
					)) {
				unique = false;
			}
		}
		if(unique) {
			allRequests.add(deliveryRequest);
			Save(allRequests);
		}
		return unique;
	}
	
	public boolean Update(DeliveryRequest request) {
		ArrayList<DeliveryRequest> requests = Load();
		for(DeliveryRequest r : requests) {
			if(r.getDelivererUsername().equals(request.getDelivererUsername())
					|| r.getDeliveryId().equals(request.getDeliveryId())
					|| r.getManagerUsername().equals(request.getManagerUsername())) {
				requests.remove(r);
				requests.add(request);
				Save(requests);
				return true;
			}
		}
		return false;
	}
	
	public boolean Delete(DeliveryRequest request) {
		ArrayList<DeliveryRequest> requests = Load();
		for(DeliveryRequest r : requests) {
			if(r.getDelivererUsername().equals(request.getDelivererUsername())
					|| r.getDeliveryId().equals(request.getDeliveryId())
					|| r.getManagerUsername().equals(request.getManagerUsername())) {
				r.setDeleted(true);
				Save(requests);
				return true;
			}
		}
		return false;
	}
}
