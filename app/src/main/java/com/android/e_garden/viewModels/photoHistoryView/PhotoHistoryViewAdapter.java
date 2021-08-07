package com.android.e_garden.viewModels.photoHistoryView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.PlantPhoto;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PhotoHistoryViewAdapter extends ArrayAdapter<PlantPhoto> {

    private final Context context;
    private final ArrayList<PlantPhoto> photos;

    public PhotoHistoryViewAdapter(Context context, ArrayList<PlantPhoto> photos) {
        super(context, R.layout.item_photo_history, photos);
        this.context = context;
        this.photos = photos;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.item_photo_history, viewGroup, false);
        ImageView image = row.findViewById(R.id.ivPlantHistory);
        Glide.with(row).load(Globals.getInstance().getPlantImage(photos.get(i).getPath())).into(image);
        return row;
    }
}
