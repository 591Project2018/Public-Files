package com.ja.getdevicelocation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class creates an DailyWeather Object with city name
 * 
 * @author scarlett
 *
 */
public class DailyWeather {

	private double temMin;
	private String city;
	private double temMax;
	private String discript;
	private double humid;
	private double pressure;
	private Calendar cal;

	/**
	 * COnstructor for DailyWeather object
	 * 
	 * @param city
	 * @param temMin
	 * @param temMax
	 * @param discript
	 * @param pressure
	 * @param humid
	 * @param cal
	 */
	public DailyWeather(String city, double temMin, double temMax, String discript, double pressure, double humid,
			Calendar cal) {
		this.temMin = temMin;
		this.city = city;
		this.temMax = temMax;
		this.discript = discript;
		this.pressure = pressure;
		this.humid = humid;
		this.cal = cal;

	}

	/**
	 * getter method
	 * 
	 * @return minimum temperature
	 */
	public double getTemMin() {
		return temMin;
	}

	/**
	 * getter method
	 * 
	 * @return maximum temperature
	 */
	public double getTemMax() {
		return temMax;
	}

	/**
	 * getter method
	 * 
	 * @return city name
	 */
	public String getCity() {
		return city;
	}

	/**
	 * getter method
	 * 
	 * @return description of weather
	 */
	public String getDiscription() {
		return discript;
	}

	/**
	 * getter method
	 * 
	 * @return humidity
	 */
	public double getHumid() {
		return humid;
	}

	/**
	 * getter method
	 * 
	 * @return pressure
	 */
	public double getPressure() {
		return pressure;
	}

	/**
	 * getter method
	 * 
	 * @return cal
	 */
	public Calendar getCal() {
		return cal;
	}

	/**
	 * setter method
	 * 
	 * @param cal
	 */
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
}
