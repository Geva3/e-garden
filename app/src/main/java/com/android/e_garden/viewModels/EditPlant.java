package com.android.e_garden.viewModels;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.e_garden.R;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditPlant extends AppCompatActivity {

    String nome = "Cebolinha";
    String categoria = "Tempero";
    String rega = "Todos os dias / 08h - 18h";
    int image = R.drawable.cebolete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plant);
        Toolbar toolbar = findViewById(R.id.toolbar_Edit);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Editar Planta");

        TextView tvname = findViewById(R.id.etName_Edit);
        tvname.setText(nome);
        TextView tvcat = findViewById(R.id.etCategory_Edit);
        tvcat.setText(categoria);
        TextView tvrega = findViewById(R.id.etperiodoRega_Edit);
        tvrega.setText(rega);
        ImageView images = findViewById(R.id.ivPlant_Edit);
        images.setImageResource(image);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

}