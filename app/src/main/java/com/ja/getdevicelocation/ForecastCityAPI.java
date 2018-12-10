package com.ja.getdevicelocation;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ForecastCityAPI {
	final double TEMP_CONVERT = 273.15;

	/**
	 * Parse the JSON response String
	 *
	 * @param jsonResponse
	 * @return ArrayList of DailyWeather objects
	 * @throws JSONException
	 */
	public List<DailyWeather> parseWeatherJSON(String jsonResponse) throws JSONException {
		//create a JSON object with the String response
		JSONObject jObj = new JSONObject(jsonResponse);
		//Look at the raw String response
		//Look for the results key
		//After the colon there is a square bracket indicating a JSONArray
		JSONArray jArray1 = jObj.getJSONArray("list");

		String city = jObj.getJSONObject("city").getString("name");

		ArrayList<DailyWeather> dWeatherArray= new ArrayList<DailyWeather>();


		for (int i = 0; i < jArray1.length(); i++) {

			JSONObject ob = jArray1.getJSONObject(i);

			JSONArray weather = ob.getJSONArray("weather");
			String descript = weather.getJSONObject(0).getString("description");


			JSONObject obMain = ob.getJSONObject("main");
			double tempMin = obMain.getDouble("temp_min") - TEMP_CONVERT;
			tempMin=(double)(Math.round(tempMin*100)/100.0);

			double tempMax = obMain.getDouble("temp_max") - TEMP_CONVERT;
			tempMax=(double)(Math.round(tempMax*100)/100.0);

			double pressure = obMain.getDouble("pressure");
			//System.out.println(pressure);

			double humidity = obMain.getDouble("humidity");


			String date = ob.getString("dt_txt");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();


			try {
				cal.setTime(sdf.parse(date));               // parse input

			} catch (ParseException e) {
				e.printStackTrace();
			}

			DailyWeather dWeather = new DailyWeather(city,tempMin,tempMax,descript,pressure,humidity, cal);
			dWeatherArray.add(dWeather);
		}

		List<DailyWeather> res=arrayProcess(dWeatherArray);

		return res;
	}

	private List<DailyWeather> arrayProcess(List<DailyWeather> dailyWeathers){
		int today=dailyWeathers.get(0).getCal().get(Calendar.DAY_OF_MONTH);
		List<DailyWeather> res=new ArrayList<>();
		for(DailyWeather day:dailyWeathers){
			if(day.getCal().get(Calendar.DAY_OF_MONTH)>today && day.getCal().get(Calendar.HOUR_OF_DAY)==0){
				res.add(day);
			}
		}
		return res;
	}

	/**
	 * Makes the API call and returns the JSON result as a String
	 *
	 * @param url
	 * @return string of URL contents
	 * @throws IOException
	 */
	public String makeAPICall(URL url) throws IOException {
		URL recipeWeather;
		URLConnection yc;
		BufferedReader in;


		yc = url.openConnection();
		in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;

		//Why StringBuffer? - StringBuffer is mutable so we can append to it
		StringBuffer response = new StringBuffer();
		//BufferedReader does not have a "hasNext" type method so this is how to check for
		//more input
		//if it has more input append to the StringBuffer
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
	/**
	 * Creates URL string
	 * 
	 * @param cityName
	 * @return string of weather URL
	 */
	public String createURL(String cityName) {
		String endPoint = "https://api.openweathermap.org";
		String url2 = "/data/2.5/forecast?q=";
		String key = "&appid=93878e3b8fc1c0224d9e17f353cf474f";
		String weatherUrl = endPoint + url2 + cityName + key;
		return weatherUrl;
	}
	/**
	 * Gets city forecast data
	 * 
	 * @param response
	 * @return an Arraylist of DailyWeather objects
	 */
	public List<DailyWeather> getCityForecast(String response) {

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