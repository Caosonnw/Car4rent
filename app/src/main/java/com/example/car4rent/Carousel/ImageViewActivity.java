package com.example.car4rent.Carousel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.car4rent.R;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ImageView imageView = findViewById(R.id.imageView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);

        String imageUrl = getIntent().getStringExtra("image");
        String description = getIntent().getStringExtra("description");

        Glide.with(ImageViewActivity.this).load(imageUrl).into(imageView);
        descriptionTextView.setText(description);
    }

}