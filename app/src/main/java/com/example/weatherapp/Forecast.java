package com.example.weatherapp;

import java.util.Map;

public class Forecast {
    Integer cod;
    Integer message;
    Integer cnt;
    Weather[] list;
    City city;

    class City {
        Integer id;
        String name;
        Map<String, Float> coords;
        String country;
        Integer timezone;
        Integer sunrise;
        Integer sunset;
    }

    class Weather {
        Integer dt;
        Map<String, Float> main;
        Map<String, String>[] weather;
        Map<String, Float> clouds;
        Map<String, Float> wind;
        String visibility;
        Float pop;
        Map<String, Float> rain;
        Map<String, String> sys;
        String dt_txt;
    }
}
