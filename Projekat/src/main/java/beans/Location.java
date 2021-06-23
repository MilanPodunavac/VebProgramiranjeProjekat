package beans;

public class Location {

	private double latitude; //geographical length
	private double longitude; //geographical width
	
	private String streetName;
	private int streetNumber;
	
	private String cityName;
	private int cityPostalNumber;
	
	public Location(double latitude, double longitude, String streetName, int streetNumber, String cityName,
			int cityPostalNumber) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.cityName = cityName;
		this.cityPostalNumber = cityPostalNumber;
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

	public int getCityPostalNumber() {
		return cityPostalNumber;
	}

	public void setCityPostalNumber(int cityPostalNumber) {
		this.cityPostalNumber = cityPostalNumber;
	}
	
	
	
}
