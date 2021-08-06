package com.android.e_garden.models.plant_enums;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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

    public static ArrayList<String> getStrings() {
        ArrayList<String> strings = new ArrayList<>();
        for (PlantPlace category : PlantPlace.values()) {
            strings.add(category.toString());
        }
        return strings;
    }

    public @NotNull String toString() {
        switch (this) {
            case INDOOR:
                return "Interno";
            case OUTDOOR:
                return "Externo";
            case SEMI_OUTDOOR:
                return "Semi-Externo";
            case GREENHOUSE:
                return "Estufa";
            default:
                return "";
        }
    }
}
