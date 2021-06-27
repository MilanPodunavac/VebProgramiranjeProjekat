package beans;

import java.util.*;

public class Location {
	private double latitude;
	private double longitude;
	private String streetName;
	private int streetNumber;
	private String cityName;
	private int cityNumber;
   
	public Location(double latitude, double longitude, String streetName, int streetNumber, String cityName,
			int cityNumber) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.cityName = cityName;
		this.cityNumber = cityNumber;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public int getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getCityNumber() {
		return cityNumber;
	}
	public void setCityNumber(int cityNumber) {
		this.cityNumber = cityNumber;
	}
	
	public boolean equals(Location location) {
		return location.getLatitude() == this.getLatitude()
				&& location.getLongitude() == this.getLongitude()
				&& location.getStreetName().equals(this.getStreetName())
				&& location.getStreetNumber() == this.getStreetNumber()
				&& location.getCityName().equals(this.getCityName())
				&& location.getCityNumber() == this.getCityNumber();
	}
   
}