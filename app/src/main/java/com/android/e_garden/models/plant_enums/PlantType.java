package com.android.e_garden.models.plant_enums;

public enum PlantType {
    SEED, PURCHASED_SEEDLING, CLONED_SEEDLING;

    public static PlantType fromString(String text) {
        try {
            if (text == null) {
                return null;
            }
            return PlantType.valueOf(text);
        } catch (IllegalArgumentException ignored) {

        }
        return null;
    }
}
