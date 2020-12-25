package com.example.weatherapp;


import com.google.gson.Gson;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface OpenWeatherMapAPI {
    String url = "http://api.openweathermap.org";
    String key ="81075f27a891b5b5f28b4dd74aad6fc4";
    @GET("/data/2.5/forecast?q=Irkutsk&units=metric&appid=81075f27a891b5b5f28b4dd74aad6fc4")
    //@GET("/")
    Call<Forecast> getToken();
}
