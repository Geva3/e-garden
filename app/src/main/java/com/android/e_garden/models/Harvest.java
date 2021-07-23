package com.android.e_garden.models;

import java.util.Date;

public class Harvest {

    private Date date;

    public Harvest() {
    }

    public Harvest(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
