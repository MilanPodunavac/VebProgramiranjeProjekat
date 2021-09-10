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

import beans.ImageBase64;

public class ImageBase64Serializer {

	private String imeFajla = "database" + File.separator + "images64.txt";
	
	public ImageBase64Serializer(String contextPath){
		imeFajla = contextPath + File.separator + "database" + File.separator + "images64.txt";
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
	
	public ImageBase64Serializer(){
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
	
	public void Save(ArrayList<ImageBase64> images) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(images));
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
	
	public ArrayList<ImageBase64> Load(){
		ArrayList<ImageBase64> managers = new ArrayList<ImageBase64>();
		try {
			managers = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<ImageBase64>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return managers;
	}
	
	public boolean Add(ImageBase64 image, String contextPath) {
		ArrayList<ImageBase64> images = Load();
		boolean unique = CheckUnique(image.getId(), contextPath);
		if(unique) {
			images.add(image);
			Save(images);
		}
		return unique;
	}
	
	public boolean CheckUnique(String id, String contextPath) {
		return new UsernameChecker().Check(id, contextPath);
	}
	
	public boolean Update(ImageBase64 administrator) {
		ArrayList<ImageBase64> administrators = Load();
		for(ImageBase64 a : administrators) {
			if(a.getId().equals(administrator.getId())) {
				administrators.remove(a);
				administrators.add(administrator);
				Save(administrators);
				return true;
			}
		}
		return false;
	}
	
}
