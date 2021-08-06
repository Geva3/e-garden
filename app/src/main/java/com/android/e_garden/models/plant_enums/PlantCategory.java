package com.android.e_garden.models.plant_enums;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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

    public static ArrayList<String> getStrings() {
        ArrayList<String> strings = new ArrayList<>();
        for (PlantCategory category : PlantCategory.values()) {
            strings.add(category.toString());
        }
        return strings;
    }

    public @NotNull String toString() {
        switch (this) {
            case FRUIT:
                return "Árvore Frutífera";
            case BOTANY:
                return "Árvore Botânica";
            case FLOWER:
                return "Flor";
            case LEGUME:
                return "Leguminosa";
            case SEASONING:
                return "Tempero";
            case VEGETABLE:
                return "Hortaliça";
            default:
                return "";
        }
    }
}
