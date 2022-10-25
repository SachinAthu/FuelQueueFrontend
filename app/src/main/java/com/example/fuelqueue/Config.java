package com.example.fuelqueue;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {

    // user types
    public static final HashMap<String, String> USER_TYPES = new HashMap<String, String>() {
        {
            put("User", "user");
            put("Station Owner", "station_owner");
        }
    };

    // station types
    public static final HashMap<String, String> STATION_TYPES = new HashMap<String, String>() {
        {
            put("Ceypetco", "ceypetco");
            put("IOC", "ioc");
        }
    };

    public static final ArrayList<String> VEHICLE_TYPES = new ArrayList<String>() {
        {
            add("A1 - Light Motor Cycle");
            add("A  - Motor Cycle");
            add("B1 - Motor Tricycle");
            add("B  - Motor Vehicle");
            add("C1 - Light Motor Lorry");
            add("C  - Motor Lorry");
            add("CE - Heavy Motor Lorry");
            add("D1 - Light Motor Coach");
            add("D  - Motor Coach");
            add("DE - Heavy Motor Coach");
            add("G1 - Hand Tractors");
            add("G -  Land Vehicle");
            add("J -  Special purpose Vehicle,");
        }
    };

    public static final ArrayList<String> FUEL_TYPES = new ArrayList<String>() {
        {

        }
    };

    public static final String API_URL = "https://fuelqueuebackend20221025095530.azurewebsites.net/api/";
    // public static final String API_URL = "https://192.168.1.7:5000/api/";
    // public static final String API_URL = "https://6063-45-121-91-152.in.ngrok.io/api/";
    // public static final String API_URL = "https://10.0.2.2:5000/api/";

}
