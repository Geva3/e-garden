package com.android.e_garden.models.plant_enums;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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

    public static ArrayList<String> getStrings() {
        ArrayList<String> strings = new ArrayList<>();
        for (PlantSeason category : PlantSeason.values()) {
            strings.add(category.toString());
        }
        return strings;
    }

    public @NotNull String toString() {
        switch (this) {
            case WINTER:
                return "Inverno";
            case SPRING:
                return "Primavera";
            case SUMMER:
                return "Verão";
            case AUTUMN:
                return "Outono";
            default:
                return "";
        }
    }
}
