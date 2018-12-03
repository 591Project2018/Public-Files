package com.ja.getdevicelocation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    TextView city;
    TextView mainWeather;
    TextView pressure;
    TextView temperature;
    TextView humid;
    TextView tempMax;
    TextView tempMin;
    //TextView temptomMax;
    //TextView mainWeather1;
    ImageView weatherImage;
    private FusedLocationProviderClient client;
    public static String url="";
    public static String textResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        final WeatherAPI weatherAPI=new WeatherAPI();
        //final ForecastAPI forecastAPI=new ForecastAPI();
        client = LocationServices.getFusedLocationProviderClient(this);
        Button button = findViewById(R.id.getLocation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location!=null){
                            mainWeather=findViewById(R.id.weather);
                            city=findViewById(R.id.location);
                            pressure=findViewById(R.id.pressure);
                            temperature=findViewById(R.id.temperature);
                            tempMax=findViewById(R.id.tempMax);
                            tempMin=findViewById(R.id.tempMin);
                            humid=findViewById(R.id.humid);
                            weatherImage=findViewById(R.id.WeatherImage);
                            //mainWeather1=findViewById(R.id.tomInfo);
                            //textView.setText(String.valueOf(location.getLatitude())+" +  "+String.valueOf(location.getLongitude()));
                            url=weatherAPI.createURL(location.getLatitude(),location.getLongitude());
                            //url1=forecastAPI.createURL(location.getLatitude(),location.getLongitude());
                            MyTask myTask=new MyTask();
                            myTask.execute();

                            System.out.print(location.getLatitude());
                        }
                    }
                });
            }
        });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    private class MyTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            URL textURL;
            try{
                textURL=new URL(url);
                BufferedReader in=new BufferedReader(new InputStreamReader(
                        textURL.openStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                textResult=response.toString();
                in.close();
            }catch (MalformedURLException e){
                textResult=e.toString();
                e.printStackTrace();
            }catch (IOException e){
                textResult=e.toString();
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            WeatherAPI weatherAPI=new WeatherAPI();
            Intent intent=new Intent(MainActivity.this, WeatherDisplay.class);
            intent.putExtra("mainWeather",weatherAPI.getWgeo(textResult).getMainWeather());
            intent.putExtra("city",weatherAPI.getWgeo(textResult).getCity());
            intent.putExtra("pressure","Pressure \n"+String.valueOf(weatherAPI.getWgeo(textResult).getPressure()));
            intent.putExtra("temperature",String.valueOf(weatherAPI.getWgeo(textResult).getTemMax()/2+weatherAPI.getWgeo(textResult).getTemMin()/2)+" °C");
            intent.putExtra("humid","Humid \n"+String.valueOf(weatherAPI.getWgeo(textResult).getHumid()));
            intent.putExtra("tempMax","Max\n"+String.valueOf(weatherAPI.getWgeo(textResult).getTemMax())+" °C");
            intent.putExtra("tempMin","Min\n"+String.valueOf(weatherAPI.getWgeo(textResult).getTemMin())+" °C");
            //ForecastAPI forecastAPI=new ForecastAPI();
           // intent.putExtra("mainWeather1",forecastAPI.getWgeo(textResult).get(1).getTemMax());
            //intent.putExtra("temptomMax",forcastAPI.getWgeo(textResult).get(1).getTemMax());
            // intent.putExtra("weatherImage",)
            startActivity(intent);
            //mainWeather.setText(weatherAPI.getWgeo(textResult).getMainWeather());
            //city.setText(weatherAPI.getWgeo(textResult).getCity());
            //pressure.setText(String.valueOf(weatherAPI.getWgeo(textResult).getPressure()));
            super.onPostExecute(aVoid);
        }




    }

}
