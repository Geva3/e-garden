package com.android.e_garden;

import com.google.firebase.auth.FirebaseUser;

public class Globals {

    private static Globals instance;

    private FirebaseUser user;

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
}
