package com.android.e_garden.models.plant_enums;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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

    public static ArrayList<String> getStrings() {
        ArrayList<String> strings = new ArrayList<>();
        for (PlantType category : PlantType.values()) {
            strings.add(category.toString());
        }
        return strings;
    }

    public @NotNull String toString() {
        switch (this) {
            case SEED:
                return "Semente";
            case CLONED_SEEDLING:
                return "Muda Clonada";
            case PURCHASED_SEEDLING:
                return "Muda Comprada";
            default:
                return "";
        }
    }
}
