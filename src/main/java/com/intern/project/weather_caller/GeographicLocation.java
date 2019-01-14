package com.intern.project.weather_caller;

public class GeographicLocation {
	
	//JSON either returns long or Integer
	private long id;
	private String cityName;
	private String country;
	private Coordinate coordinate;
	
	public GeographicLocation() {
		
	}
	
	public GeographicLocation(long id, String cityName, String country, Coordinate coordinate) {
		this.id = id;
		this.cityName = cityName;
		this.country = country;
		this.coordinate = coordinate;
	}
	
	public long getID() {
		return id;
	}
	
	public void setID(long id) {
		this.id = id;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
}

