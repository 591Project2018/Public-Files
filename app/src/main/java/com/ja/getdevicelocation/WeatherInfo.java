package com.ja.getdevicelocation;

/**
 * This class creates a WeatherInfo object
 * 
 * @author scarlett
 *
 */
public class WeatherInfo {
	private double temMin;
	private String city;
	private double temMax;
	private String mainWeather;
	private int humid;
	private double pressure;

	/**
	 * constructor for WeatherInfo object with city name
	 * 
	 * @param city
	 * @param temMin
	 * @param temMax
	 * @param mainWeather
	 * @param pressure
	 * @param humid
	 */
	public WeatherInfo(String city, double temMin, double temMax, String mainWeather, double pressure, int humid) {
		this.temMin = temMin;
		this.city = city;
		this.temMax = temMax;
		this.mainWeather = mainWeather;
		this.pressure = pressure;
		this.humid = humid;

	}

	/**
	 * getter method
	 * 
	 * @return min tenmp
	 */
	public double getTemMin() {
		return temMin;
	}

	/**
	 * getter method
	 * 
	 * @return max temp
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
	 * @return main weather
	 */
	public String getMainWeather() {
		return mainWeather;
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

}
