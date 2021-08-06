package com.android.e_garden.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PlantPhoto implements Serializable {
    private Date date;
    private String path;

    public static PlantPhoto fromFirestore(Map<String, Object> document) {
        Timestamp date = (Timestamp) document.get("date");
        return new PlantPhoto(date == null ? null : date.toDate(), (String) document.get("path"));
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
