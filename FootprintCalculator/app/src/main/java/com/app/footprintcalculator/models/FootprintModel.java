package com.app.footprintcalculator.models;

public class FootprintModel {
    private String id;
    private String name;
    private String date;
    private String time;
    private String userId;

    public FootprintModel() { }

    public FootprintModel(String id, String name, String date, String time, String userId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUserId() {
        return userId;
    }
}
