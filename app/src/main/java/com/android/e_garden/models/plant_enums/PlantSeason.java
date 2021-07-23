package com.android.e_garden.models.plant_enums;

public enum PlantSeason {
    WINTER, SUMMER, SPRING, AUTUMN;

    public static PlantSeason fromString(String text) {
        try {
            if (text == null) {
                return null;
            }
            return PlantSeason.valueOf(text);
        } catch (IllegalArgumentException ignored) {

        }
        return null;
    }
}
