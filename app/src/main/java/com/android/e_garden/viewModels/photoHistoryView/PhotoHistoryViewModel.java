
package com.android.e_garden.viewModels.photoHistoryView;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.e_garden.Globals;
import com.android.e_garden.R;
import com.android.e_garden.models.Plant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

public class PhotoHistoryViewModel extends AppCompatActivity implements Globals.PlantObservable {

    private Plant plant;
    private PhotoHistoryViewAdapter adapter;

    private boolean fullscreen = false;
    private ImageView fullScreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_history);

        Toolbar toolbar = findViewById(R.id.toolbar_photo_history);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Histórico de Fotos");

        String plantId = getIntent().getStringExtra("plant");
        for (Plant plantItem : Globals.getInstance().getPlants()) {
            if (plantItem.getId().equals(plantId)) {
                plant = plantItem;
            }
        }

        Globals.getInstance().setPlantObservable(this);
        adapter = new PhotoHistoryViewAdapter(this, plant.getPhotos());

        fullScreenImage = findViewById(R.id.ivFullscreen);

        GridView gridView = findViewById(R.id.gvphotoHistory);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (fullscreen) {
                hideFullscreenImage();
            } else {
                fullscreen = true;
                Uri imageUri = Globals.getInstance().getPlantImage(plant.getPhotos().get(i).getPath());
                Glide.with(this).load(imageUri).into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        fullScreenImage.setImageDrawable(resource);
                        fullScreenImage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
            }
        });

        fullScreenImage.setOnClickListener(view -> hideFullscreenImage());
    }

    @Override
    public void onBackPressed() {
        if (fullscreen) {
            hideFullscreenImage();
        } else {
            super.onBackPressed();
        }
    }

    private void hideFullscreenImage() {
        fullscreen = false;
        fullScreenImage.setVisibility(View.GONE);
    }

    @Override
    public void onPlantUpdate() {
        for (Plant plantItem : Globals.getInstance().getPlants()) {
            if (plantItem.getId().equals(plant.getId())) {
                plant = plantItem;
            }
        }
        adapter.clear();
        adapter.addAll(plant.getPhotos());
    }
}