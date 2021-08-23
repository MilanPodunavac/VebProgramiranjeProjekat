package dao;

import java.util.ArrayList;
import java.util.List;

import beans.Comment;
import beans.Restaurant;

public class CommentDao {

	private ArrayList<Comment> comments;

	public CommentDao(ArrayList<Comment> comments) {
		super();
		this.comments = comments;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	public List<Comment> getRestaurantComments(Restaurant restaurant){
		return null;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
}
