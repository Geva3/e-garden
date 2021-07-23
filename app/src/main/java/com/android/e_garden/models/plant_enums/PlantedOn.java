package com.android.e_garden.models.plant_enums;

public enum PlantedOn {
    VASE, PLAT;

    public static PlantedOn fromString(String text) {
        try {
            if (text == null) {
                return null;
            }
            return PlantedOn.valueOf(text);
        } catch (IllegalArgumentException ignored) {

        }
        return null;
    }
}
