package com.example.car4rent;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateCarActivity extends AppCompatActivity {
    private ProductCar car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Mở fragment mặc định khi MainActivity được tạo
        navigateToCarInfoFragment();
    }
    public void navigateToCarInfoFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new CarInfoFragment())
                .commit();
    }

    public void navigateToUploadImageFragment(ProductCar car) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, UploadImageFragment.newInstance(car))
                .addToBackStack(null)
                .commit();
    }

    public void navigateToCarListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new CarListFragment())
                .commit();
    }
    public ProductCar getCar() {
        if (car == null) {
            car = new ProductCar();
        }
        return car;
    }

    public void setCar(ProductCar car) {
        this.car = car;
    }
}