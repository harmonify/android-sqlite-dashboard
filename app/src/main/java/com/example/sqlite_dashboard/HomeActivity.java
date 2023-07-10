package com.example.sqlite_dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private Button buttonManageMahasiswa, buttonManageBuku;
    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonManageMahasiswa = findViewById(R.id.button_manage_mahasiswa);
        buttonManageBuku = findViewById(R.id.button_manage_buku);
        imageSlider = findViewById(R.id.image_slider);

        buttonManageMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MahasiswaActivity.class);
                startActivity(intent);
            }
        });

        buttonManageBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BukuActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.kucing_kekar, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.kucing_break_dance, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.kucing_hand_stand, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.kucing_kung_fu, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.kucing_sit_up, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }
}