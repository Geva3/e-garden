package com.android.e_garden.viewModels.listView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.android.e_garden.viewModels.DetailsViewModel;

import java.util.ArrayList;
import java.util.Collections;

public class ListViewModel extends AppCompatActivity implements Globals.PlantObservable {

    private ListView listView;
    private ListViewAdapter adapter;

    private String plantName;
    private ArrayList<Plant> allPlants;

    private boolean nameAsc = true;

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

        adapter = new ListViewAdapter(this, new ArrayList<>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Plant itemValue = (Plant) listView.getItemAtPosition(position);
            Intent intent = new Intent(this, DetailsViewModel.class);
            intent.putExtra("plant", itemValue.getId());
            startActivity(intent);
        });

        allPlants = Globals.getInstance().getPlants();
        sortByWatering();
        Globals.getInstance().addPlantObservable(this);
        adapter.addAll(allPlants);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                plantName = s;
                filterPlants();
                return false;
            }
        });
        return true;
    }

    private void filterPlants() {

        allPlants = Globals.getInstance().getPlants();
        ArrayList<Plant> filteredPlants = new ArrayList<>();

        for (Plant plant : allPlants) {
            if (plantName != null) {
                if (plant.getName().toLowerCase().contains(plantName)) {
                    filteredPlants.add(plant);
                }
            } else {
                filteredPlants = allPlants;
                break;
            }
        }

        adapter.clear();
        adapter.addAll(filteredPlants);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filterPlants();
    }

    @Override
    public void onPlantUpdate() {
        filterPlants();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            if (nameAsc) {
                nameAsc = false;
                Collections.sort(allPlants, (plant, t1) -> {
                    if (plant.getName() == null && t1.getName() == null) {
                        return 0;
                    } else if (plant.getName() == null) {
                        return 1;
                    } else if (t1.getName() == null) {
                        return -1;
                    }
                    return t1.getName().toLowerCase().compareTo(plant.getName().toLowerCase());
                });
            } else {
                nameAsc = true;
                Collections.sort(allPlants, (plant, t1) -> {
                    if (plant.getName() == null && t1.getName() == null) {
                        return 0;
                    } else if (plant.getName() == null) {
                        return -1;
                    } else if (t1.getName() == null) {
                        return 1;
                    }
                    return plant.getName().toLowerCase().compareTo(t1.getName().toLowerCase());
                });
            }
            filterPlants();
            return true;
        } else if (item.getItemId() == R.id.action_sort_watering) {
            sortByWatering();
            filterPlants();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortByWatering() {
        Collections.sort(allPlants, (plant, t1) -> {
            Long plantHours = plant.calculateRemainingHours();
            Long t1Hours = t1.calculateRemainingHours();
            if (plantHours == null && t1Hours == null) {
                return 0;
            } else if (plantHours == null) {
                return 1;
            } else if (t1Hours == null) {
                return -1;
            }
            return plantHours.compareTo(t1Hours);
        });
    }

    @Override
    public void finish() {
        Globals.getInstance().removePlantObservable(this);
        super.finish();
    }
}
