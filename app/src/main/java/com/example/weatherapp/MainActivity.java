package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    Weather weather;
    TextView city;
    TextView temp;
    TextView humidity;
    TextView sunrise;
    TextView sunset;
    TextView daylong;

    public class TokenCallback implements Callback<Weather> {

        @Override
        public void onResponse(Response<Weather> response, Retrofit retrofit) {
            if (response.isSuccess()) {
                weather = response.body();
                city.setText(weather.name + " " + weather.sys.country);
                temp.setText(String.format("%.0f", weather.main.get("temp")) + "°C");
                humidity.setText("Humidity: " + weather.main.get("humidity").toString() + "%");
                Date sunrise_time = new Date(weather.sys.sunrise*1000L);
                Date sunset_time = new Date(weather.sys.sunset*1000L);
                long s = weather.sys.sunset - weather.sys.sunrise;
                SimpleDateFormat dtformat = new SimpleDateFormat("HH:mm:ss");
                sunrise.setText("Sunrise time: " + dtformat.format(sunrise_time));
                sunset.setText("Sunset time: " + dtformat.format(sunset_time));
                daylong.setText("Daytime: " + String.format("%2d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));
                //Log.d("myTag1: good", response.body().toString());
                //Log.d("myTag2: good", retrofit.baseUrl().url().toString());
            }
            else {
                Log.d("myTag1: site_error", String.valueOf(response.code()));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("myTag1: internet_error", t.getLocalizedMessage());
        }
    }

    public void onTokenClick(MainActivity v){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(OpenWeatherMapAPI.url)
                .build();

        OpenWeatherMapAPI api = retrofit.create(OpenWeatherMapAPI.class);

        Call<Weather> call = api.getToken();
        call.enqueue(new TokenCallback());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onTokenClick(this);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        humidity = findViewById(R.id.humidity);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        daylong = findViewById(R.id.daylong);
    }
}