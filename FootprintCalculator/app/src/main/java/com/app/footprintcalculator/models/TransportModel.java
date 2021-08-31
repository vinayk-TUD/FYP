package com.app.footprintcalculator.models;

public class TransportModel {
    private String id;
    private double vehicleEmission;
    private double busEmission;
    private double trainEmission;
    private double planeEmission;
    private double metroEmission;
    private double taxiEmission;
    private double totalEmission;

    public TransportModel() { }

    public TransportModel(String id, double vehicleEmission, double busEmission, double trainEmission, double planeEmission,
                          double metroEmission, double taxiEmission, double totalEmission) {
        this.id = id;
        this.vehicleEmission = vehicleEmission;
        this.busEmission = busEmission;
        this.trainEmission = trainEmission;
        this.planeEmission = planeEmission;
        this.metroEmission = metroEmission;
        this.taxiEmission = taxiEmission;
        this.totalEmission = totalEmission;
    }

    public String getId() {
        return id;
    }

    public double getBusEmission() {
        return busEmission;
    }

    public double getVehicleEmission() {
        return vehicleEmission;
    }

    public double getTrainEmission() {
        return trainEmission;
    }

    public double getPlaneEmission() {
        return planeEmission;
    }

    public double getMetroEmission() {
        return metroEmission;
    }

    public double getTaxiEmission() {
        return taxiEmission;
    }

    public double getTotalEmission() {
        return totalEmission;
    }
}
