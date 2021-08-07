package com.android.e_garden.viewModels.listView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.android.e_garden.viewModels.DetailsViewModel;

import java.util.ArrayList;

public class ListViewModel extends AppCompatActivity implements Globals.PlantObservable {

    private ListView listView;
    private ListViewAdapter adapter;

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

        Globals.getInstance().setPlantObservable(this);
        adapter.addAll(Globals.getInstance().getPlants());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Globals.getInstance().setPlantObservable(this);
        adapter.clear();
        adapter.addAll(Globals.getInstance().getPlants());
    }

    @Override
    public void onPlantUpdate() {
        adapter.clear();
        adapter.addAll(Globals.getInstance().getPlants());
    }
}
