package com.intern.project.weather_caller;

public class Coordinate {
	private Double longitude;
	private Double latitude;
	
	public Coordinate(Double longitude, Double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Coordinate(Object longitude, Object latitude) {
		this.longitude = (Double)longitude;
		this.latitude = (Double)latitude;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	
}
