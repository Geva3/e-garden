package com.android.e_garden.models;

import java.util.Date;

public class Watering {
    private Date date;

    public Watering() {
    }

    public Watering(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
