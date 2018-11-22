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
 * This class demos how to make an API call, parse the JSON response and uses the response
 * values to create an ArrayList of RecipePuppyRecipe objects.
 * @author yilinsun
 *
 */
public class WeatherAPI {
	
	/**
	 * Parse the JSON response String
	 * @param jsonResponse
	 * @return ArrayList of RecipePuppyRecipe objects
	 * @throws JSONException
	 */
	public ArrayList<WeatherInfo> parseWeatherJSON(String jsonResponse) throws JSONException {
		//create a JSON object with the String response
		JSONObject jObj = new JSONObject(jsonResponse);
		//Look at the raw String response
		//Look for the results key
		//After the colon there is a square bracket indicating a JSONArray
		JSONArray jArray1 = jObj.getJSONArray("weather");
		
		
		
		ArrayList<WeatherInfo> weatherArray = new ArrayList<WeatherInfo>();
		
		
			String mainWeather=jArray1.getJSONObject(0).getString("main");
			double tempMin=jObj.getJSONObject("main").getDouble("temp_min");
			double tempMax=jObj.getJSONObject("main").getDouble("temp_max");
		
			String city=jObj.getString("name");
			WeatherInfo rpr = new WeatherInfo(city,tempMin,tempMax,mainWeather);
			weatherArray.add(rpr);
		
		return weatherArray;
	}
	
	/**
	 * Makes the API call and returns the JSON result as a String
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
	
	public static void main(String[] args) {
		Location place=new Location(11,33);
		//create the API URL
		String endPoint="https://api.openweathermap.org";
		String url2="/data/2.5/weather?lat=";
		double latNum=place.getLat();
		String lon="&lon=";
		double lonNum=place.getLon();
		String key="&appid=93878e3b8fc1c0224d9e17f353cf474f";
		String weatherUrl =endPoint+url2+latNum+lon+lonNum+key;
		
		WeatherAPI recipePuppy = new WeatherAPI();
		ArrayList<WeatherInfo> rpRecipes = new ArrayList<>();
		
		try {
			//make the API call and get a String response
			String jsonResponse = recipePuppy.makeAPICall(weatherUrl);
			//parse the response and get an ArrayList of RecipePuppyRecipe objects
			rpRecipes = recipePuppy.parseWeatherJSON(jsonResponse);
			//view the results in a proper Java object
			for(WeatherInfo rpr: rpRecipes) {
				System.out.println(rpr.getCity());
				System.out.println(rpr.getMainWeather());
				System.out.println(rpr.getTemMax());
				System.out.println(rpr.getTemMin());
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
