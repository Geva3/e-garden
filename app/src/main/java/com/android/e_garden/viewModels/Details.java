package com.android.e_garden.viewModels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.e_garden.R;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    String nome = "Cebolinha";
    String categoria = "Tempero";
    String rega = "Todos os dias / 08h - 18h";
    int image = R.drawable.cebolete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar_Details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detalhes");

        TextView tvname = findViewById(R.id.tvName_Details);
        tvname.setText(nome);
        TextView tvcat = findViewById(R.id.tvCategory_Details);
        tvcat.setText(categoria);
        TextView tvrega = findViewById(R.id.tvperiodoRega_Details);
        tvrega.setText(rega);
        ImageView images = findViewById(R.id.ivPlant_Details);
        images.setImageResource(image);





    }

    public boolean onCreateOptionsMenu(Menu menu) // Habilita o menu e mostra ele na toolbar
    {
        getMenuInflater().inflate(R.menu.detailsmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.action_erase:

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Excluir Planta")
                        .setMessage("Você deseja excluir a planta?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            this.finish();
                        })
                        .setNegativeButton("Não", null)
                        .show();

                return true;

            case R.id.action_edit:
                startActivity(new Intent(Details.this, EditPlant.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }



}