package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    Forecast forecast;
    TextView city;
    TextView temp;
    TextView humidity;
    TextView wind;
    ProgressBar progressBar;
    Map<String, Integer> windDirs;
    GraphView graph;

    public class TokenCallback implements Callback<Forecast> {

        @Override
        public void onResponse(Response<Forecast> response, Retrofit retrofit) {
            if (response.isSuccess()) {
                forecast = response.body();

                city.setText(forecast.city.name);
                float tvalue = forecast.list[0].main.get("temp");
                temp.setText(String.format("%.0f°", tvalue));
                humidity.setText(String.format("Humidity: %.0f%%", forecast.list[0].main.get("humidity")));
                progressBar.setProgress(Math.round(tvalue + 40));


                float deg = forecast.list[0].wind.get("deg");
                for(String dir: windDirs.keySet()) {
                    int ang = windDirs.get(dir);
                    if(deg < (ang + 22.5) % 360 && deg > (ang - 22.5) % 360) {
                        wind.setText("Wind direction: " + dir);
                    }
                }

                final SimpleDateFormat dtformat = new SimpleDateFormat("E HH:mm");
                DataPoint[] dpArray = new DataPoint[forecast.list.length];
                for(int i = 0; i < forecast.list.length; i++) {
                    Log.d("myTag", forecast.list[i].dt_txt);
                    dpArray[i] = new DataPoint(i, forecast.list[i].main.get("temp"));
                    //dpArray[i] = new DataPoint(new Date(forecast.list[i].dt_txt.replace('-', '/')), forecast.list[i].main.get("temp"));
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dpArray);
                series.setDrawBackground(true);
                graph.addSeries(series);
                /*
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if(isValueX) {
                            return dtformat.format(value);
                        }
                        return super.formatLabel(value, isValueX);
                    }
                });*/
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

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

        Call<Forecast> call = api.getToken();
        call.enqueue(new TokenCallback());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onTokenClick(this);

        windDirs = new HashMap<>();
        windDirs.put("N", 0);
        windDirs.put("NE", 45);
        windDirs.put("E", 90);
        windDirs.put("SE", 135);
        windDirs.put("S", 180);
        windDirs.put("SW", 225);
        windDirs.put("W", 270);
        windDirs.put("NW", 315);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        progressBar = findViewById(R.id.progressBar);
        graph = findViewById(R.id.graph);

    }
}