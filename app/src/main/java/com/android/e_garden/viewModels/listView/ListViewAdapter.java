package com.android.e_garden.viewModels.listView;

import android.content.Context;
import android.net.Uri;
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

import java.util.ArrayList;

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
        View row = layoutInflater.inflate(R.layout.item_list, parent, false);
        ImageView image = row.findViewById(R.id.ivPlant);
        TextView plantTitle = row.findViewById(R.id.tvplantName);
        TextView plantDescription = row.findViewById(R.id.tvplantCategory);
        TextView plantStatus = row.findViewById(R.id.tvplantStatus);

        Plant plant = plants.get(position);

        ArrayList<PlantPhoto> photos = plant.getPhotos();
        image.setImageResource(R.drawable.plant);
        image.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (photos.size() > 0) {
            Uri plantUri = Globals.getInstance().getPlantImage(photos.get(0).getPath());
            for (int i = 1; i < photos.size(); i++) {
                if (plantUri != null) {
                    break;
                }
                plantUri = Globals.getInstance().getPlantImage(photos.get(i).getPath());
            }
            if (plantUri != null) {
                Glide.with(row).load(plantUri).into(image);
            }
        }

        plantTitle.setText(plant.getName());
        plantDescription.setText(plant.getCategory().toString());

        Long remainingHours = plant.calculateRemainingHours();
        if (remainingHours == null) {
            plantStatus.setText("Não foi definido um período de rega para esta planta");
        } else if (remainingHours < 0) {
            plantStatus.setText("Rega atrasada em " + ((remainingHours < -24) ? (-1 * remainingHours / 24) + " dias" : remainingHours + " horas"));
        } else if (remainingHours == 0) {
            plantStatus.setText("Regar agora");
        } else {
            plantStatus.setText("Regar em " + ((remainingHours > 24) ? remainingHours / 24 + " dias" : remainingHours + " horas"));
        }

        return row;
    }
}
