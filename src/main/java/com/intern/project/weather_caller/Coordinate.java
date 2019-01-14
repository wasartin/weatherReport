package com.intern.project.weather_caller;

public class Coordinate {
	private double longitude;
	private double latitude;
	
	public Coordinate(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Coordinate(Object longitude, Object latitude) {
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	
}
