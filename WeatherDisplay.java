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

public class WeatherDisplay extends AppCompatActivity {
    private TextView real_weather, real_pressure, location,real_temperature,real_humid,real_tempMax,real_tempMin,real_weather1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);
        Intent intent=getIntent();
        String city=intent.getStringExtra("city");
        String pressure=intent.getStringExtra("pressure");
        String mainWeather=intent.getStringExtra("mainWeather");
       // String mainWeather1=intent.getStringExtra("mainWeather1");

        ImageView view=findViewById(R.id.WeatherImage);

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

       /* ImageView view1=findViewById(R.id.tommImage);

        if(mainWeather1.contains("Rain")) {
            view1.setImageResource(R.drawable.rainy);
        }else if (mainWeather1.contains("rain")) {
            view1.setImageResource(R.drawable.rainy);
        }else if (mainWeather1.contains("snow")) {
            view1.setImageResource(R.drawable.snow);
        }else if (mainWeather1.contains("Snow")) {
            view1.setImageResource(R.drawable.snow);
        } else if (mainWeather1.contains("Cloud")) {
            view1.setImageResource(R.drawable.cloudy);
        } else if (mainWeather1.contains("cloud")) {
            view1.setImageResource(R.drawable.cloudy);
        } else if (mainWeather1.contains("Clear")) {
            view1.setImageResource(R.drawable.sunny);
        } else if (mainWeather1.contains("clear")) {
            view1.setImageResource(R.drawable.sunny);
        } else if (mainWeather1.contains("sun")) {
            view1.setImageResource(R.drawable.sunny);
        }else if (mainWeather1.contains("fog")) {
            view1.setImageResource(R.drawable.fog);
        }else if (mainWeather1.contains("haze")) {
            view1.setImageResource(R.drawable.fog);
        }else if (mainWeather1.contains("Fog")) {
            view1.setImageResource(R.drawable.fog);
        }else if (mainWeather1.contains("Haze")) {
            view1.setImageResource(R.drawable.fog);
        }else{
            view1.setImageResource(R.drawable.sunny);
        }
        */
        Date now=new Date();

        Calendar calendar=new GregorianCalendar();
        calendar.add(calendar.DATE,1);
        Date tomorrow1=calendar.getTime();
        String nowAsString=new SimpleDateFormat("yyyy-MM-dd" ).format(now);
        TextView day=findViewById(R.id.Day);
        day.setText(nowAsString);
        String tomAsString=new SimpleDateFormat("MM-dd").format(tomorrow1);
        TextView tomorrow=findViewById(R.id.tomorrow);
        tomorrow.setText(tomAsString);

        calendar.add(calendar.DATE,1);
        Date tom2=calendar.getTime();
        String thirdAsString=new SimpleDateFormat("MM-dd").format(tom2);
        TextView third=findViewById(R.id.third);
        third.setText(thirdAsString);

        calendar.add(calendar.DATE,1);
        Date tom3=calendar.getTime();
        String fourthAsString=new SimpleDateFormat("MM-dd").format(tom3);
        TextView fourth=findViewById(R.id.fourth);
        fourth.setText(fourthAsString);

        calendar.add(calendar.DATE,1);
        Date tom4=calendar.getTime();
        String fifthAsString=new SimpleDateFormat("MM-dd").format(tom4);
        TextView fifth=findViewById(R.id.fifth);
        fifth.setText(fifthAsString);

        calendar.add(calendar.DATE,1);
        Date tom5=calendar.getTime();
        String sixthAsString=new SimpleDateFormat("MM-dd").format(tom5);
        TextView sixth=findViewById(R.id.sixth);
        sixth.setText(sixthAsString);

        String temperature=intent.getStringExtra("temperature");
        String tempMax=intent.getStringExtra("tempMax");
        String tempMin=intent.getStringExtra("tempMin");
        String humid=intent.getStringExtra("humid");
        location=findViewById(R.id.location);
        real_weather=findViewById(R.id.weather);
        real_pressure=findViewById(R.id.pressure);
        real_temperature=findViewById(R.id.temperature);
        real_tempMax=findViewById(R.id.tempMax);
        real_tempMin=findViewById(R.id.tempMin);
        real_humid=findViewById(R.id.humid);
        //real_weather1=findViewById(R.id.tomInfo);
        location.setText(city);
        real_weather.setText(mainWeather);
        real_pressure.setText(pressure);
        real_temperature.setText(temperature);
        real_humid.setText(humid);
        real_tempMax.setText(tempMax);
        real_tempMin.setText(tempMin);

        //real_weather1.setText(mainWeather1);

        location.setOnTouchListener(new OnSwipeTouchListener(WeatherDisplay.this) {
            public void onSwipeTop() {
                Toast.makeText(WeatherDisplay.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Intent intent1=new Intent(WeatherDisplay.this,TestSwipe.class);
                startActivity(intent1);
            }
            public void onSwipeLeft() {
                Toast.makeText(WeatherDisplay.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(WeatherDisplay.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


    }
}
