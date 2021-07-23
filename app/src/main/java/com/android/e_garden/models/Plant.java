package com.android.e_garden.models;

import com.android.e_garden.models.plant_enums.PlantCategory;
import com.android.e_garden.models.plant_enums.PlantOrigin;
import com.android.e_garden.models.plant_enums.PlantPlace;
import com.android.e_garden.models.plant_enums.PlantSeason;
import com.android.e_garden.models.plant_enums.PlantType;
import com.android.e_garden.models.plant_enums.PlantedOn;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class Plant {

    private String id;
    private String name;
    private PlantCategory category;
    private PlantOrigin origin;
    private Date plantedDate;
    private PlantedOn plantedOn;
    private PlantPlace plantedPlace;
    private PlantType plantType;
    private Double wateringPeriod;
    private Integer waterQuantity;
    private PlantSeason season;
    private ArrayList<Harvest> harvests;
    private ArrayList<PlantPhoto> photos;
    private ArrayList<Watering> watering;

    private String user;

    public static Plant fromFirestore(QueryDocumentSnapshot document) {
        return new Plant(
                document.getId(),
                document.get("name", String.class),
                PlantCategory.fromString(document.get("category", String.class)),
                PlantOrigin.fromString(document.get("origin", String.class)),
                document.get("planted_date", Date.class),
                PlantedOn.fromString(document.get("planted_on", String.class)),
                PlantPlace.fromString(document.get("planted_place", String.class)),
                PlantType.fromString(document.get("plant_type", String.class)),
                document.get("watering_period", Double.class),
                document.get("watering_quantity", Integer.class),
                PlantSeason.fromString(document.get("plant_season", String.class)),
                null,
                null,
                null,
                document.get("user", String.class)
        );
    }

    public Plant(
            String id,
            String name,
            PlantCategory category,
            PlantOrigin origin,
            Date plantedDate,
            PlantedOn plantedOn,
            PlantPlace plantedPlace,
            PlantType plantType,
            Double wateringPeriod,
            Integer waterQuantity,
            PlantSeason season,
            ArrayList<Harvest> harvests,
            ArrayList<PlantPhoto> photos,
            ArrayList<Watering> watering,
            String user) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.origin = origin;
        this.plantedDate = plantedDate;
        this.plantedOn = plantedOn;
        this.plantedPlace = plantedPlace;
        this.plantType = plantType;
        this.wateringPeriod = wateringPeriod;
        this.waterQuantity = waterQuantity;
        this.season = season;
        this.harvests = harvests;
        this.photos = photos;
        this.watering = watering;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantCategory getCategory() {
        return category;
    }

    public void setCategory(PlantCategory category) {
        this.category = category;
    }

    public PlantOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(PlantOrigin origin) {
        this.origin = origin;
    }

    public Date getPlantedDate() {
        return plantedDate;
    }

    public void setPlantedDate(Date plantedDate) {
        this.plantedDate = plantedDate;
    }

    public PlantedOn getPlantedOn() {
        return plantedOn;
    }

    public void setPlantedOn(PlantedOn plantedOn) {
        this.plantedOn = plantedOn;
    }

    public PlantPlace getPlantedPlace() {
        return plantedPlace;
    }

    public void setPlantedPlace(PlantPlace plantedPlace) {
        this.plantedPlace = plantedPlace;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public double getWateringPeriod() {
        return wateringPeriod;
    }

    public void setWateringPeriod(Double wateringPeriod) {
        this.wateringPeriod = wateringPeriod;
    }

    public Integer getWaterQuantity() {
        return waterQuantity;
    }

    public void setWaterQuantity(Integer waterQuantity) {
        this.waterQuantity = waterQuantity;
    }

    public PlantSeason getSeason() {
        return season;
    }

    public void setSeason(PlantSeason season) {
        this.season = season;
    }

    public ArrayList<Harvest> getHarvests() {
        return harvests;
    }

    public void setHarvests(ArrayList<Harvest> harvests) {
        this.harvests = harvests;
    }

    public ArrayList<PlantPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PlantPhoto> photos) {
        this.photos = photos;
    }

    public ArrayList<Watering> getWatering() {
        return watering;
    }

    public void setWatering(ArrayList<Watering> watering) {
        this.watering = watering;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
