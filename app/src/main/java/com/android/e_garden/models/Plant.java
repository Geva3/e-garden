package com.android.e_garden.models;

import androidx.annotation.NonNull;

import com.android.e_garden.models.plant_enums.PlantCategory;
import com.android.e_garden.models.plant_enums.PlantPlace;
import com.android.e_garden.models.plant_enums.PlantSeason;
import com.android.e_garden.models.plant_enums.PlantType;
import com.android.e_garden.models.plant_enums.PlantedOn;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class Plant implements Serializable {

    private String id;
    private String name;
    private PlantCategory category;
    private String origin;
    private Date plantedDate;
    private PlantedOn plantedOn;
    private PlantPlace plantedPlace;
    private PlantType plantType;
    private Long wateringPeriod;
    private Integer waterQuantity;
    private PlantSeason season;
    private ArrayList<Timestamp> harvests;
    private ArrayList<PlantPhoto> photos;
    private ArrayList<Timestamp> watering;
    private String description;
    private String fertilizer;

    private String user;

    public static Plant fromFirestore(QueryDocumentSnapshot document) {

        ArrayList<Timestamp> harvests = (ArrayList<Timestamp>) document.get("harvests");
        ArrayList<Timestamp> watering = (ArrayList<Timestamp>) document.get("watering");

        harvests = harvests == null ? new ArrayList<>() : harvests;
        watering = watering == null ? new ArrayList<>() : watering;

        Collections.sort(harvests);
        Collections.sort(watering);

        ArrayList<Map<String, Object>> photosFirestore = (ArrayList<Map<String, Object>>) document.get("photos");
        if (photosFirestore == null) {
            photosFirestore = new ArrayList<>();
        }
        ArrayList<PlantPhoto> photos = new ArrayList<>();
        for (Map<String, Object> item : photosFirestore) {
            photos.add(PlantPhoto.fromFirestore(item));
        }
        Collections.sort(photos, (plantPhoto, t1) -> t1.getDate().compareTo(plantPhoto.getDate()));

        return new Plant(
                document.getId(),
                document.get("name", String.class),
                PlantCategory.fromString(document.get("category", String.class)),
                document.get("origin", String.class),
                document.get("plantedDate", Date.class),
                PlantedOn.fromString(document.get("plantedOn", String.class)),
                PlantPlace.fromString(document.get("plantedPlace", String.class)),
                PlantType.fromString(document.get("plantType", String.class)),
                document.get("wateringPeriod", Long.class),
                document.get("wateringQuantity", Integer.class),
                PlantSeason.fromString(document.get("season", String.class)),
                harvests,
                photos,
                watering,
                document.getString("description"),
                document.getString("fertilizer"),
                document.get("user", String.class)
        );
    }

    public Plant(
            String id,
            String name,
            PlantCategory category,
            String origin,
            Date plantedDate,
            PlantedOn plantedOn,
            PlantPlace plantedPlace,
            PlantType plantType,
            Long wateringPeriod,
            Integer waterQuantity,
            PlantSeason season,
            @NonNull ArrayList<Timestamp> harvests,
            @NonNull ArrayList<PlantPhoto> photos,
            @NonNull ArrayList<Timestamp> watering,
            String description,
            String fertilizer,
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
        this.description = description;
        this.fertilizer = fertilizer;
        this.user = user;
    }

    public Plant() {
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
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

    public Long getWateringPeriod() {
        return wateringPeriod;
    }

    public void setWateringPeriod(Long wateringPeriod) {
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

    public ArrayList<Timestamp> getHarvests() {
        return harvests;
    }

    public void setHarvests(ArrayList<Timestamp> harvests) {
        this.harvests = harvests;
    }

    public ArrayList<PlantPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PlantPhoto> photos) {
        this.photos = photos;
    }

    public ArrayList<Timestamp> getWatering() {
        return watering;
    }

    public void setWatering(ArrayList<Timestamp> watering) {
        this.watering = watering;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }
}
