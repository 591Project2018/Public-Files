package com.ja.getdevicelocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class demos how to make an API call, parse the JSON response and uses the response
 * values to create an ArrayList of RecipePuppyRecipe objects.
 * @author yilinsun, GJY
 *
 */
public class WeatherAPI {

    private Exception exception;
    /**
     * Parse the JSON response String
     * @param jsonResponse
     * @return ArrayList of RecipePuppyRecipe objects
     * @throws JSONException
     */
    public WeatherInfo parseWeatherJSON(String jsonResponse) throws JSONException {
        //create a JSON object with the String response
        JSONObject jObj = new JSONObject(jsonResponse);
        //Look at the raw String response
        //Look for the results key
        //After the colon there is a square bracket indicating a JSONArray
        JSONArray jArray1 = jObj.getJSONArray("weather");
        String mainWeather=jArray1.getJSONObject(0).getString("main");
        double tempMin=jObj.getJSONObject("main").getDouble("temp_min")-273.15;
        tempMin=(double)(Math.round(tempMin*100)/100.0);
        double tempMax=jObj.getJSONObject("main").getDouble("temp_max")-273.15;
        tempMax=(double)(Math.round(tempMax*100)/100.0);
        double pressure=jObj.getJSONObject("main").getDouble("pressure");
        int humid=jObj.getJSONObject("main").getInt("humidity");
        String city=jObj.getString("name");
        WeatherInfo wgeo = new WeatherInfo(city,tempMin,tempMax,mainWeather,pressure, humid);

        return wgeo;
    }

    public String createURL(double latNum, double lonNum){
        String endPoint = "https://api.openweathermap.org";
        String url2 = "/data/2.5/weather?lat=";
        String lon = "&lon=";
        String key = "&appid=93878e3b8fc1c0224d9e17f353cf474f";
        String weatherUrl = endPoint + url2 + latNum + lon + lonNum + key;
        return  weatherUrl;
    }
    public WeatherInfo getWgeo(String response) {

        WeatherAPI weatherAPI = new WeatherAPI();
        WeatherInfo wgeo=new WeatherInfo("",0.0,0.0,"",0.0,0);
        try {
            wgeo = weatherAPI.parseWeatherJSON(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wgeo;
    }


}