
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
public class CityAPI {

    /**
     * Parse the JSON response String
     *
     * @param jsonResponse
     * @return ArrayList of RecipePuppyRecipe objects
     * @throws JSONException
     */

    public static final double TEMP_CONVERT = 273.15;

    public WeatherInfo parseCityWeatherJSON(String jsonResponse) throws JSONException {
        //create a JSON object with the String response
        JSONObject jObj = new JSONObject(jsonResponse);
        //Look at the raw String response
        //Look for the results key
        //After the colon there is a square bracket indicating a JSONArray
        JSONArray jArray1 = jObj.getJSONArray("weather");
        String mainWeather=jArray1.getJSONObject(0).getString("main");
        double tempMin=jObj.getJSONObject("main").getDouble("temp_min")-TEMP_CONVERT;
        tempMin=(double)(Math.round(tempMin*100)/100.0);
        double tempMax=jObj.getJSONObject("main").getDouble("temp_max")-TEMP_CONVERT;
        tempMax=(double)(Math.round(tempMax*100)/100.0);
        double pressure=jObj.getJSONObject("main").getDouble("pressure");
        int humid=jObj.getJSONObject("main").getInt("humidity");
        String city=jObj.getString("name");
        WeatherInfo wgeo = new WeatherInfo(city,tempMin,tempMax,mainWeather,pressure, humid);

        return wgeo;
    }



    public String createURL(String cityName){
        String endPoint = "https://api.openweathermap.org";
        String url2 = "/data/2.5/weather?q=";
        String key = "&appid=93878e3b8fc1c0224d9e17f353cf474f";
        String weatherUrl = endPoint + url2 + cityName + key;
        return  weatherUrl;
    }
    public WeatherInfo getCityWeather(String response) {

        CityAPI cityAPI = new CityAPI();
        WeatherInfo w=new WeatherInfo("",0.0,0.0,"",0.0,0);
        try {
            w= cityAPI.parseCityWeatherJSON(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return w;
    }
    public String makeAPICall(URL url) throws IOException {

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
}