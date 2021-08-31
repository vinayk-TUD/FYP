package com.app.footprintcalculator.models;

public class FoodModel {
    private String id;
    private double redMeatEmission;
    private double whiteMeatEmission;
    private double dairyEmission;
    private double cerealsEmission;
    private double vegetablesEmission;
    private double fruitsEmission;
    private double oilsEmission;
    private double snacksEmission;
    private double drinksEmission;
    private double totalEmission;

    public FoodModel() { }

    public FoodModel(String id, double redMeatEmission, double whiteMeatEmission, double dairyEmission, double cerealsEmission,
                     double vegetablesEmission, double fruitsEmission, double oilsEmission, double snacksEmission, double drinksEmission, double totalEmission) {
        this.id = id;
        this.redMeatEmission = redMeatEmission;
        this.whiteMeatEmission = whiteMeatEmission;
        this.dairyEmission = dairyEmission;
        this.cerealsEmission = cerealsEmission;
        this.vegetablesEmission = vegetablesEmission;
        this.fruitsEmission = fruitsEmission;
        this.oilsEmission = oilsEmission;
        this.snacksEmission = snacksEmission;
        this.drinksEmission = drinksEmission;
        this.totalEmission = totalEmission;
    }

    public String getId() {
        return id;
    }

    public double getRedMeatEmission() {
        return redMeatEmission;
    }

    public double getWhiteMeatEmission() {
        return whiteMeatEmission;
    }

    public double getDairyEmission() {
        return dairyEmission;
    }

    public double getCerealsEmission() {
        return cerealsEmission;
    }

    public double getVegetablesEmission() {
        return vegetablesEmission;
    }

    public double getFruitsEmission() {
        return fruitsEmission;
    }

    public double getOilsEmission() {
        return oilsEmission;
    }

    public double getSnacksEmission() {
        return snacksEmission;
    }

    public double getDrinksEmission() {
        return drinksEmission;
    }

    public double getTotalEmission() {
        return totalEmission;
    }
}
