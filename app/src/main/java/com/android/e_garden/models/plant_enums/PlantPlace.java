package com.android.e_garden.models.plant_enums;

public enum PlantPlace {
    INDOOR, OUTDOOR, SEMI_OUTDOOR, GREENHOUSE;

    public static PlantPlace fromString(String text) {
        try {
            if (text == null) {
                return null;
            }
            return PlantPlace.valueOf(text);
        } catch (IllegalArgumentException ignored) {

        }
        return null;
    }
}
