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
    TextView temp;
    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();

        final WeatherAPI weatherAPI=new WeatherAPI();
        final ForecastAPI forecastAPI=new ForecastAPI();
        client = LocationServices.getFusedLocationProviderClient(this);
        Button buttonSearch=findViewById(R.id.citySearch);
        buttonSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.i("MainActivity","Search!");
                Intent intent=new Intent(MainActivity.this, SearchInfo.class);
                EditText editText = (EditText) findViewById(R.id.editText);
                String cityName = editText.getText().toString();
                Log.i("MainActivitycityName",cityName);
                CityAPI cityAPI=new CityAPI();
                ForecastCityAPI forecastCityAPI=new ForecastCityAPI();
                String cityURL=cityAPI.createURL(cityName);
                String forecastCityURL=forecastCityAPI.createURL(cityName);
                Log.i("cityURL",cityURL);
                CityTask cityTask=new CityTask();
                cityTask.execute(cityURL, forecastCityURL);

            }
        });
        Button button = findViewById(R.id.getLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","Clicked!");

                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED) {
                    //System.out.print("permission failed!");
                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location!=null){
                            mainWeather=findViewById(R.id.weather);
                            city=findViewById(R.id.location);
                            pressure=findViewById(R.id.pressure);
                            temp=findViewById(R.id.temp);
                            //textView.setText(String.valueOf(location.getLatitude())+" +  "+String.valueOf(location.getLongitude()));

                            String forecastURL=forecastAPI.createURL(location.getLatitude(),location.getLongitude());

                            String locationURL=weatherAPI.createURL(location.getLatitude(),location.getLongitude());
                            String[] response;
                            GeoTask geoTask=new GeoTask();
                            geoTask.execute(locationURL,forecastURL);

                            //System.out.print(location.getLatitude());
                        }
                    }
                });
            }
        });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    private class GeoTask extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... urls) {
            ForecastAPI forecastAPI=new ForecastAPI();
            WeatherAPI weatherAPI=new WeatherAPI();
            URL locationURL;
            URL forecastURL;
            String[] response=new String[2];
            try{
                locationURL=new URL(urls[0]);
                forecastURL=new URL(urls[1]);
                String locationResponse=weatherAPI.makeAPICall(locationURL);
                response[0]=locationResponse;
                String forecastResponse=forecastAPI.makeAPICall(forecastURL);
                response[1]=forecastResponse;
            }catch (MalformedURLException e){

                e.printStackTrace();
            }catch (IOException e){

                e.printStackTrace();
            }

            return response;
        }



        @Override
        protected void onPostExecute( String[] response) {
            WeatherAPI weatherAPI=new WeatherAPI();
            ForecastAPI forecastAPI=new ForecastAPI();
            List<DailyWeather> res=forecastAPI.getGeoForecast(response[1]);
            Intent intent=new Intent(MainActivity.this, WeatherDisplay.class);
            intent.putExtra("mainWeather",weatherAPI.getWgeo(response[0]).getMainWeather());
            intent.putExtra("city",weatherAPI.getWgeo(response[0]).getCity());
            intent.putExtra("pressure",String.valueOf(weatherAPI.getWgeo(response[0]).getPressure()));
            intent.putExtra("temp",String.valueOf(weatherAPI.getWgeo(response[0]).getTemMax()));
            //intent.putExtra("fore_temp",res.get())

            startActivity(intent);


        }
    }

    private class CityTask extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... urls) {
            ForecastCityAPI forecastCityAPI=new ForecastCityAPI();
            CityAPI cityAPI=new CityAPI();
            URL cityURL;
            URL forecastCityURL;
            String[] response=new String[2];
            try{

                cityURL=new URL(urls[0]);
                forecastCityURL=new URL(urls[1]);
                String cityResponse=cityAPI.makeAPICall(cityURL);
                response[0]=cityResponse;
                String forecastCityResponse=forecastCityAPI.makeAPICall(forecastCityURL);
                response[1]=forecastCityResponse;
            }catch (MalformedURLException e){

                e.printStackTrace();
            }catch (IOException e){

                e.printStackTrace();
            }

            return response;
        }



        @Override
        protected void onPostExecute(String[] response) {
           CityAPI cityAPI=new CityAPI();
            ForecastCityAPI forecastAPI=new ForecastCityAPI();
            List<DailyWeather> res=forecastAPI.getCityForecast(response[1]);
            Intent intent=new Intent(MainActivity.this, WeatherDisplay.class);
            intent.putExtra("mainWeather",cityAPI.getCityWeather(response[0]).getMainWeather());
            intent.putExtra("city",cityAPI.getCityWeather(response[0]).getCity());
            intent.putExtra("pressure",String.valueOf(cityAPI.getCityWeather(response[0]).getPressure()));
            intent.putExtra("temp",String.valueOf(cityAPI.getCityWeather(response[0]).getTemMax()));
        }
    }


}
