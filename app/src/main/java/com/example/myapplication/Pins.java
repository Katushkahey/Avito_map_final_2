package com.example.myapplication;

import java.util.ArrayList;

class Coordinates {
    double lat;
    double lng;
}

class Pin {
    int id;
    String service;
    Coordinates coordinates;
}

public class Pins {
    ArrayList<String> services;
    ArrayList<Pin> pins;
}
