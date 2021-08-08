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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.android.e_garden.models.PlantPhoto;
import com.android.e_garden.utils.ImageUtils;
import com.android.e_garden.viewModels.photoHistoryView.PhotoHistoryViewModel;
import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class DetailsViewModel extends AppCompatActivity implements Globals.PlantObservable {

    private Plant plant;
    private Uri photoPath;
    private ActivityResultLauncher<Intent> signInActivityResult;
    private ImageView images;

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

        String plantId = getIntent().getStringExtra("plant");
        for (Plant plantItem : Globals.getInstance().getPlants()) {
            if (plantItem.getId().equals(plantId)) {
                plant = plantItem;
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        images = findViewById(R.id.ivPlant_Details);
        signInActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        images.setImageURI(photoPath);

                        Toast.makeText(this, "Salvando foto...", Toast.LENGTH_LONG).show();
                        String photoPathStorage = plant.getUser() + "/" + UUID.randomUUID().toString();
                        StorageReference ref = FirebaseStorage.getInstance().getReference().child(photoPathStorage);
                        ref.putFile(photoPath)
                                .addOnSuccessListener(taskSnapshot -> {
                                    db.collection("plant").document(plant.getId()).update("photos", plant.getPhotos());
                                    Toast.makeText(this, "Foto salva com sucesso", Toast.LENGTH_LONG).show();
                                });

                        ArrayList<PlantPhoto> photos = plant.getPhotos();
                        photos.add(new PlantPhoto(new Date(), photoPathStorage));
                        plant.setPhotos(photos);
                    }
                }
        );

        Button harvestPlant = findViewById(R.id.bColher);
        harvestPlant.setOnClickListener(view -> {
            ArrayList<Timestamp> harvests = plant.getHarvests();
            harvests.add(Timestamp.now());
            plant.setHarvests(harvests);
            db.collection("plant").document(plant.getId()).update("harvests", plant.getHarvests());
        });

        Button waterPlant = findViewById(R.id.bRegar);
        waterPlant.setOnClickListener(view -> {
            ArrayList<Timestamp> watering = plant.getWatering();
            watering.add(Timestamp.now());
            plant.setWatering(watering);
            db.collection("plant").document(plant.getId()).update("watering", plant.getWatering());
        });

        Globals.getInstance().setPlantObservable(this);
        updatePageComponents();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailsmenu, menu);
        return true;
    }

    private void updatePageComponents() {
        images.setAdjustViewBounds(true);
        images.setScaleType(ImageView.ScaleType.CENTER_CROP);
        images.setImageResource(R.drawable.plant);
        ArrayList<PlantPhoto> photos = plant.getPhotos();
        if (photos.size() > 0) {
            Uri plantUri = Globals.getInstance().getPlantImage(photos.get(0).getPath());
            for (int i = 1; i < photos.size(); i++) {
                if (plantUri != null) {
                    break;
                }
                plantUri = Globals.getInstance().getPlantImage(photos.get(i).getPath());
            }
            if (plantUri != null) {
                Glide.with(this).load(plantUri).into(images);
            }
        }

        TextView name = findViewById(R.id.tvName_Details);
        name.setText(plant.getName());

        TextView category = findViewById(R.id.tvCategory_Details);
        category.setText(plant.getCategory().toString());

        ArrayList<String> daysOfWeek = new ArrayList<>();
        daysOfWeek.add("Domingo");
        daysOfWeek.add("Segunda");
        daysOfWeek.add("Terça");
        daysOfWeek.add("Quarta");
        daysOfWeek.add("Quinta");
        daysOfWeek.add("Sexta");
        daysOfWeek.add("Sábado");
        TextView tvrega = findViewById(R.id.tvperiodoRega_Details);
        ArrayList<Long> wateringPeriodDaysOfWeek = plant.getWateringPeriodDaysOfWeek();
        if (wateringPeriodDaysOfWeek.size() > 0) {
            StringBuilder days = new StringBuilder();
            for (Long day : wateringPeriodDaysOfWeek) {
                days.append(daysOfWeek.get((int) (day - 1))).append(", ");
            }
            days.setCharAt(days.length() - 2, ' ');
            tvrega.setText(days.toString().trim());
        } else {
            tvrega.setText("Nenhum dia");
        }

        TextView tvregahours = findViewById(R.id.tvperiodoRegaHoras_Details);
        ArrayList<Long> wateringPeriodHoursOfDay = plant.getWateringPeriodHoursOfDay();
        if (wateringPeriodHoursOfDay.size() > 0) {
            StringBuilder hours = new StringBuilder();
            for (Long hour : wateringPeriodHoursOfDay) {
                hours.append(hour.toString()).append("h, ");
            }
            hours.setCharAt(hours.length() - 2, ' ');
            tvregahours.setText(hours.toString().trim());
        } else {
            tvregahours.setText("Nenhum horário");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("hh'h'mm - dd/MM/yyyy", Locale.US);

        StringBuilder watering = new StringBuilder();
        for (Timestamp water : plant.getWatering()) {
            watering.append(sdf.format(water.toDate())).append("\n");
        }
        TextView plantWatering = findViewById(R.id.tvultimasRegas_Details);
        plantWatering.setText(watering.toString().trim());

        TextView plantedDate = findViewById(R.id.tvdataPlantio_Details);
        plantedDate.setText(plant.getPlantedDate() != null ? sdf.format(plant.getPlantedDate()) : "");

        StringBuilder harvests = new StringBuilder();
        for (Timestamp harvest : plant.getHarvests()) {
            harvests.append(sdf.format(harvest.toDate())).append("\n");
        }
        TextView plantHarvests = findViewById(R.id.tvdataColheita_Details);
        plantHarvests.setText(harvests.toString().trim());

        TextView plantedOn = findViewById(R.id.tvlocalPlantio_Details);
        plantedOn.setText(plant.getPlantedOn().toString());

        TextView plantType = findViewById(R.id.tvtipoPlantio_Details);
        plantType.setText(plant.getPlantType().toString());

        TextView plantOrigin = findViewById(R.id.tvorigemPlantio_Details);
        plantOrigin.setText(plant.getOrigin());

        TextView wateringQuantity = findViewById(R.id.tvqtdeAgua_Details);
        wateringQuantity.setText(plant.getWaterQuantity() != null ? plant.getWaterQuantity().toString() + " ml" : "");

        TextView plantPlace = findViewById(R.id.tvAmbiente_Details);
        plantPlace.setText(plant.getPlantedPlace().toString());

        TextView plantSeason = findViewById(R.id.tvEstacao_Details);
        plantSeason.setText(plant.getSeason().toString());

        TextView fertilizer = findViewById(R.id.tvFertilizante_Details);
        fertilizer.setText(plant.getFertilizer());

        TextView description = findViewById(R.id.tvObservacao_Details);
        description.setText(plant.getDescription());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_erase) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Excluir Planta")
                    .setMessage("Você tem certeza que deseja excluir esta planta?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("plant").document(plant.getId()).delete();
                        this.finish();
                    })
                    .setNegativeButton("Não", null)
                    .show();

            return true;
        } else if (id == R.id.action_edit) {
            Intent intent = new Intent(this, UpdatePlantViewModel.class);
            intent.putExtra("plant", plant.getId());
            startActivity(intent);
            return true;
        } else if (id == R.id.action_newphoto) {
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
        } else if (id == R.id.action_photos_history) {
            Intent intent = new Intent(this, PhotoHistoryViewModel.class);
            intent.putExtra("plant", plant.getId());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlantUpdate() {
        for (Plant plantItem : Globals.getInstance().getPlants()) {
            if (plantItem.getId().equals(plant.getId())) {
                plant = plantItem;
            }
        }
        updatePageComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Globals.getInstance().setPlantObservable(this);
        for (Plant plantItem : Globals.getInstance().getPlants()) {
            if (plantItem.getId().equals(plant.getId())) {
                plant = plantItem;
            }
        }
        updatePageComponents();
    }
}