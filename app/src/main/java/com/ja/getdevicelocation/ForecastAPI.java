package com.ja.getdevicelocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class makes an API call, parse the JSON response and uses the response
 * values to create an ArrayList of DailyWeather objects.
 * 
 * @author Scarlett Yu
 *
 */
public class ForecastAPI {
	final double TEMP_CONVERT = 273.15;

	/**
	 * Parse the JSON response String
	 * 
	 * @param jsonResponse
	 * @return ArrayList of DailyWeather objects
	 * @throws JSONException
	 */
	public List<DailyWeather> parseWeatherJSON(String jsonResponse) throws JSONException {
		// create a JSON object with the String response
		JSONObject jObj = new JSONObject(jsonResponse);
		JSONArray jArray1 = jObj.getJSONArray("list");
		String city = jObj.getJSONObject("city").getString("name");
		ArrayList<DailyWeather> dWeatherArray = new ArrayList<DailyWeather>();

		for (int i = 0; i < jArray1.length(); i++) {

			JSONObject ob = jArray1.getJSONObject(i);

			JSONArray weather = ob.getJSONArray("weather");
			String descript = weather.getJSONObject(0).getString("main");
			// System.out.println(descript);

			JSONObject obMain = ob.getJSONObject("main");
			double tempMin = obMain.getDouble("temp_min") - TEMP_CONVERT;
			tempMin = (double) (Math.round(tempMin * 100) / 100.0);

			double tempMax = obMain.getDouble("temp_max") - TEMP_CONVERT;
			tempMax = (double) (Math.round(tempMax * 100) / 100.0);
			// System.out.println(tempMax);

			double pressure = obMain.getDouble("pressure");
			// System.out.println(pressure);

			double humidity = obMain.getDouble("humidity");
			// System.out.println(humidity);

			String time = ob.getString("dt_txt");

			String[] times = time.split(" ");
			String[] hour = times[1].split(":");

			if (hour[0].equals("00") || hour[0].equals("15")) {

				DailyWeather dWeather = new DailyWeather(city, tempMin, tempMax, descript, pressure, humidity);
				dWeatherArray.add(dWeather);
			}
		}

		return dWeatherArray;
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

	/**
	 * Creates URL string
	 * 
	 * @param latNum
	 * @param lonNum
	 * @return full URL
	 */
	public String createURL(Double latNum, Double lonNum) {
		String endPoint = "https://api.openweathermap.org";
		String url2 = "/data/2.5/forecast?lat=";
		String lon = "&lon=";
		String key = "&appid=93878e3b8fc1c0224d9e17f353cf474f";
		String weatherUrl = endPoint + url2 + latNum + lon + lonNum + key;
		return weatherUrl;
	}

	/**
	 * Gets geo forecast data
	 * 
	 * @param response
	 * @return an Arraylist of DailyWeather objects
	 */
	public List<DailyWeather> getGeoForecast(String response) {

		ForecastAPI forecastAPI = new ForecastAPI();
		List<DailyWeather> forecast = new ArrayList<>();
		try {
			forecast = forecastAPI.parseWeatherJSON(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return forecast;
	}

}
