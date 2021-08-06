package com.android.e_garden.viewModels;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.bumptech.glide.Glide;

public class DetailsViewModel extends AppCompatActivity {

    private Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar_Details);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Detalhes");

        plant = (Plant) getIntent().getSerializableExtra("plant");

        TextView tvname = findViewById(R.id.tvName_Details);
        tvname.setText(plant.getName());
        TextView tvcat = findViewById(R.id.tvCategory_Details);
        tvcat.setText(plant.getCategory().toString());
        TextView tvrega = findViewById(R.id.tvperiodoRega_Details);
        tvrega.setText(plant.getWateringPeriod() != null ? plant.getWateringPeriod().toString() : "");
        ImageView images = findViewById(R.id.ivPlant_Details);
        images.setAdjustViewBounds(true);
        Glide.with(this).load(Globals.getInstance().getPlantImage(plant.getPhotos().get(0).getPath())).into(images);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailsmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_erase) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Excluir Planta")
                    .setMessage("Você deseja excluir a planta?")
                    .setPositiveButton("Sim", (dialog, which) -> this.finish())
                    .setNegativeButton("Não", null)
                    .show();

            return true;
        } else if (id == R.id.action_edit) {
            startActivity(new Intent(this, EditPlant.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}