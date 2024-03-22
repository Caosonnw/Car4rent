package com.example.car4rent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

public class CarInfoFragment extends Fragment {

    private EditText editTextLicensePlate, editTextBrand, editTextModel, editTextSeats, editTextPrice;
    private Spinner spinnerTransmission, spinnerFuelType;
    private Button buttonNext;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_info, container, false);

        editTextLicensePlate = view.findViewById(R.id.editTextLicensePlate);
        editTextBrand = view.findViewById(R.id.editTextBrand);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextSeats = view.findViewById(R.id.editTextSeats);
        spinnerTransmission = view.findViewById(R.id.spinnerTransmission);
        spinnerFuelType = view.findViewById(R.id.spinnerFuelType);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        buttonNext = view.findViewById(R.id.buttonNext);

        db = FirebaseFirestore.getInstance();

        // ArrayAdapter cho spinnerTransmission
        ArrayAdapter<CharSequence> transmissionAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.transmission_options, android.R.layout.simple_spinner_item);
        transmissionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTransmission.setAdapter(transmissionAdapter);

        // ArrayAdapter cho spinnerFuelType
        ArrayAdapter<CharSequence> fuelTypeAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.fuel_type_options, android.R.layout.simple_spinner_item);
        fuelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFuelType.setAdapter(fuelTypeAdapter);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra và lưu thông tin vào Firebase
                saveCarInfoToFirebase();
            }
        });

        return view;
    }

    private void saveCarInfoToFirebase() {
        String licensePlate = editTextLicensePlate.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String model = editTextModel.getText().toString().trim();
        String seatsStr = editTextSeats.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();

        if (licensePlate.isEmpty() || brand.isEmpty() || model.isEmpty() || seatsStr.isEmpty() || price.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int seats;
        try {
            seats = Integer.parseInt(seatsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Please enter a valid number for seats", Toast.LENGTH_SHORT).show();
            return;
        }

        String transmission = spinnerTransmission.getSelectedItem().toString();
        String fuelType = spinnerFuelType.getSelectedItem().toString();

        // Tạo đối tượng ProductCar và lưu vào Firestore
        ProductCar car = new ProductCar();
        car.setLicensePlate(licensePlate);
        car.setBrand(brand);
        car.setModel(model);
        car.setSeats(seats);
        car.setTransmission(transmission);
        car.setFuelType(fuelType);
        car.setPrice(price);

        // Lưu thông tin vào Firestore và chuyển sang Fragment UploadImageFragment
        db.collection("items").add(car)
                .addOnSuccessListener(documentReference -> {
                    car.setDocumentId(documentReference.getId()); // Sử dụng setDocumentId() để đặt documentId
                    ((UpdateCarActivity) requireActivity()).setCar(car); // Lưu đối tượng car vào MainActivity
                    ((UpdateCarActivity) requireActivity()).navigateToUploadImageFragment(car); // Chuyển sang UploadImageFragment và truyền đối tượng ProductCar
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi lưu thất bại
                    Toast.makeText(requireContext(), "Failed to save car: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
