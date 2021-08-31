package com.app.footprintcalculator.models;

public class HomeModel {
    private String id;
    private double elcetricityEmission;
    private double gasEmission;
    private double oilEmission;
    private double wasteEmission;
    private double waterEmission;
    private double totalEmission;

    public HomeModel() { }

    public HomeModel(String id, double elcetricityEmission, double gasEmission, double oilEmission, double wasteEmission, double waterEmission, double totalEmission) {
        this.id = id;
        this.elcetricityEmission = elcetricityEmission;
        this.gasEmission = gasEmission;
        this.oilEmission = oilEmission;
        this.wasteEmission = wasteEmission;
        this.waterEmission = waterEmission;
        this.totalEmission = totalEmission;
    }

    public String getId() {
        return id;
    }

    public double getElcetricityEmission() {
        return elcetricityEmission;
    }

    public double getGasEmission() {
        return gasEmission;
    }

    public double getOilEmission() {
        return oilEmission;
    }

    public double getWasteEmission() {
        return wasteEmission;
    }

    public double getWaterEmission() {
        return waterEmission;
    }

    public double getTotalEmission() {
        return totalEmission;
    }
}
