package com.ja.getdevicelocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SearchInfo extends AppCompatActivity {
    private TextView real_weather, real_pressure, location,real_temperature,real_humid,real_tempMax,real_tempMin, real_Weather2,real_Weather3,real_Weather4,real_Weather5,real_Weather6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_info);
        Intent intent = getIntent();
        String city=intent.getStringExtra("search_city");
        String pressure=intent.getStringExtra("search_pressure");
        String mainWeather=intent.getStringExtra("search_mainWeather");

        String mainWeather2=intent.getStringExtra("search_mainWeather2Image");
        String mainWeather2Info=intent.getStringExtra("search_mainWeather2");
        String mainWeather3=intent.getStringExtra("search_mainWeather3Image");
        String mainWeather3Info=intent.getStringExtra("search_mainWeather3");
        String mainWeather4=intent.getStringExtra("search_mainWeather4Image");
        String mainWeather4Info=intent.getStringExtra("search_mainWeather4");
        String mainWeather5=intent.getStringExtra("search_mainWeather5Image");
        String mainWeather5Info=intent.getStringExtra("search_mainWeather5");
       // String mainWeather6=intent.getStringExtra("search_mainWeather6Image");
        //String mainWeather6Info=intent.getStringExtra("search_mainWeather6");

        String temperature=intent.getStringExtra("search_temperature");
        String tempMax=intent.getStringExtra("search_tempMax");
        String tempMin=intent.getStringExtra("search_tempMin");
        String humid=intent.getStringExtra("search_humid");
        location=findViewById(R.id.search_location);
        real_weather=findViewById(R.id.search_weather);
        real_pressure=findViewById(R.id.search_pressure);
        real_temperature=findViewById(R.id.search_temp);
        real_tempMax=findViewById(R.id.search_tempMax);
        real_tempMin=findViewById(R.id.search_tempMin);
        real_humid=findViewById(R.id.search_humid);
        real_Weather2 =findViewById(R.id.search_tomInfo);
        real_Weather3 =findViewById(R.id.search_thirdInfo);
        real_Weather4 =findViewById(R.id.search_fourthInfo);
        real_Weather5 =findViewById(R.id.search_fifthInfo);
        //real_Weather6 =findViewById(R.id.search_sixthInfo);
        location.setText(city);
        real_weather.setText(mainWeather);
        real_pressure.setText(pressure);
        real_temperature.setText(temperature);
        real_humid.setText(humid);
        real_tempMax.setText(tempMax);
        real_tempMin.setText(tempMin);

        real_Weather2.setText(mainWeather2Info);
        real_Weather3.setText(mainWeather3Info);
        real_Weather4.setText(mainWeather4Info);
        real_Weather5.setText(mainWeather5Info);
       // real_Weather6.setText(mainWeather6Info);



        ImageView view=findViewById(R.id.search_WeatherImage);

        if(mainWeather.contains("Rain")) {
            view.setImageResource(R.drawable.rainy);
        }else if (mainWeather.contains("rain")) {
            view.setImageResource(R.drawable.rainy);
        }else if (mainWeather.contains("snow")) {
            view.setImageResource(R.drawable.snow);
        }else if (mainWeather.contains("Snow")) {
            view.setImageResource(R.drawable.snow);
        } else if (mainWeather.contains("Cloud")) {
            view.setImageResource(R.drawable.cloudy);
        } else if (mainWeather.contains("cloud")) {
            view.setImageResource(R.drawable.cloudy);
        } else if (mainWeather.contains("Clear")) {
            view.setImageResource(R.drawable.sunny);
        } else if (mainWeather.contains("clear")) {
            view.setImageResource(R.drawable.sunny);
        } else if (mainWeather.contains("sun")) {
            view.setImageResource(R.drawable.sunny);
        }else if (mainWeather.contains("fog")) {
            view.setImageResource(R.drawable.fog);
        }else if (mainWeather.contains("haze")) {
            view.setImageResource(R.drawable.fog);
        }else if (mainWeather.contains("Fog")) {
            view.setImageResource(R.drawable.fog);
        }else if (mainWeather.contains("Haze")) {
            view.setImageResource(R.drawable.fog);
        }else{
            view.setImageResource(R.drawable.sunny);
        }

        ImageView view1=findViewById(R.id.search_tommImage);

        if(mainWeather2.contains("Rain")) {
            view1.setImageResource(R.drawable.rainy);
        }else if (mainWeather2.contains("rain")) {
            view1.setImageResource(R.drawable.rainy);
        }else if (mainWeather2.contains("snow")) {
            view1.setImageResource(R.drawable.snow);
        }else if (mainWeather2.contains("Snow")) {
            view1.setImageResource(R.drawable.snow);
        } else if (mainWeather2.contains("Cloud")) {
            view1.setImageResource(R.drawable.cloudy);
        } else if (mainWeather2.contains("cloud")) {
            view1.setImageResource(R.drawable.cloudy);
        } else if (mainWeather2.contains("Clear")) {
            view1.setImageResource(R.drawable.sunny);
        } else if (mainWeather2.contains("clear")) {
            view1.setImageResource(R.drawable.sunny);
        } else if (mainWeather2.contains("sun")) {
            view1.setImageResource(R.drawable.sunny);
        }else if (mainWeather2.contains("fog")) {
            view1.setImageResource(R.drawable.fog);
        }else if (mainWeather2.contains("haze")) {
            view1.setImageResource(R.drawable.fog);
        }else if (mainWeather2.contains("Fog")) {
            view1.setImageResource(R.drawable.fog);
        }else if (mainWeather2.contains("Haze")) {
            view1.setImageResource(R.drawable.fog);
        }else{
            view1.setImageResource(R.drawable.sunny);
        }

        ImageView view2=findViewById(R.id.search_thirdImage);

        if(mainWeather3.contains("Rain")) {
            view2.setImageResource(R.drawable.rainy);
        }else if (mainWeather3.contains("rain")) {
            view2.setImageResource(R.drawable.rainy);
        }else if (mainWeather3.contains("snow")) {
            view2.setImageResource(R.drawable.snow);
        }else if (mainWeather3.contains("Snow")) {
            view2.setImageResource(R.drawable.snow);
        } else if (mainWeather3.contains("Cloud")) {
            view2.setImageResource(R.drawable.cloudy);
        } else if (mainWeather3.contains("cloud")) {
            view2.setImageResource(R.drawable.cloudy);
        } else if (mainWeather3.contains("Clear")) {
            view2.setImageResource(R.drawable.sunny);
        } else if (mainWeather3.contains("clear")) {
            view2.setImageResource(R.drawable.sunny);
        } else if (mainWeather3.contains("sun")) {
            view2.setImageResource(R.drawable.sunny);
        }else if (mainWeather3.contains("fog")) {
            view2.setImageResource(R.drawable.fog);
        }else if (mainWeather3.contains("haze")) {
            view2.setImageResource(R.drawable.fog);
        }else if (mainWeather3.contains("Fog")) {
            view2.setImageResource(R.drawable.fog);
        }else if (mainWeather3.contains("Haze")) {
            view2.setImageResource(R.drawable.fog);
        }else{
            view2.setImageResource(R.drawable.sunny);
        }

        ImageView view3=findViewById(R.id.search_fourthImage);

        if(mainWeather4.contains("Rain")) {
            view3.setImageResource(R.drawable.rainy);
        }else if (mainWeather4.contains("rain")) {
            view3.setImageResource(R.drawable.rainy);
        }else if (mainWeather4.contains("snow")) {
            view3.setImageResource(R.drawable.snow);
        }else if (mainWeather4.contains("Snow")) {
            view3.setImageResource(R.drawable.snow);
        } else if (mainWeather4.contains("Cloud")) {
            view3.setImageResource(R.drawable.cloudy);
        } else if (mainWeather4.contains("cloud")) {
            view3.setImageResource(R.drawable.cloudy);
        } else if (mainWeather4.contains("Clear")) {
            view3.setImageResource(R.drawable.sunny);
        } else if (mainWeather4.contains("clear")) {
            view3.setImageResource(R.drawable.sunny);
        } else if (mainWeather4.contains("sun")) {
            view3.setImageResource(R.drawable.sunny);
        }else if (mainWeather4.contains("fog")) {
            view3.setImageResource(R.drawable.fog);
        }else if (mainWeather4.contains("haze")) {
            view3.setImageResource(R.drawable.fog);
        }else if (mainWeather4.contains("Fog")) {
            view3.setImageResource(R.drawable.fog);
        }else if (mainWeather4.contains("Haze")) {
            view3.setImageResource(R.drawable.fog);
        }else{
            view3.setImageResource(R.drawable.sunny);
        }

        ImageView view4=findViewById(R.id.search_fifthImage);

        if(mainWeather5.contains("Rain")) {
            view4.setImageResource(R.drawable.rainy);
        }else if (mainWeather5.contains("rain")) {
            view4.setImageResource(R.drawable.rainy);
        }else if (mainWeather5.contains("snow")) {
            view4.setImageResource(R.drawable.snow);
        }else if (mainWeather5.contains("Snow")) {
            view4.setImageResource(R.drawable.snow);
        } else if (mainWeather5.contains("Cloud")) {
            view4.setImageResource(R.drawable.cloudy);
        } else if (mainWeather5.contains("cloud")) {
            view4.setImageResource(R.drawable.cloudy);
        } else if (mainWeather5.contains("Clear")) {
            view4.setImageResource(R.drawable.sunny);
        } else if (mainWeather5.contains("clear")) {
            view4.setImageResource(R.drawable.sunny);
        } else if (mainWeather5.contains("sun")) {
            view4.setImageResource(R.drawable.sunny);
        }else if (mainWeather5.contains("fog")) {
            view4.setImageResource(R.drawable.fog);
        }else if (mainWeather5.contains("haze")) {
            view4.setImageResource(R.drawable.fog);
        }else if (mainWeather5.contains("Fog")) {
            view4.setImageResource(R.drawable.fog);
        }else if (mainWeather5.contains("Haze")) {
            view4.setImageResource(R.drawable.fog);
        }else{
            view4.setImageResource(R.drawable.sunny);
        }


        Date now=new Date();

        Calendar calendar=new GregorianCalendar();
        calendar.add(calendar.DATE,1);
        Date tomorrow1=calendar.getTime();
        String nowAsString=new SimpleDateFormat("yyyy-MM-dd" ).format(now);
        TextView day=findViewById(R.id.search_Day);
        day.setText(nowAsString);
        String tomAsString=new SimpleDateFormat("MM-dd").format(tomorrow1);
        TextView tomorrow=findViewById(R.id.search_tomorrow);
        tomorrow.setText(tomAsString);

        calendar.add(calendar.DATE,1);
        Date tom2=calendar.getTime();
        String thirdAsString=new SimpleDateFormat("MM-dd").format(tom2);
        TextView third=findViewById(R.id.search_third);
        third.setText(thirdAsString);

        calendar.add(calendar.DATE,1);
        Date tom3=calendar.getTime();
        String fourthAsString=new SimpleDateFormat("MM-dd").format(tom3);
        TextView fourth=findViewById(R.id.search_fourth);
        fourth.setText(fourthAsString);

        calendar.add(calendar.DATE,1);
        Date tom4=calendar.getTime();
        String fifthAsString=new SimpleDateFormat("MM-dd").format(tom4);
        TextView fifth=findViewById(R.id.search_fifth);
        fifth.setText(fifthAsString);



        location.setOnTouchListener(new OnSwipeTouchListener(SearchInfo.this) {
            public void onSwipeTop() {
                Toast.makeText(SearchInfo.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Intent intent1=new Intent(SearchInfo.this,TestSwipe.class);
                startActivity(intent1);
            }
            public void onSwipeLeft() {
                Toast.makeText(SearchInfo.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(SearchInfo.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

    }

}