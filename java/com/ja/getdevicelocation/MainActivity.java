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
    ImageView weatherImage2;
    ImageView weatherImage3;
    ImageView weatherImage4;
    ImageView weatherImage5;
    //ImageView weatherImage6;
    TextView mainWeather2;
    TextView mainWeather3;
    TextView mainWeather4;
    TextView mainWeather5;
    //TextView mainWeather6;

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
        buttonSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.i("MainActivity","Search!");
                Intent intent=new Intent(MainActivity.this, SearchInfo.class);
                EditText editText = (EditText) findViewById(R.id.editText);
                String cityName = editText.getText().toString();
                Log.i("MainActivitycityName",cityName);
                CityAPI cityAPI=new CityAPI();
                String cityURL=cityAPI.createURL(cityName);
                Log.i("stringcityURL",cityURL);
                ForecastCityAPI forecastCityAPI=new ForecastCityAPI();
                String forecastCityURL=forecastCityAPI.createURL(cityName);
                Log.i("forcastcityURL",forecastCityURL);
                CityTask cityTask=new CityTask();
                cityTask.execute(cityURL, forecastCityURL);

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
                            mainWeather2=findViewById(R.id.tomInfo);
                            mainWeather3=findViewById(R.id.thirdInfo);
                            mainWeather4=findViewById(R.id.fourthInfo);
                            mainWeather5=findViewById(R.id.fifthInfo);
                            //mainWeather6=findViewById(R.id.sixthInfo);
                            weatherImage = findViewById(R.id.WeatherImage);
                            weatherImage2=findViewById(R.id.tommImage);
                            weatherImage3=findViewById(R.id.thirdImage);
                            weatherImage4=findViewById(R.id.fourthImage);
                            weatherImage5=findViewById(R.id.fifthImage);
                            //weatherImage6=findViewById(R.id.sixthImage);
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
                locationURL = new URL(urls[1]);
                forecastURL = new URL(urls[0]);

                String locationResponse = weatherAPI.makeAPICall(locationURL);
                response[0] = locationResponse;
                Log.i("Mainactivity", response[0]);
                String forecastResponse = forecastAPI.makeAPICall(forecastURL);
                response[1] = forecastResponse;
                Log.i("MainactivityforecastURL", response[1]);

            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String[] response) {
            WeatherAPI weatherAPI = new WeatherAPI();
           ForecastAPI forecastAPI = new ForecastAPI();

            List<DailyWeather> res = new ArrayList<>();
            res=forecastAPI.getGeoForecast(response[1]);

            Intent intent = new Intent(MainActivity.this, WeatherDisplay.class);

            intent.putExtra("mainWeather",weatherAPI.getWgeo(response[0]).getMainWeather());
            intent.putExtra("city",weatherAPI.getWgeo(response[0]).getCity());

            intent.putExtra("pressure","Pressure \n"+String.valueOf(weatherAPI.getWgeo(response[0]).getPressure()));
            intent.putExtra("temperature",String.valueOf(weatherAPI.getWgeo(response[0]).getTemMax()/2+weatherAPI.getWgeo(response[0]).getTemMin()/2)+" °C");
            intent.putExtra("humid","Humid \n"+String.valueOf(weatherAPI.getWgeo(response[0]).getHumid()));
            intent.putExtra("tempMax","Max\n"+String.valueOf(weatherAPI.getWgeo(response[0]).getTemMax())+" °C");
            intent.putExtra("tempMin","Min\n"+String.valueOf(weatherAPI.getWgeo(response[0]).getTemMin())+" °C");

            intent.putExtra("mainWeather2Image",res.get(0).getDiscription());
            double temp2=res.get(0).getTemMax()/2+res.get(1).getTemMax()/2;
            temp2=(double)(Math.round(temp2*100)/100.0);
            intent.putExtra("mainWeather2", res.get(0).getDiscription()+"\n\n "+temp2+" °C \n\n Humid: \n"+String.valueOf(res.get(0).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(0).getPressure()));

            intent.putExtra("mainWeather3Image",res.get(2).getDiscription());
            double temp3=res.get(2).getTemMax()/2+res.get(3).getTemMax()/2;
            temp3=(double)(Math.round(temp3*100)/100.0);
            intent.putExtra("mainWeather3", res.get(2).getDiscription()+"\n\n "+temp3+" °C \n\n Humid: \n"+String.valueOf(res.get(2).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(2).getPressure()));

            intent.putExtra("mainWeather4Image",res.get(4).getDiscription());
            double temp4=res.get(4).getTemMax()/2+res.get(5).getTemMax()/2;
            temp4=(double)(Math.round(temp4*100)/100.0);
            intent.putExtra("mainWeather4", res.get(4).getDiscription()+"\n\n "+temp4+" °C \n\n Humid: \n"+String.valueOf(res.get(4).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(4).getPressure()));

            intent.putExtra("mainWeather5Image",res.get(6).getDiscription());
            double temp5=res.get(6).getTemMax()/2+res.get(7).getTemMax()/2;
            temp5=(double)(Math.round(temp5*100)/100.0);
            intent.putExtra("mainWeather5", res.get(6).getDiscription()+"\n\n "+temp5+" °C \n\n Humid: \n"+String.valueOf(res.get(6).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(6).getPressure()));

           // intent.putExtra("mainWeather6Image",res.get(34).getDiscription());
            //double temp6=res.get(34).getTemMax()/2+res.get(37).getTemMax()/2;
            //temp6=(double)(Math.round(temp6*100)/100.0);
            //intent.putExtra("mainWeather6", res.get(34).getDiscription()+"\n\n "+temp6+" °C \n\n Humid: \n"+String.valueOf(res.get(34).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(34).getPressure()));

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
            Intent intent=new Intent(MainActivity.this, SearchInfo.class);


            intent.putExtra("search_mainWeather",cityAPI.getCityWeather(response[0]).getMainWeather());
            intent.putExtra("search_city",cityAPI.getCityWeather(response[0]).getCity());

            intent.putExtra("search_pressure","Pressure \n"+String.valueOf(cityAPI.getCityWeather(response[0]).getPressure()));
            intent.putExtra("search_temperature",String.valueOf(cityAPI.getCityWeather(response[0]).getTemMax()/2+cityAPI.getCityWeather(response[0]).getTemMin()/2)+" °C");
            intent.putExtra("search_humid","Humid \n"+String.valueOf(cityAPI.getCityWeather(response[0]).getHumid()));
            intent.putExtra("search_tempMax","Max\n"+String.valueOf(cityAPI.getCityWeather(response[0]).getTemMax())+" °C");
            intent.putExtra("search_tempMin","Min\n"+String.valueOf(cityAPI.getCityWeather(response[0]).getTemMin())+" °C");

            intent.putExtra("search_mainWeather2Image",res.get(0).getDiscription());
            double temp2=res.get(0).getTemMax()/2+res.get(1).getTemMax()/2;
            temp2=(double)(Math.round(temp2*100)/100.0);
            intent.putExtra("search_mainWeather2", res.get(0).getDiscription()+"\n\n "+temp2+" °C \n\n Humid: \n"+String.valueOf(res.get(0).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(0).getPressure()));

            intent.putExtra("search_mainWeather3Image",res.get(2).getDiscription());
            double temp3=res.get(2).getTemMax()/2+res.get(3).getTemMax()/2;
            temp3=(double)(Math.round(temp3*100)/100.0);
            intent.putExtra("search_mainWeather3", res.get(2).getDiscription()+"\n\n "+temp3+" °C \n\n Humid: \n"+String.valueOf(res.get(2).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(2).getPressure()));

            intent.putExtra("search_mainWeather4Image",res.get(4).getDiscription());
            double temp4=res.get(4).getTemMax()/2+res.get(5).getTemMax()/2;
            temp4=(double)(Math.round(temp4*100)/100.0);
            intent.putExtra("search_mainWeather4", res.get(4).getDiscription()+"\n\n "+temp4+" °C \n\n Humid: \n"+String.valueOf(res.get(4).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(4).getPressure()));

            intent.putExtra("search_mainWeather5Image",res.get(6).getDiscription());
            double temp5=res.get(6).getTemMax()/2+res.get(7).getTemMax()/2;
            temp5=(double)(Math.round(temp5*100)/100.0);
            intent.putExtra("search_mainWeather5", res.get(6).getDiscription()+"\n\n "+temp5+" °C \n\n Humid: \n"+String.valueOf(res.get(6).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(6).getPressure()));



            startActivity(intent);

        }
    }



    public void onClick(View v) {


        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
    }
}