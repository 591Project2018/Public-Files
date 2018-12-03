package com.ja.getdevicelocation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    TextView city;
    TextView mainWeather;
    TextView pressure;
    TextView temperature;
    TextView humid;
    TextView tempMax;
    TextView tempMin;
    ImageView weatherImage;
    private FusedLocationProviderClient client;

    public static String textResult = "";
    public static String textResult2="";
    public static String cityURL = "";
    public static String forecastCityURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();

        final WeatherAPI weatherAPI = new WeatherAPI();
        final ForecastAPI forecastAPI = new ForecastAPI();
        client = LocationServices.getFusedLocationProviderClient(this);
        Button buttonSearch = findViewById(R.id.citySearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("MainActivity", "Search!");
                Intent intent = new Intent(MainActivity.this, SearchInfo.class);
                EditText editText = (EditText) findViewById(R.id.editText);
                String cityName = editText.getText().toString();
                Log.i("MainActivitycityName", cityName);
                CityAPI cityAPI = new CityAPI();
                ForecastCityAPI forecastCityAPI=new ForecastCityAPI();
                cityURL = cityAPI.createURL(cityName);
                forecastCityURL=forecastCityAPI.createURL(cityName);
                Log.i("cityURL", cityURL);
                CityTask cityTask = new CityTask();
                cityTask.execute();

            }
        });
        Button button = findViewById(R.id.getLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "Clicked!");

                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //System.out.print("permission failed!");
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mainWeather = findViewById(R.id.weather);
                            city = findViewById(R.id.location);
                            pressure = findViewById(R.id.pressure);
                            temperature = findViewById(R.id.temperature);
                            tempMax = findViewById(R.id.tempMax);
                            tempMin = findViewById(R.id.tempMin);
                            humid = findViewById(R.id.humid);
                            weatherImage = findViewById(R.id.WeatherImage);
                            //textView.setText(String.valueOf(location.getLatitude())+" +  "+String.valueOf(location.getLongitude()));

                            String forecastURL = forecastAPI.createURL(location.getLatitude(), location.getLongitude());

                            String locationURL = weatherAPI.createURL(location.getLatitude(), location.getLongitude());
                            String[] response;
                            GeoTask geoTask = new GeoTask();
                            geoTask.execute(forecastURL, locationURL);

                            //System.out.print(location.getLatitude());
                        }
                    }
                });
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    private class GeoTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... urls) {
            ForecastAPI forecastAPI = new ForecastAPI();
            WeatherAPI weatherAPI = new WeatherAPI();
            URL locationURL;
            URL forecastURL;
            String[] response = new String[2];
            try {
                locationURL = new URL(urls[0]);
                forecastURL = new URL(urls[1]);
                String locationResponse = weatherAPI.makeAPICall(locationURL);
                response[0] = locationResponse;
                String forecastResponse = forecastAPI.makeAPICall(forecastURL);
                response[1] = forecastResponse;
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String[] response) {
            WeatherAPI weatherAPI = new WeatherAPI();
            ForecastAPI forecastAPI = new ForecastAPI();
            List<DailyWeather> res = forecastAPI.getGeoForecast(response[1]);
            Intent intent = new Intent(MainActivity.this, WeatherDisplay.class);
            intent.putExtra("mainWeather2", res.get(1).getDiscription());
            intent.putExtra("temp2", String.valueOf(res.get(1).getTemMax()));
            intent.putExtra("mainWeather",weatherAPI.getWgeo(response[0]).getMainWeather());
            intent.putExtra("city",weatherAPI.getWgeo(response[0]).getCity());
            intent.putExtra("pressure","Pressure \n"+String.valueOf(weatherAPI.getWgeo(response[0]).getPressure()));
            intent.putExtra("temperature",String.valueOf(weatherAPI.getWgeo(response[0]).getTemMax()/2+weatherAPI.getWgeo(response[0]).getTemMin()/2)+" °C");
            intent.putExtra("humid","Humid \n"+String.valueOf(weatherAPI.getWgeo(response[0]).getHumid()));
            intent.putExtra("tempMax","Max\n"+String.valueOf(weatherAPI.getWgeo(response[0]).getTemMax())+" °C");
            intent.putExtra("tempMin","Min\n"+String.valueOf(weatherAPI.getWgeo(response[0]).getTemMin())+" °C");
            //intent.putExtra("fore_temp",res.get())

            startActivity(intent);


        }
    }

    private class CityTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


            URL textURL;
            URL text2URL;
            try {
                textURL = new URL(cityURL);
                text2URL=new URL(forecastCityURL);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        textURL.openStream()));
                BufferedReader in2 = new BufferedReader(new InputStreamReader(
                        text2URL.openStream()));
                String inputLine;
                String inputLine2;
                StringBuffer response = new StringBuffer();
                StringBuffer response2=new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                while ((inputLine2 = in2.readLine()) != null) {
                    response2.append(inputLine2);
                }
                textResult = response.toString();
                textResult2=response2.toString();
                in.close();
                in2.close();
            } catch (MalformedURLException e) {
                textResult = e.toString();
                textResult2=e.toString();
                e.printStackTrace();
            } catch (IOException e) {
                textResult = e.toString();
                textResult2 = e.toString();
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            CityAPI cityAPI = new CityAPI();
            ForecastCityAPI forecastCityAPI=new ForecastCityAPI();
            Intent intent = new Intent(MainActivity.this, SearchInfo.class);

            intent.putExtra("city_mainWeather", cityAPI.getCityWeather(textResult).getMainWeather());
            Log.i("MainActivityWeather = ", cityAPI.getCityWeather(textResult).getMainWeather());
            intent.putExtra("city_city", cityAPI.getCityWeather(textResult).getCity());
            Log.i("MainActivitycity = ", cityAPI.getCityWeather(textResult).getCity());
            intent.putExtra("city_pressure", String.valueOf(cityAPI.getCityWeather(textResult).getPressure()));
            Log.i("MainActivitypressure = ", String.valueOf(cityAPI.getCityWeather(textResult).getPressure()));
            intent.putExtra("city_temp", String.valueOf(cityAPI.getCityWeather(textResult).getTemMax()));
            Log.i("MainActivitytempMax = ", String.valueOf(cityAPI.getCityWeather(textResult).getTemMax()));
            startActivity(intent);

            intent.putExtra("city_mainWeather2",forecastCityAPI.getCityForecast(textResult2).get(1).getDiscription());
            Log.i("MainActivityWeather2 = ", forecastCityAPI.getCityForecast(textResult2).get(1).getDiscription());


           // intent.putExtra("city_temp2", String.valueOf(forecastCityAPI.getCityForecast(textResult2).get(1).getTemMax()));
            //Log.i("MainActivitytempMax2 = ", String.valueOf(cityAPI.getCityWeather(textResult).getTemMax()));
            super.onPostExecute(aVoid);
        }
    }


    public void onClick(View v) {


        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
    }
}