package beans;

import java.util.*;

public class CustomerType {
	private String name;
	private double discount;
	private int neededPoints;
	
	public CustomerType() {
		super();
	}
	
	public CustomerType(String name, double discount, int neededPoints) {
		super();
		this.name = name;
		this.discount = discount;
		this.neededPoints = neededPoints;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getNeededPoints() {
		return neededPoints;
	}
	public void setNeededPoints(int neededPoints) {
		this.neededPoints = neededPoints;
	}
	
}