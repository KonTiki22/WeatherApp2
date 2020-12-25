package com.example.weatherapp;

import java.util.Map;

public class Weather {
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
