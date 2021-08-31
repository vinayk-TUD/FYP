package com.app.footprintcalculator.models;

public class HomeLocationModel {
    private double latitude;
    private double longitude;

    public HomeLocationModel() { }

    public HomeLocationModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
