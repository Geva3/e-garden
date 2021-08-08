package com.android.e_garden;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.e_garden.models.Plant;
import com.android.e_garden.models.PlantPhoto;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class Globals implements EventListener<QuerySnapshot> {

    private static Globals instance;

    private FirebaseUser user;
    private final HashMap<String, Uri> plantImages = new HashMap<>();
    private final PlantList plants = new PlantList();

    public static Globals getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new Globals();
        return instance;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Query currentPlantsCollection = db.collection("plant").whereEqualTo("user", user.getUid());
            currentPlantsCollection.addSnapshotListener(this);
        }
    }

    public void addPlantImage(String path, Uri image) {
        plantImages.put(path, image);
    }

    public Uri getPlantImage(String path) {
        return plantImages.get(path);
    }

    public void clearPlantImages() {
        plantImages.clear();
    }

    public ArrayList<Plant> getPlants() {
        return plants.getPlants();
    }

    public void addPlantObservable(PlantObservable observable) {
        plants.addObservable(observable);
    }

    public void removePlantObservable(PlantObservable observable) {
        plants.removeObservable(observable);
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if (error != null) {
            Log.w("ANDROID_ERROR", "Error getting documents.", error);
            return;
        }

        ArrayList<Plant> items = new ArrayList<>();
        if (value == null) {
            plants.setPlants(items);
            plants.notifyListeners();
            return;
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();

        for (QueryDocumentSnapshot document : value) {
            Plant plant = Plant.fromFirestore(document);
            for (PlantPhoto photo : plant.getPhotos()) {
                if (Globals.getInstance().getPlantImage(photo.getPath()) == null) {
                    StorageReference reference = storage.getReference(photo.getPath());
                    reference.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                addPlantImage(photo.getPath(), uri);
                                plants.notifyListeners();
                    });
                }
            }
            items.add(plant);
        }

        plants.setPlants(items);
        plants.notifyListeners();
    }

    private static class PlantList {

        private ArrayList<Plant> plants;
        private final ArrayList<PlantObservable> observables = new ArrayList<>();

        public PlantList() {
            plants = new ArrayList<>();
        }

        public void notifyListeners() {
            for (PlantObservable observable : observables) {
                observable.onPlantUpdate();
            }
        }

        public ArrayList<Plant> getPlants() {
            return plants;
        }

        public void setPlants(ArrayList<Plant> plants) {
            this.plants = plants;
        }

        public void addObservable(PlantObservable observable) {
            this.observables.add(observable);
        }

        public void removeObservable(PlantObservable observable) {
            this.observables.remove(observable);
        }
    }

    public interface PlantObservable {
        void onPlantUpdate();
    }
}
