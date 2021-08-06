package com.android.e_garden.viewModels.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.android.e_garden.models.PlantPhoto;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ListViewAdapter extends ArrayAdapter<Plant> {
    Context context;
    ArrayList<Plant> plants;

    public ListViewAdapter(Context context, ArrayList<Plant> plants) {
        super(context, R.layout.item_list, plants);
        this.context = context;
        this.plants = plants;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.item_list, parent, false); // TODO remove this warning
        ImageView image = row.findViewById(R.id.ivPlant);
        TextView plantTitle = row.findViewById(R.id.tvplantName);
        TextView plantDescription = row.findViewById(R.id.tvplantCategory);
        TextView plantStatus = row.findViewById(R.id.tvplantStatus);

        Plant plant = plants.get(position);

        ArrayList<PlantPhoto> photos = plant.getPhotos();
        image.setImageResource(R.drawable.plant);
        image.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        image.setAdjustViewBounds(true);
        if (photos.size() > 0) {
            if (Globals.getInstance().getPlantImage(photos.get(0).getPath()) != null) {
                Glide.with(row).asBitmap().load(Globals.getInstance().getPlantImage(photos.get(0).getPath())).into(image);
            } else {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference reference = storage.getReference(photos.get(0).getPath());
                reference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(row).asBitmap().load(uri).into(image));
            }
        }

        plantTitle.setText(plant.getName());
        plantDescription.setText(plant.getCategory().toString());

        if (plant.getWateringPeriod() != null) {
            ArrayList<Date> watering = plant.getWatering();
            Collections.sort(watering);
            if (watering.size() > 0) {
                Date lastWatering = watering.get(watering.size() - 1);

                long hoursInMilli = 60 * 60 * 1000;
                long hours = (new Date().getTime() - (lastWatering.getTime() + plant.getWateringPeriod() * 3600000)) / hoursInMilli;

                if (hours < 0) {
                    plantStatus.setText("Rega atrasada");
                } else if (hours == 0) {
                    plantStatus.setText("Regar agora");
                } else {
                    plantStatus.setText("Regar em " + ((hours > 24) ? hours % 24 + " dias" : hours + " horas"));
                }
            } else {
                plantStatus.setText("Regar agora");
            }
        } else {
            plantStatus.setText("Não foi definido um período de rega para esta planta");
        }

        return row;
    }
}
