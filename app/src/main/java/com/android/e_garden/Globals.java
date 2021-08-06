package com.android.e_garden;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class Globals {

    private static Globals instance;

    private FirebaseUser user;
    private HashMap<String, Uri> plantImages = new HashMap<>();

    public static Globals getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new Globals();
        return instance;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public void addPlantImage(String path, Uri image) {
        plantImages.put(path, image);
    }

    public Uri getPlantImage(String path) {
        return plantImages.get(path);
    }

    public void clearPlantImages() {
        plantImages.clear();
    }
}
