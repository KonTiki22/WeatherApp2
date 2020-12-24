package com.example.weatherapp;

import java.util.Map;

public class Weather {
    Map<String, Float> coords;
    Map<String, String>[] weather;
    String base;
    Map<String, Float> main;
    String visibility;
    Map<String, Float> wind;
    Map<String, Float> clouds;
    Integer dt;
    Sys sys;
    Integer timezone;
    Integer id;
    String name;
    Integer cod;

    @Override
    public String toString() {
        String res = "";
        for(String i: main.keySet()) {
            res += String.format("%s: %f\n", i, main.get(i));
        }
        return res;
    }

    class Sys {
        Integer type;
        Integer id;
        Float message;
        String country;
        Integer sunrise;
        Integer sunset;
    }
}
