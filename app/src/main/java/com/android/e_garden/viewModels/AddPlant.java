package com.android.e_garden.viewModels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.android.e_garden.R;

public class AddPlant extends AppCompatActivity {

    private Button botao03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        Toolbar toolbar = findViewById(R.id.toolbar_Add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Nova Planta");


        botao03 = (Button) findViewById(R.id.bSave);

        botao03.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                botao03Activity();

            }
        });
    }

    private void botao03Activity() {

        startActivity(new Intent(AddPlant.this, Home.class));


    }

    public boolean onCreateOptionsMenu(Menu menu) // Habilita o menu e mostra ele na toolbar
    {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

}
