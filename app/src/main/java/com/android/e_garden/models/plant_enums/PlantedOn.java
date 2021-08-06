package com.android.e_garden.models.plant_enums;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public enum PlantedOn {
    VASE, PLAT, YARD;

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

    public static ArrayList<String> getStrings() {
        ArrayList<String> strings = new ArrayList<>();
        for (PlantedOn category : PlantedOn.values()) {
            strings.add(category.toString());
        }
        return strings;
    }

    public @NotNull String toString() {
        switch (this) {
            case VASE:
                return "Vaso";
            case PLAT:
                return "Canteiro";
            case YARD:
                return "Quintal";
            default:
                return "";
        }
    }
}
