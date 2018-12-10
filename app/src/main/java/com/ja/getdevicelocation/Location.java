package com.ja.getdevicelocation;
/**
 * This class creates a location object
 * 
 * @author scarlett
 *
 */
public class Location {
    private double lat;
    private double lon;
	/**
	 * constructor for location object with latitude and longitude
	 * 
	 * @param lat
	 * @param lon
	 */
    Location(double lat,double lon){
        this.lat=lat;
        this.lon=lon;
    }
	/**
	 * getter method
	 * 
	 * @return latitude
	 */
    public double getLat() {
        return lat;
    }
	/**
	 * setter method
	 * 
	 * @param lat
	 */
    public void setLat(double lat) {
        this.lat = lat;
    }
	/**
	 * getter method
	 * 
	 * @return longitude
	 */
    public double getLon() {
        return lon;
    }
    /**
	 * setter method
	 * 
	 * @param lon
	 */
    public void setLon(double lon) {
        this.lon = lon;
    }
}
