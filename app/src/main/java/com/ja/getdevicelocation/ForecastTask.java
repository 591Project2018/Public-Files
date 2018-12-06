package com.ja.getdevicelocation;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



    public class ForecastTask extends AsyncTask<String, Void, String> {
            Context mainContent;
            Class activityClass;
            Intent intent;

        public ForecastTask(Context mainContent, Class activityClass){
            this.mainContent=mainContent;
            this.activityClass=activityClass;

        }

        @Override
        protected String doInBackground(String... geos) {
            ForecastAPI forecastAPI = new ForecastAPI();
            String url=forecastAPI.createURL(Double.parseDouble(geos[0]),Double.parseDouble(geos[1]));

            URL forecastURL;
            String response ="" ;
            try {

                forecastURL = new URL(url);


                 response = forecastAPI.makeAPICall(forecastURL);



            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String response) {

            ForecastAPI forecastAPI = new ForecastAPI();

            List<DailyWeather> res = new ArrayList<>();
            res=forecastAPI.getGeoForecast(response);

            Intent intent = new Intent(mainContent, activityClass);

            intent.putExtra("mainWeather2Image",res.get(0).getDiscription());
            double temp2=res.get(0).getTemMax()/2+res.get(1).getTemMax()/2;
            temp2=(double)(Math.round(temp2*100)/100.0);
            intent.putExtra("mainWeather2", res.get(0).getDiscription()+"\n\n "+temp2+" °C \n\n Humid: \n"+String.valueOf(res.get(0).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(0).getPressure()));





            // intent.putExtra("mainWeather6Image",res.get(34).getDiscription());
            //double temp6=res.get(34).getTemMax()/2+res.get(37).getTemMax()/2;
            //temp6=(double)(Math.round(temp6*100)/100.0);
            //intent.putExtra("mainWeather6", res.get(34).getDiscription()+"\n\n "+temp6+" °C \n\n Humid: \n"+String.valueOf(res.get(34).getHumid())+"\n\n Pressure: \n"+String.valueOf(res.get(34).getPressure()));

            //intent.putExtra("fore_temp",res.get())
            this.intent=intent;



        }
    }

