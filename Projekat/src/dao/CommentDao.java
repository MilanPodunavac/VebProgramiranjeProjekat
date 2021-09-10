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
		List<Comment> restaurantComments = new ArrayList<Comment>();
		for(Comment comment : comments) {
			if(comment.getRestaurant().getName().equals(restaurant.getName())
					&& comment.getRestaurant().checkLocation(restaurant)) {
				restaurantComments.add(comment);
			}
		}
		return restaurantComments;
	}
	
	public List<Comment> getApprovedRestaurantComments(Restaurant restaurant){
		List<Comment> restaurantComments = getRestaurantComments(restaurant);
		List<Comment> approvedRestaurantComments = new ArrayList<Comment>();
		for(Comment comment : restaurantComments) {
			if(comment.isApproved()) {
				approvedRestaurantComments.add(comment);
			}
		}
		return approvedRestaurantComments;
	}
	
	public List<Comment> getNotApprovedRestaurantComments(Restaurant restaurant){
		List<Comment> restaurantComments = getRestaurantComments(restaurant);
		List<Comment> notApprovedRestaurantComments = new ArrayList<Comment>();
		for(Comment comment : restaurantComments) {
			if(!comment.isApproved() && !comment.isRejected()) {
				notApprovedRestaurantComments.add(comment);
			}
		}
		return notApprovedRestaurantComments;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	
	public double getAverageGrade(Restaurant restaurant) {
		double gradeSum = 0;
		List<Comment> commentList = getApprovedRestaurantComments(restaurant);
		for(Comment comment : commentList) {
			gradeSum += comment.getGrade();
		}
		return (gradeSum == 0) ? gradeSum : gradeSum / commentList.size();
	}
}
