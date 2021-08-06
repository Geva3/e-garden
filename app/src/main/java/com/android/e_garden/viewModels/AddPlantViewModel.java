package com.android.e_garden.viewModels;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.android.e_garden.models.PlantPhoto;
import com.android.e_garden.models.plant_enums.PlantCategory;
import com.android.e_garden.models.plant_enums.PlantPlace;
import com.android.e_garden.models.plant_enums.PlantSeason;
import com.android.e_garden.models.plant_enums.PlantType;
import com.android.e_garden.models.plant_enums.PlantedOn;
import com.android.e_garden.utils.DateInputMask;
import com.android.e_garden.utils.ImageUtils;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddPlantViewModel extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Uri photoPath;
    private ActivityResultLauncher<Intent> signInActivityResult;
    private EditText datePlant;

    private final Plant plant = new Plant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1337);
        }

        Toolbar toolbar = findViewById(R.id.toolbar_Add);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Nova Planta");

        plant.setUser(Globals.getInstance().getUser().getUid());

        Button saveButton = findViewById(R.id.bSave);
        saveButton.setOnClickListener(v -> save());

        ImageView image = findViewById(R.id.ivPlant);

        signInActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        image.setAdjustViewBounds(true);
                        image.setImageURI(photoPath);
                    }
                }
        );

        datePlant = findViewById(R.id.etdataPlantio);
        new DateInputMask(datePlant);

        //TODO create method to implement this (cleaner code)
        Spinner spCategory = findViewById(R.id.spCategory);
        ArrayAdapter<PlantCategory> adCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PlantCategory.values());
        adCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(adCategory);
        spCategory.setOnItemSelectedListener(this);

        Spinner spLocalPlantio = findViewById(R.id.splocalPlantio);
        ArrayAdapter<PlantedOn> adLocalPlantio = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PlantedOn.values());
        adLocalPlantio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocalPlantio.setAdapter(adLocalPlantio);
        spLocalPlantio.setOnItemSelectedListener(this);

        Spinner spTipoPlantio = findViewById(R.id.sptipoPlantio);
        ArrayAdapter<PlantType> adTipoPlantio = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PlantType.values());
        adTipoPlantio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoPlantio.setAdapter(adTipoPlantio);
        spTipoPlantio.setOnItemSelectedListener(this);

        Spinner spAmbiente = findViewById(R.id.spAmbiente);
        ArrayAdapter<PlantPlace> adAmbiente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PlantPlace.values());
        adAmbiente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAmbiente.setAdapter(adAmbiente);
        spAmbiente.setOnItemSelectedListener(this);

        Spinner spEstacao = findViewById(R.id.spEstacao);
        ArrayAdapter<PlantSeason> adEstacao = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PlantSeason.values());
        adEstacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstacao.setAdapter(adEstacao);
        spEstacao.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);
        Class<?> aClass = item.getClass();
        if (PlantCategory.class.equals(aClass)) {
            plant.setCategory((PlantCategory) item);
        } else if (PlantedOn.class.equals(aClass)) {
            plant.setPlantedOn((PlantedOn) item);
        } else if (PlantPlace.class.equals(aClass)) {
            plant.setPlantedPlace((PlantPlace) item);
        } else if (PlantType.class.equals(aClass)) {
            plant.setPlantType((PlantType) item);
        } else if (PlantSeason.class.equals(aClass)) {
            plant.setSeason((PlantSeason) item);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void save() {

        AtomicBoolean finished = new AtomicBoolean(false);

        if (photoPath != null) {
            String photoPathStorage = plant.getUser() + "/" + UUID.randomUUID().toString();
            StorageReference ref = FirebaseStorage.getInstance().getReference().child(photoPathStorage);
            ref.putFile(photoPath)
                    .addOnSuccessListener(taskSnapshot -> {
                        if (finished.get()) {
                            Toast.makeText(this, "Planta criada com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            finished.set(true);
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (finished.get()) {
                            Toast.makeText(this, "Planta criada com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            finished.set(true);
                        }
                    });

            ArrayList<PlantPhoto> photos = new ArrayList<>();
            photos.add(new PlantPhoto(new Date(), photoPathStorage));
            plant.setPhotos(photos);
        } else {
            finished.set(true);
        }

        plant.setName(((EditText) findViewById(R.id.etName)).getText().toString());
        plant.setOrigin(((EditText) findViewById(R.id.etorigemPlantio)).getText().toString());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            plant.setPlantedDate(sdf.parse(datePlant.getText().toString()));
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        EditText wateringPeriod = findViewById(R.id.etperiodoRega);
        if (wateringPeriod.getText() != null) {
            try {
                Long period = Long.parseLong(wateringPeriod.getText().toString());
                plant.setWateringPeriod(period);
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
        }
        EditText wateringQuantity = findViewById(R.id.etqtdeAgua);
        if (wateringQuantity.getText() != null) {
            try {
                Integer quantity = Integer.parseInt(wateringPeriod.getText().toString());
                plant.setWaterQuantity(quantity);
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
        }
        plant.setFertilizer(((EditText) findViewById(R.id.etFertilizante)).getText().toString());
        plant.setDescription(((EditText) findViewById(R.id.etObservacao)).getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("plant").add(plant);

        if (finished.get()) {
            Toast.makeText(this, "Planta criada com sucesso", Toast.LENGTH_LONG).show();
            finish();
        } else {
            finished.set(true);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menucamera) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                return true;
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = ImageUtils.getInstance().createImageFile("newPhoto", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            if (photoFile != null) {
                photoPath = Uri.fromFile(photoFile);
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.android.e_garden.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
            signInActivityResult.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
