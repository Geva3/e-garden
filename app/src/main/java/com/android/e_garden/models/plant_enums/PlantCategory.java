package com.android.e_garden.models.plant_enums;

public enum PlantCategory {
    SEASONING, FLOWER, FRUIT, VEGETABLE, LEGUME, BOTANY;

    public static PlantCategory fromString(String text) {
        try {
            if (text == null) {
                return null;
            }
            return PlantCategory.valueOf(text);
        } catch (IllegalArgumentException ignored) {

        }
        return null;
    }
}
