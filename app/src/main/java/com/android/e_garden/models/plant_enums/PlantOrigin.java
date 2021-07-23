package com.android.e_garden.models.plant_enums;

public enum PlantOrigin {
    STORE, DONATION;

    public static PlantOrigin fromString(String text) {
        try {
            if (text == null) {
                return null;
            }
            return PlantOrigin.valueOf(text);
        } catch (IllegalArgumentException ignored) {

        }
        return null;
    }
}
