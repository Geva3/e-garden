package com.android.e_garden.viewModels.listView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.android.e_garden.models.PlantPhoto;
import com.android.e_garden.viewModels.DetailsViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListViewModel extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Meu Jardim");

        listView = findViewById(R.id.lvPlant);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ListViewAdapter adapter = new ListViewAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Plant itemValue = (Plant) listView.getItemAtPosition(position);
            Intent intent = new Intent(this, DetailsViewModel.class);
            intent.putExtra("plant", itemValue);
            startActivity(intent);
        });

        String user = Globals.getInstance().getUser().getUid();
        Query currentPlantsCollection = db.collection("plant").whereEqualTo("user", user);

        currentPlantsCollection.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("ANDROID_ERROR", "Error getting documents.", error);
                return;
            }

            ArrayList<Plant> items = new ArrayList<>();
            if (value == null) {
                adapter.clear();
                return;
            }

            FirebaseStorage storage = FirebaseStorage.getInstance();
            for (QueryDocumentSnapshot document : value) {
                Plant plant = Plant.fromFirestore(document);
                for (PlantPhoto photo : plant.getPhotos()) {
                    if (Globals.getInstance().getPlantImage(photo.getPath()) == null) {
                        StorageReference reference = storage.getReference(photo.getPath());
                        reference.getDownloadUrl().addOnSuccessListener(uri -> Globals.getInstance().addPlantImage(photo.getPath(), uri));
                    }
                }
                items.add(plant);
            }
            adapter.clear();
            adapter.addAll(items);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;
    }
}
