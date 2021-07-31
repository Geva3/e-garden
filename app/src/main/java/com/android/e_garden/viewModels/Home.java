package com.android.e_garden.viewModels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.android.e_garden.MainActivity;
import com.android.e_garden.R;

public class Home extends AppCompatActivity {

    private ImageButton botao01, botao02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        botao01 = (ImageButton) findViewById(R.id.ibLista);

        botao01.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                botao01Activity();

            }
        });

        botao02 = (ImageButton) findViewById(R.id.ibAdicionar);

        botao02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                botao02Activity();

            }
        });



    }

    private void botao01Activity() {

        startActivity(new Intent(Home.this, TesteListView.class));


    }

    private void botao02Activity() {

        startActivity(new Intent(Home.this, AddPlant.class));


    }

}