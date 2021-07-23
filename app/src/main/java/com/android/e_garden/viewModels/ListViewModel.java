package com.android.e_garden.viewModels;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class ListViewModel extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.list);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<>());


        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            String  itemValue = (String) listView.getItemAtPosition(position);

            Toast.makeText(getApplicationContext(),
                    "Position :"+ position +"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                    .show();

        });

        String user = Globals.getInstance().getUser().getUid();
        Query currentPlantsCollection = db.collection("plant").whereEqualTo("user", user);

        currentPlantsCollection.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("ANDROID_ERROR", "Error getting documents.", error);
                return;
            }

            ArrayList<String> items = new ArrayList<>();
            if (value == null) {
                adapter.clear();
                return;
            }
            for (QueryDocumentSnapshot document : value) {
                Plant plant = Plant.fromFirestore(document);
                String plantName = plant.getName();

                if (plantName == null) {
                    plantName = "No name for this plant";
                }

                items.add(plantName);
            }
            adapter.clear();
            adapter.addAll(items);
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Closing Activity")
            .setMessage("Are you sure you want to quit E-Garden?")
            .setPositiveButton("Yes", (dialog, which) -> {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                finish();
            })
            .setNegativeButton("No", null)
            .show();
    }
}
