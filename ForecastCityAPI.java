
package com.ja.getdevicelocation;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.MalformedURLException;
	import java.net.URL;
	import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
	import java.util.List;

	import org.json.JSONArray;
	import org.json.JSONException;
	import org.json.JSONObject;

	/**
	 * This class demos how to make an API call, parse the JSON response and uses the response
	 * values to create an ArrayList of RecipePuppyRecipe objects.
	 * @author Scarlett Yu
	 *
	 */
	public class ForecastCityAPI {
		final double TEMP_CONVERT = 273.15;

		/**
		 * Parse the JSON response String
		 *
		 * @param jsonResponse
		 * @return ArrayList of RecipePuppyRecipe objects
		 * @throws JSONException
		 */
		public ArrayList<DailyWeather> parseWeatherJSON(String jsonResponse) throws JSONException {
			//create a JSON object with the String response
			JSONObject jObj = new JSONObject(jsonResponse);
			//Look at the raw String response
			//Look for the results key
			//After the colon there is a square bracket indicating a JSONArray
			JSONArray jArray1 = jObj.getJSONArray("list");

			String city = jObj.getJSONObject("city").getString("name");

			ArrayList<DailyWeather> dWeatherArray0 = new ArrayList<DailyWeather>();
			ArrayList<DailyWeather> dWeatherArray = new ArrayList<DailyWeather>();

			for (int i = 0; i < jArray1.length(); i++) {

				JSONObject ob = jArray1.getJSONObject(i);

				JSONArray weather = ob.getJSONArray("weather");
				String descript = weather.getJSONObject(0).getString("description");
				//System.out.println(descript);

				JSONObject obMain = ob.getJSONObject("main");
				double tempMin = obMain.getDouble("temp_min") - TEMP_CONVERT;
				System.out.println(tempMin);

				double tempMax = obMain.getDouble("temp_max") - TEMP_CONVERT;
				//System.out.println(tempMax);

				double pressure = obMain.getDouble("pressure");
				//System.out.println(pressure);

				double humidity = obMain.getDouble("humidity");
				//System.out.println(humidity);

				String time = ob.getString("dt_txt");


				DailyWeather dWeather = new DailyWeather(city, tempMin, tempMax, descript, pressure, humidity);
				dWeatherArray0.add(dWeather);
			}

			for (DailyWeather d : dWeatherArray) {
				if (dWeatherArray.size() > 5) {
					dWeatherArray.remove(0);
				}
			}

			return dWeatherArray;
		}

		/**
		 * Makes the API call and returns the JSON result as a String
		 *
		 * @param url
		 * @return
		 * @throws IOException
		 */
		public String makeAPICall(String url) throws IOException {
			URL recipeWeather;
			URLConnection yc;
			BufferedReader in;

			recipeWeather = new URL(url);
			yc = recipeWeather.openConnection();
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


		public String createURL(String cityName) {
			String endPoint = "https://api.openweathermap.org";
			String url2 = "/data/2.5/forecast?q=";

			String key = "&appid=93878e3b8fc1c0224d9e17f353cf474f";
			String weatherUrl = endPoint + url2 + cityName + key;
			return weatherUrl;
		}

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