package com.ja.getdevicelocation;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class makes an API call, parse the JSON response and uses the response
 * values to create a WeatherInfo object.
 * 
 * @author yilinsun, GJY, Scarlett Yu
 *
 */

public class WeatherAPI {
	final double TEMP_CONVERT = 273.15;
	private Exception exception;

	/**
	 * Parse the JSON response String
	 * 
	 * @param jsonResponse
	 * @return WeatherInfo object
	 * @throws JSONException
	 */
	public WeatherInfo parseWeatherJSON(String jsonResponse) throws JSONException {
		// create a JSON object with the String response
		JSONObject jObj = new JSONObject(jsonResponse);
		// Look at the raw String response
		// Look for the results key
		// After the colon there is a square bracket indicating a JSONArray
		JSONArray jArray1 = jObj.getJSONArray("weather");
		String mainWeather = jArray1.getJSONObject(0).getString("main");
		double tempMin = jObj.getJSONObject("main").getDouble("temp_min") - TEMP_CONVERT;
		tempMin = (double) (Math.round(tempMin * 100) / 100.0);
		double tempMax = jObj.getJSONObject("main").getDouble("temp_max") - TEMP_CONVERT;
		tempMax = (double) (Math.round(tempMax * 100) / 100.0);
		double pressure = jObj.getJSONObject("main").getDouble("pressure");
		int humid = jObj.getJSONObject("main").getInt("humidity");
		String city = jObj.getString("name");
		WeatherInfo wgeo = new WeatherInfo(city, tempMin, tempMax, mainWeather, pressure, humid);

		return wgeo;
	}

	/**
	 * makes URL
	 * 
	 * @param latNum
	 * @param lonNum
	 * @return string of weather URL
	 */
	public String createURL(double latNum, double lonNum) {
		String endPoint = "https://api.openweathermap.org";
		String url2 = "/data/2.5/weather?lat=";
		String lon = "&lon=";
		String key = "&appid=93878e3b8fc1c0224d9e17f353cf474f";
		String weatherUrl = endPoint + url2 + latNum + lon + lonNum + key;
		return weatherUrl;
	}

	/**
	 * parse in response and get geo weather object
	 * 
	 * @param response
	 * @return geo weather object
	 */
	public WeatherInfo getWgeo(String response) {

		WeatherAPI weatherAPI = new WeatherAPI();
		WeatherInfo wgeo = new WeatherInfo("", 0.0, 0.0, "", 0.0, 0);
		try {
			wgeo = weatherAPI.parseWeatherJSON(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return wgeo;
	}

	/**
	 * Makes the API call and returns the JSON result as a String
	 * 
	 * @param url
	 * @return string of URL contents
	 * @throws IOException
	 */
	public String makeAPICall(URL url) throws IOException {

		URLConnection yc;
		BufferedReader in;

		yc = url.openConnection();
		in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;

		// Why StringBuffer? - StringBuffer is mutable so we can append to it
		StringBuffer response = new StringBuffer();
		// BufferedReader does not have a "hasNext" type method so this is how to check
		// for
		// more input
		// if it has more input append to the StringBuffer
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

}