package com.android.e_garden.viewModels;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.viewModels.listView.ListViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class HomeViewModel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageButton buttonList = findViewById(R.id.ibLista);
        buttonList.setOnClickListener(v -> startListPage());

        ImageButton buttonAddPlant = findViewById(R.id.ibAdicionar);
        buttonAddPlant.setOnClickListener(v -> startAddPlantPage());

        Button buttonLogout = findViewById(R.id.bLogout);
        buttonLogout.setOnClickListener(v -> logout());
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    private void startListPage() {
        startActivity(new Intent(this, ListViewModel.class));
    }

    private void startAddPlantPage() {
        startActivity(new Intent(this, AddPlantViewModel.class));
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Você está deixando seu Jardim")
                .setMessage("Tem certeza que você quer sair?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    Globals.getInstance().setUser(null);
                    finish();
                })
                .setNegativeButton("Não", null)
                .show();
    }
}