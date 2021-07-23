package com.android.e_garden.models;

import java.util.Date;

public class PlantPhoto {
    private Date date;
    private String path;

    public PlantPhoto() {
    }

    public PlantPhoto(Date date, String path) {
        this.date = date;
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
