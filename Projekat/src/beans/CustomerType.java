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
	public boolean changeCustomerType(int points) {
		boolean retVal = false;
		switch("name") {
			case "Bronze":{
				if(points > 1000) {
					name = "Silver";
					neededPoints = 1000;
					discount = 5;
					retVal = true;
				}
			}
			case "Silver":{
				if(points > 1500) {
					name = "Gold";
					neededPoints = 1500;
					discount = 10;
					retVal = true;
				}else if(points <= 1000) {
					name = "Bronze";
					neededPoints = 1000;
					discount = 0;
					retVal = true;
				}
			}
			case "Gold":{
				if(points > 2500) {
					name = "Platinum";
					neededPoints = 2500;
					discount = 15;
					retVal = true;
				}
				else if(points <= 1500) {
					name = "Silver";
					neededPoints = 1500;
					discount = 5;
					retVal = true;
				}
			}
			case "Platinum":{
				if(points > 5000) {
					name = "Diamond";
					neededPoints = 5000;
					discount = 20;
					retVal = true;
				}
				else if(points <= 1500) {
					name = "Gold";
					neededPoints = 1500;
					discount = 10;
					retVal = true;
				}
			}
			case "Diamond":{
				if(points < 5000) {
					name = "Platinum";
					neededPoints = 2500;
					discount = 15;
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
}