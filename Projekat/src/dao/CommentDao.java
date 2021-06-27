package dao;

import java.util.ArrayList;

import beans.Comment;

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
}
