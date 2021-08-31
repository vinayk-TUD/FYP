package com.app.footprintcalculator.models;

public class CountriesModel {
    private String name;
    private double value;

    public CountriesModel() { }

    public CountriesModel(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
