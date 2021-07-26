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

import beans.Comment;

public class CommentSerializer {

private String imeFajla = "database" + File.separator + "comments.txt";

	public CommentSerializer(String contextPath){
		imeFajla = contextPath + File.separator + "database" + File.separator + "comments.txt";
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
	
	public CommentSerializer(){
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
	
	public void Save(ArrayList<Comment> comments) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(imeFajla, "UTF-8");
			try {
				writer.println(new ObjectMapper().writeValueAsString(comments));
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
	
	public ArrayList<Comment> Load(){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		try {
			comments = new ObjectMapper().readValue(new File(imeFajla), new TypeReference<ArrayList<Comment>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	public boolean Add(Comment comment) {
		ArrayList<Comment> comments = Load();
		comments.add(comment);
		Save(comments);
		return true;
	}
	
}
